package com.sebin.employee_poc.repository;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.entity.EmployeeEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRespository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeRespository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EmployeeEntity> findall() {
        String sql = "SELECT * FROM employee";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new EmployeeEntity(
                rs.getInt("employeeId"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("jobRole"),
                rs.getDouble("salary"),
                rs.getInt("departmentId"),
                rs.getTimestamp("createdDateTime").toLocalDateTime(),
                rs.getTimestamp("modifiedDateTime").toLocalDateTime()
        ));
    }
}
