//package com.sebin.employee_poc.controller;
//
//import com.sebin.employee_poc.entity.DepartmentEntity;
//import com.sebin.employee_poc.repository.DepartmentRepository;
//import com.sebin.employee_poc.service.DepartmentService;
//import org.apache.commons.io.IOUtils;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
//@WebAppConfiguration
//public class DepartmentControllerIT {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private DepartmentRepository departmentRepository;
//
//    @InjectMocks
//    private DepartmentController departmentController;
//
//    private String readJsonAsString() throws IOException {
//        try (FileInputStream fis = new FileInputStream("src/test/resources/get_department_response.json")) {
//            return IOUtils.toString(fis, StandardCharsets.UTF_8);
//        }
//    }
//
//    @Test
//    public void getAllDepartmentsTest() throws Exception {
//
//        String jsonContent = readJsonAsString();
//        DepartmentEntity departmentEntity = new DepartmentEntity(1,"IT","Bangalore");
//
//        //doNothing().when(departmentRepository).save(any());
//
//        when(departmentRepository.save(departmentEntity))
//                .thenReturn(departmentEntity);
//
//        MvcResult result = mockMvc.perform(get("/api/departments")
//                        .contentType("application/json")
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//}
