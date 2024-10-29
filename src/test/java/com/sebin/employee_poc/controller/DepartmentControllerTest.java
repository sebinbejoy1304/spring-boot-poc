package com.sebin.employee_poc.controller;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.model.ApiResponse;
import com.sebin.employee_poc.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DepartmentControllerTest {
    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setDepartmentController(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDepartmentsTest(){
        DepartmentEntity department1 = new DepartmentEntity(1,"IT","Bangalore");
        DepartmentEntity department2 = new DepartmentEntity(2,"HR","Chennai");
        DepartmentEntity department3 = new DepartmentEntity(3,"Finance","Hyderabad");

        List<DepartmentEntity> mockDepartments = Arrays.asList(department1,department2,department3);

        when(departmentService.getAllDepartments()).thenReturn(mockDepartments);

        List<DepartmentEntity> response = departmentController.getAllDepartments();

        assertEquals(3,response.size());
    }

    @Test
    void getDepartmentByIdTest(){
        int departmentId = 1;
        DepartmentEntity mockDepartment = new DepartmentEntity(1,"IT","Bangalore");

        when(departmentService.getDepartmentById(departmentId)).thenReturn(mockDepartment);

        DepartmentEntity response = departmentController.getDepartmentById(departmentId);

        assertEquals(mockDepartment,response);
    }

    @Test
    void addDepartmentTest(){
        DepartmentEntity departmentEntity = new DepartmentEntity(1,"IT","Bangalore");

        ApiResponse mockResponse = new ApiResponse(200,"Department added successfully");
        when(departmentService.addDepartment(departmentEntity))
                .thenReturn(mockResponse);

        ApiResponse response = departmentController.addDepartment(departmentEntity);

        assertEquals(mockResponse,response);
    }

    @Test
    void updateDepartmentTest(){
        int departmentId=1;
        DepartmentEntity departmentEntity = new DepartmentEntity(1,"IT","Bangalore");
        ApiResponse mockResponse = new ApiResponse(200,"Department updated successfully");

        when(departmentService.updateDepartment(departmentId,departmentEntity)).thenReturn(mockResponse);

        ApiResponse response = departmentController.updateDepartment(departmentId,departmentEntity);

        assertEquals(mockResponse,response);
    }

    @Test
    void deleteDepartmentTest(){
        int departmentId=1;
        ApiResponse mockResponse = new ApiResponse(200,"Department deleted successfully");

        when(departmentService.deleteDepartment(departmentId)).thenReturn(mockResponse);

        ApiResponse response = departmentController.deleteDepartment(departmentId);

        assertEquals(mockResponse,response);
    }
}