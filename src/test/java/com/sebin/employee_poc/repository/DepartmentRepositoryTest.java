package com.sebin.employee_poc.repository;

import com.sebin.employee_poc.entity.DepartmentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class DepartmentRepositoryTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    DepartmentRepository departmentRepository;

    @BeforeEach
    void setDepartmentRepository(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllTest(){
        DepartmentEntity department1 = new DepartmentEntity(1,"IT","Bangalore");
        DepartmentEntity department2 = new DepartmentEntity(2,"HR","Chennai");
        DepartmentEntity department3 = new DepartmentEntity(3,"Finance","Hyderabad");

        List<DepartmentEntity> mockDepartments = Arrays.asList(department1,department2,department3);

        when(jdbcTemplate.query(any(String.class),any(RowMapper.class))).thenReturn(mockDepartments);

        List<DepartmentEntity> responses = departmentRepository.findAll();

        assertEquals(3,responses.size());
    }

    @Test
    void findByIdTest_departmentExists(){
        int departmentId = 1;
        DepartmentEntity departmentEntity = new DepartmentEntity(1,"IT","Bangalore");

        when(jdbcTemplate.query(eq("SELECT * FROM department WHERE departmentId=1"), any(ResultSetExtractor.class)))
                .thenReturn(Optional.of(departmentEntity));

        Optional<DepartmentEntity> response = departmentRepository.findById(departmentId);

        assertEquals(departmentEntity,response.get());
    }

    @Test
    public void findByIdTest_departmentDoesNotExist(){
        int departmentId = 1;

        when(jdbcTemplate.query(eq("SELECT * FROM department WHERE departmentId=1"), any(ResultSetExtractor.class)))
                .thenReturn(Optional.empty());

        Optional<DepartmentEntity> response = departmentRepository.findById(departmentId);

        assertEquals(Optional.empty(),response);
    }

    @Test
    void departmentSaveTest(){
        DepartmentEntity departmentEntity = new DepartmentEntity(1,"IT","Bangalore");

        when(jdbcTemplate.update(
                eq("INSERT INTO department (departmentName, location) VALUES (?,?)"),
                eq(departmentEntity.getDepartmentName()),
                eq(departmentEntity.getLocation())))
                .thenReturn(1);

        int rowsAffected = departmentRepository.save(departmentEntity);

        assertEquals(1,rowsAffected);
    }

    @Test
    void departmentUpdateTest(){
        DepartmentEntity departmentEntity = new DepartmentEntity(1,"IT","Bangalore");

        when(jdbcTemplate.update(
                eq("UPDATE department SET departmentName=?, location=? WHERE departmentId=?"),
                eq(departmentEntity.getDepartmentName()),
                eq(departmentEntity.getLocation()),
                eq(departmentEntity.getDepartmentId())))
                .thenReturn(1);

        int rowsAffected = departmentRepository.update(departmentEntity);

        assertEquals(1,rowsAffected);
    }

    @Test
    void departmentDeleteTest(){
        int departmentId=1;

        when(jdbcTemplate.update(eq("DELETE FROM department WHERE departmentId=1")))
                .thenReturn(1);

        int rowsAffected = departmentRepository.deleteById(departmentId);

        assertEquals(1,rowsAffected);
    }
}
