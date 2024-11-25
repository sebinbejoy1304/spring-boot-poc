package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.entity.EmployeeEntity;
import com.sebin.employee_poc.exception.DepartmentAlreadyExists;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import com.sebin.employee_poc.exception.NoChangeInDepartmentException;
import com.sebin.employee_poc.model.DepartmentResponse;
import com.sebin.employee_poc.model.ErrorResponse;
import com.sebin.employee_poc.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    private DepartmentResponse mapToDepartmentResponse(DepartmentEntity departmentEntity){
        return new DepartmentResponse(departmentEntity.getDepartmentName(),departmentEntity.getLocation());
    }

    private boolean isDepartmentUnchanged(DepartmentEntity existingDepartment, DepartmentEntity newDepartment) {
        return (newDepartment.getDepartmentName() == null || existingDepartment.getDepartmentName().equals( newDepartment.getDepartmentName()))
                && (newDepartment.getLocation() == null || existingDepartment.getLocation().equals( newDepartment.getLocation()));
    }

    public List<DepartmentEntity> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public DepartmentEntity getDepartmentById(int departmentId){
        return departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
    }

    public DepartmentResponse addDepartment(DepartmentEntity departmentEntity){
        if(departmentRepository.departmentExists(departmentEntity.getDepartmentName(),departmentEntity.getLocation()))
            throw new DepartmentAlreadyExists("Department already exists");
        departmentRepository.save(departmentEntity);
        return mapToDepartmentResponse(departmentEntity);
    }

    public DepartmentResponse updateDepartment(int departmentId, DepartmentEntity departmentEntity){
        DepartmentEntity existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));

        if(isDepartmentUnchanged(departmentEntity,existingDepartment))
            throw new NoChangeInDepartmentException("No change in department");

        if (departmentEntity.getDepartmentName() != null)
            existingDepartment.setDepartmentName(departmentEntity.getDepartmentName());
        if (departmentEntity.getLocation() != null )
            existingDepartment.setLocation(departmentEntity.getLocation());

        departmentRepository.update(existingDepartment);
        return mapToDepartmentResponse(existingDepartment);
    }

    public ErrorResponse deleteDepartment(int departmentId){
        departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
        departmentRepository.deleteById(departmentId);
        return new ErrorResponse(204,"Department deleted successfully");
    }
}
