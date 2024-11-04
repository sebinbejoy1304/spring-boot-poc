package com.sebin.employee_poc.controller;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.model.ApiResponse;
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
        return departmentService.getDepartmentById(departmentId);
    }

    @PostMapping("department/add")
    public DepartmentEntity addDepartment(@RequestBody DepartmentEntity departmentEntity){
        return departmentService.addDepartment(departmentEntity);
    }

    @PutMapping("department/{departmentId}")
    public DepartmentEntity updateDepartment(@PathVariable int departmentId, @RequestBody DepartmentEntity departmentEntity){
        return departmentService.updateDepartment(departmentId,departmentEntity);
    }

    @DeleteMapping("department/{departmentId}")
    public ApiResponse deleteDepartment(@PathVariable int departmentId){
        return departmentService.deleteDepartment(departmentId);
    }
}
