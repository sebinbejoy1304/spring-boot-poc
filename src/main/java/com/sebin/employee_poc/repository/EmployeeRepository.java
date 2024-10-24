package com.sebin.employee_poc.repository;

import com.sebin.employee_poc.entity.EmployeeEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EmployeeEntity> findAll() {
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

    public Optional<EmployeeEntity> findById(int employeeId){
        String sql = "SELECT * FROM employee WHERE employeeId="+employeeId;
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()){
                return Optional.of(new EmployeeEntity(
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
            else
                return Optional.empty();
        });
    }

    public int save(EmployeeEntity employeeEntity){
        String sql = "INSERT INTO employee(firstName,lastName,jobRole,salary,departmentId) VALUES(?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                employeeEntity.getFirstName(),
                employeeEntity.getLastName(),
                employeeEntity.getJobRole(),
                employeeEntity.getSalary(),
                employeeEntity.getDepartmentId());
    }
}
