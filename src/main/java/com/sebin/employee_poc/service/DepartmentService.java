package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.exception.DepartmentAlreadyExists;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import com.sebin.employee_poc.exception.NoChangeInDepartmentException;
import com.sebin.employee_poc.model.DepartmentResponse;
import com.sebin.employee_poc.model.ErrorResponse;
import com.sebin.employee_poc.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    private DepartmentResponse mapToDepartmentResponse(DepartmentEntity departmentEntity){
        if(logger.isDebugEnabled()){
            logger.debug("Mapping DepartmentEntity to DepartmentResponse: {}", departmentEntity);
        }
        return new DepartmentResponse(departmentEntity.getDepartmentName(),departmentEntity.getLocation());
    }

    private boolean isDepartmentUnchanged(DepartmentEntity existingDepartment, DepartmentEntity newDepartment) {
        logger.debug("Checking if the department is unchanged.");
        return (newDepartment.getDepartmentName() == null || existingDepartment.getDepartmentName().equals( newDepartment.getDepartmentName()))
                && (newDepartment.getLocation() == null || existingDepartment.getLocation().equals( newDepartment.getLocation()));
    }

    public List<DepartmentEntity> getAllDepartments(){
        logger.info("Fetching all departments.");
        return departmentRepository.findAll();
    }

    public DepartmentEntity getDepartmentById(int departmentId){
        logger.info("Fetching department by ID: {}", departmentId);
        return departmentRepository.findById(departmentId)
                .orElseThrow(()-> {
                    logger.error("Department not found with ID: {}", departmentId);
                    return new DepartmentNotFoundException("Department Not Found with Id:" + departmentId);
                });
    }

    public DepartmentResponse addDepartment(DepartmentEntity departmentEntity){
        logger.info("Adding new department: {}", departmentEntity);
        if(departmentRepository.departmentExists(departmentEntity.getDepartmentName(),departmentEntity.getLocation())) {
            logger.warn("Department already exists: {}", departmentEntity);
            throw new DepartmentAlreadyExists("Department already exists");
        }
        departmentRepository.save(departmentEntity);
        logger.info("Department added successfully: {}", departmentEntity);
        return mapToDepartmentResponse(departmentEntity);
    }

    public DepartmentResponse updateDepartment(int departmentId, DepartmentEntity departmentEntity){
        logger.info("Updating department with ID: {}", departmentId);
        DepartmentEntity existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(()->{
                    logger.error("Department not found with ID: {}", departmentId);
                    System.out.println("Department not found with ID: "+departmentId);
                    return  new DepartmentNotFoundException("Department Not Found with Id:"+departmentId);
                });

        if(isDepartmentUnchanged(existingDepartment,departmentEntity)){
            logger.warn("No change detected in department with ID: {}", departmentId);
            throw new NoChangeInDepartmentException("No change in department");
        }

        if (departmentEntity.getDepartmentName() != null){
            logger.debug("Updating department name from '{}' to '{}'", existingDepartment.getDepartmentName(), departmentEntity.getDepartmentName());
            existingDepartment.setDepartmentName(departmentEntity.getDepartmentName());
        }

        if (departmentEntity.getLocation() != null ){
            logger.debug("Updating location from '{}' to '{}'", existingDepartment.getLocation(), departmentEntity.getLocation());
            existingDepartment.setLocation(departmentEntity.getLocation());
        }

        departmentRepository.update(existingDepartment);
        logger.info("Department updated successfully: {}", existingDepartment);
        return mapToDepartmentResponse(existingDepartment);
    }

    public ErrorResponse deleteDepartment(int departmentId){
        logger.info("Deleting department with ID: {}", departmentId);
        departmentRepository.findById(departmentId)
                .orElseThrow(()->{
                    logger.error("Department not found with ID: {}", departmentId);
                    return new DepartmentNotFoundException("Department Not Found with Id:"+departmentId);
                });
        departmentRepository.deleteById(departmentId);
        logger.info("Department deleted successfully with ID: {}", departmentId);
        return new ErrorResponse(204,"Department deleted successfully");
    }
}
