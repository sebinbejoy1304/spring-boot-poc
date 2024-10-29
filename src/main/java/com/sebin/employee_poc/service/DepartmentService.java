package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import com.sebin.employee_poc.model.ApiResponse;
import com.sebin.employee_poc.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<DepartmentEntity> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public DepartmentEntity getDepartmentById(int departmentId){
        return departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
    }

    public ApiResponse addDepartment(DepartmentEntity departmentEntity){
        departmentRepository.save(departmentEntity);
        return new ApiResponse(200,"Department added successfully");
    }

    public ApiResponse updateDepartment(int departmentId, DepartmentEntity departmentEntity){
        DepartmentEntity existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
        existingDepartment.setDepartmentName(departmentEntity.getDepartmentName());
        existingDepartment.setLocation(departmentEntity.getLocation());
        departmentRepository.update(existingDepartment);
        return new ApiResponse(200,"Department updated successfully (set location=kochi)");
    }

    public ApiResponse deleteDepartment(int departmentId){
        departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
        departmentRepository.deleteById(departmentId);
        return new ApiResponse(200,"Department deleted successfully");
    }
}
