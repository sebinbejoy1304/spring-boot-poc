package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import com.sebin.employee_poc.model.DepartmentResponse;
import com.sebin.employee_poc.model.ErrorResponse;
import com.sebin.employee_poc.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setDepartmentService(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDepartmentsTest(){
        DepartmentEntity department1 = new DepartmentEntity(1,"IT","Bangalore");
        DepartmentEntity department2 = new DepartmentEntity(2,"HR","Chennai");
        DepartmentEntity department3 = new DepartmentEntity(3,"Finance","Hyderabad");

        List<DepartmentEntity> mockDepartments = Arrays.asList(department1,department2,department3);

        when(departmentRepository.findAll()).thenReturn(mockDepartments);

        List<DepartmentEntity> response = departmentService.getAllDepartments();

        assertEquals(3,response.size());
    }

    @Test
    void getDepartmentByIdTest_departmentExists(){
        int departmentId = 1;
        DepartmentEntity mockDepartment = new DepartmentEntity(1,"IT","Bangalore");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(mockDepartment));

        DepartmentEntity response = departmentService.getDepartmentById(departmentId);

        assertEquals(mockDepartment,response);
    }

    @Test
    void getDepartmentByIdTest_departmentDoesNotExist(){
        int departmentId = 1;

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class,()->departmentService.getDepartmentById(departmentId));
    }

    @Test
    void addDepartmentTest(){
        DepartmentEntity departmentEntity = new DepartmentEntity(1,"IT","Bangalore");
        DepartmentResponse departmentResponse = new DepartmentResponse("IT","Bangalore");

        when(departmentRepository.save(departmentEntity))
                .thenReturn(departmentEntity);

        DepartmentResponse response = departmentService.addDepartment(departmentEntity);

        assertEquals(departmentResponse,response);
    }

    @Test
    void updateDepartmentTest_departmentExists(){
        int departmentId=1;
        DepartmentEntity mockOldDepartment = new DepartmentEntity(1,"Information Technology","Bangalore");
        DepartmentEntity mockNewDepartment = new DepartmentEntity(1,"IT","Bangalore");
        DepartmentResponse departmentResponse = new DepartmentResponse("IT","Bangalore");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(mockOldDepartment));
        when(departmentRepository.update(mockNewDepartment)).thenReturn(mockNewDepartment);

        DepartmentResponse response = departmentService.updateDepartment(departmentId,mockNewDepartment);

        assertEquals(departmentResponse,response);
    }

    @Test
    void updateDepartmentTest_departmentDoesNotExist(){
        int departmentId=1;
        DepartmentEntity mockDepartment = new DepartmentEntity(1,"IT","Bangalore");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class,()->departmentService.updateDepartment(departmentId,mockDepartment));
    }

    @Test
    void deleteDepartmentTest_departmentExists(){
        int departmentId=1;
        DepartmentEntity mockDepartment = new DepartmentEntity(1,"IT","Bangalore");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(mockDepartment));
        when(departmentRepository.deleteById(departmentId)).thenReturn(1);

        ErrorResponse response = departmentService.deleteDepartment(departmentId);

        assertEquals(204,response.getCode());
    }

    @Test
    void deleteDepartmentTest_departmentDoesNotExist(){
        int departmentId=1;

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class,()->departmentService.deleteDepartment(departmentId));
    }
}
