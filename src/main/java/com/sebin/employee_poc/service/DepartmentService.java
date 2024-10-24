package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.DepartmentEntity;
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

    public Optional<DepartmentEntity> getDepartmentById(int departmentId){
        return departmentRepository.findById(departmentId);
    }

    public int addDepartment(DepartmentEntity departmentEntity){
        return departmentRepository.save(departmentEntity);
    }

    public int updateDepartment(int departmentId){
        Optional<DepartmentEntity> existingDepartment = departmentRepository.findById(departmentId);
        DepartmentEntity department = existingDepartment.get();
        department.setLocation("Kochi");
        return departmentRepository.update(department);
    }

    public int deleteDepartment(int departmentId){
        return departmentRepository.deleteById(departmentId);
    }
}
