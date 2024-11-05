package com.sebin.employee_poc.controller;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.model.DepartmentResponse;
import com.sebin.employee_poc.model.ErrorResponse;
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
    public DepartmentResponse addDepartment(@RequestBody DepartmentEntity departmentEntity){
        return departmentService.addDepartment(departmentEntity);
    }

    @PutMapping("department/{departmentId}")
    public DepartmentResponse updateDepartment(@PathVariable int departmentId, @RequestBody DepartmentEntity departmentEntity){
        return departmentService.updateDepartment(departmentId,departmentEntity);
    }

    @DeleteMapping("department/{departmentId}")
    public ErrorResponse deleteDepartment(@PathVariable int departmentId){
        return departmentService.deleteDepartment(departmentId);
    }
}
