package com.sebin.employee_poc.controller;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("departments")
    public List<DepartmentEntity> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("department/{departmentId}")
    public DepartmentEntity getDepartmentById(@PathVariable int departmentId){
        return departmentService.getDepartmentById(departmentId).get();
    }

    @PostMapping("department/add")
    public int addDepartment(@RequestBody DepartmentEntity departmentEntity){
        return departmentService.addDepartment(departmentEntity);
    }

    @PutMapping("department/{departmentId}")
    public int updateDepartment(@PathVariable int departmentId){
        return departmentService.updateDepartment(departmentId);
    }

    @DeleteMapping("department/{departmentId}")
    public int deleteDepartment(@PathVariable int departmentId){
        return departmentService.deleteDepartment(departmentId);
    }
}
