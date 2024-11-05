package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import com.sebin.employee_poc.model.DepartmentResponse;
import com.sebin.employee_poc.model.ErrorResponse;
import com.sebin.employee_poc.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    private DepartmentResponse mapToDepartmentResponse(DepartmentEntity departmentEntity){
        return new DepartmentResponse(departmentEntity.getDepartmentName(),departmentEntity.getLocation());
    }

    public List<DepartmentEntity> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public DepartmentEntity getDepartmentById(int departmentId){
        return departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
    }

    public DepartmentResponse addDepartment(DepartmentEntity departmentEntity){
        departmentRepository.save(departmentEntity);
        return mapToDepartmentResponse(departmentEntity);
    }

    public DepartmentResponse updateDepartment(int departmentId, DepartmentEntity departmentEntity){
        DepartmentEntity existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
        existingDepartment.setDepartmentName(departmentEntity.getDepartmentName());
        existingDepartment.setLocation(departmentEntity.getLocation());
        departmentRepository.update(existingDepartment);
        return mapToDepartmentResponse(departmentEntity);
    }

    public ErrorResponse deleteDepartment(int departmentId){
        departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
        departmentRepository.deleteById(departmentId);
        return new ErrorResponse(204,"Department deleted successfully");
    }
}
