package com.sebin.employee_poc.repository;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepository {
    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DepartmentEntity> findAll(){
        String sql= "SELECT * FROM department";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new DepartmentEntity(
            rs.getInt("departmentId"),
            rs.getString("departmentName"),
            rs.getString("location")
        ));
    }

    public Optional<DepartmentEntity> findById(int departmentId){
        String sql= "SELECT * FROM department WHERE departmentId="+departmentId;
        return jdbcTemplate.query(sql, rs -> {
            if(rs.next()) {
                return Optional.of(new DepartmentEntity(
                        rs.getInt("departmentId"),
                        rs.getString("departmentName"),
                        rs.getString("location")
                ));
            }
            else
                return Optional.empty();
        });
    }

    public DepartmentEntity save(DepartmentEntity departmentEntity){
        String sql = "INSERT INTO department (departmentName, location) VALUES (?,?)";
        jdbcTemplate.update(sql,
                departmentEntity.getDepartmentName(),
                departmentEntity.getLocation());
        return departmentEntity;
    }

    public DepartmentEntity update(DepartmentEntity departmentEntity){
        String sql = "UPDATE department SET departmentName=?, location=? WHERE departmentId=?";
        jdbcTemplate.update(sql,
                departmentEntity.getDepartmentName(),
                departmentEntity.getLocation(),
                departmentEntity.getDepartmentId());
        return departmentEntity;
    }

    public int deleteById(int departmentId){
        String sql = "DELETE FROM department WHERE departmentId="+departmentId;
        return jdbcTemplate.update(sql);
    }
}
