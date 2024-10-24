package com.sebin.employee_poc.repository;

import com.sebin.employee_poc.entity.DepartmentEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepository {
    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DepartmentEntity> findAll(){
        String sql= "SELECT * FROM department";
        return jdbcTemplate.query(sql, (rs,rowNum)-> new DepartmentEntity(
            rs.getInt("departmentId"),
            rs.getString("departmentName"),
            rs.getString("location")
        ));
    }
}
