package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
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

    public int addDepartment(DepartmentEntity departmentEntity){
        return departmentRepository.save(departmentEntity);
    }

    public int updateDepartment(int departmentId){
        DepartmentEntity existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
        existingDepartment.setLocation("Kochi");
        return departmentRepository.update(existingDepartment);
    }

    public int deleteDepartment(int departmentId){
        departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department Not Found with Id:"+departmentId));
        return departmentRepository.deleteById(departmentId);
    }
}
