package com.example.stringboot;

import com.example.stringboot.controllers.EmployeeController;
import com.example.stringboot.entities.Department;
import com.example.stringboot.services.EmployeeService;
import com.example.stringboot.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.example.stringboot.services.CsvService;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private CsvService csvService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        // Inicjalizacja MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testAddEmployee() throws Exception {
        // Przygotowanie danych wejściowych
        Employee employee = new Employee();
        employee.setId(15L);  // Ręczne ustawienie ID
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setSalary(BigDecimal.valueOf(5000));
        employee.setDateOfBirth(LocalDate.of(1990, 1, 1));  // Przykładowa data urodzenia
        employee.setEmployeeCondition("obecny");  // Stan pracownika

        // Mocks dla EmployeeService
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        // Testowanie POST /employee
        mockMvc.perform(post("/api/employee")
                        .contentType("application/json")
                        .content("{\"id\":15,\"firstName\":\"John\",\"lastName\":\"Doe\",\"salary\":5000,\"dateOfBirth\":\"1990-01-01\",\"employeeCondition\":\"obecny\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(15))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.salary").value(5000))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$.employeeCondition").value("obecny"));

        // Weryfikacja, czy metoda serwisu została wywołana
        verify(employeeService, times(1)).addEmployee(any(Employee.class));
    }


    @Test
    public void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;

        // Mocks dla EmployeeService
        when(employeeService.deleteEmployeeById(employeeId)).thenReturn(true);

        // Testowanie DELETE /employee/{id}
        mockMvc.perform(delete("/api/employee/{id}", employeeId))
                .andExpect(status().isNoContent());

        // Weryfikacja, czy metoda serwisu została wywołana
        verify(employeeService, times(1)).deleteEmployeeById(employeeId);
    }


}