package com.example.stringboot;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;  // Import get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;  // Import status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;  // Import jsonPath
import com.example.stringboot.controllers.DepartmentController;
import com.example.stringboot.entities.Department;
import com.example.stringboot.entities.Employee;
import com.example.stringboot.services.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.example.stringboot.entities.repositiories.DepartmentRepository;
import com.example.stringboot.entities.repositiories.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//mowi Springowi, aby zaladowal pelen kontekst aplikacji, dzieki czemmu testy moga dzialc z pelna konfiguracja Spring Boot
@SpringBootTest
public class DepartmentControllerTest {

    // tworzy mocki (sztuczne obiekty) dla zaleznosci, ktore beda uzywane w testach
    @Mock
    private DepartmentService departmentService;

    // Wstrzykiwanie mocków do kontrolera
    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    private MockMvc mockMvc;

    // Inicjalizacja MockMvc przed każdym testem
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build(); // tworzy konfiguracje dla departmentcontroller
    }

    @Test
    public void testAddDepartment() throws Exception {
        // Przygotowanie danych wejściowych dla departamentu
        Department department = new Department();
        department.setId(100L);
        department.setName("IT");

        // mockowanie, ustawiamy aby departmentservice zwracal gotowy obiekt
        when(departmentService.addDepartment(any(Department.class))).thenReturn(department);

        // Testowanie POST /api/department
        mockMvc.perform(MockMvcRequestBuilders.post("/api/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":100,\"name\":\"IT\"}"))
                .andExpect(status().isCreated())  // Oczekiwany status HTTP 201 Created
                .andExpect(jsonPath("$.id").value(100))  // Sprawdzenie ID w odpowiedzi
                .andExpect(jsonPath("$.name").value("IT"));  // Sprawdzenie nazwy w odpowiedzi

        // Weryfikacja, czy metoda serwisu została wywołana dokladnie raz
        verify(departmentService, times(1)).addDepartment(any(Department.class));
    }

    @Test
    public void testGetDepartments() throws Exception {
        // Przygotowanie danych
        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("IT");

        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("HR");

        List<Department> departments = Arrays.asList(department1, department2);

        // Mockowanie metody serwisu
        when(departmentService.getAllDepartments()).thenReturn(departments);

        // Testowanie GET /department
        MvcResult result = mockMvc.perform(get("/api/department")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Oczekiwanie na status 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))  // Oczekiwanie na dwie grupy
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("IT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("HR"))
                .andReturn();

        // Weryfikacja, czy metoda serwisu została wywołana
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        Long departmentId = 1L;

        // Mocks dla DepartmentService
        when(departmentService.deleteDepartment(departmentId)).thenReturn(true);

        // Testowanie DELETE /department/{id}
        mockMvc.perform(delete("/api/group/{id}", departmentId))
                .andExpect(status().isNoContent());

        // Weryfikacja, czy metoda serwisu została wywołana
        verify(departmentService, times(1)).deleteDepartment(departmentId);
    }
    @Test
    public void testGetEmployeesInDepartment() throws Exception {
        Long departmentId = 1L;

        // Przygotowanie danych
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setSalary(BigDecimal.valueOf(5000));
        employee1.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employee1.setEmployeeCondition("obecny");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setSalary(BigDecimal.valueOf(5500));
        employee2.setDateOfBirth(LocalDate.of(1992, 3, 4));
        employee2.setEmployeeCondition("obecny");

        List<Employee> employees = Arrays.asList(employee1, employee2);

        // Mocks dla DepartmentService
        when(departmentService.getEmployeesByDepartment(departmentId)).thenReturn(employees);

        // Testowanie GET /api/group/{id}/employee
        mockMvc.perform(get("/api/group/{id}/employee", departmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));

        // Weryfikacja, czy metoda serwisu została wywołana
        verify(departmentService, times(1)).getEmployeesByDepartment(departmentId);
    }

}
