package com.example.stringboot.controllers;
import com.example.stringboot.services.CsvService;
import com.example.stringboot.entities.Employee;
import com.example.stringboot.entities.repositiories.EmployeeRepository;
import com.example.stringboot.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
/*
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employeelist")
    public List<Employee> employees() {
        return employeeRepository.findAll();
    }
*/

    private final EmployeeService employeeService;
    private final CsvService csvService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CsvService csvService) {
        this.employeeService = employeeService;
        this.csvService = csvService;
    }


    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        try {
            // Dodanie pracownika przez serwis
            Employee createdEmployee = employeeService.addEmployee(employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            // Jeśli wystąpi błąd
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id) {
        boolean isDeleted = employeeService.deleteEmployeeById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employee/csv")
    public ResponseEntity<byte[]> getEmployeesCsv() {
        try {
            // Pobierz listę pracowników
            byte[] csvData = csvService.generateCsvForEmployees(employeeService.getAllEmployees());

            // Ustaw odpowiednie nagłówki, aby wskazać, że odpowiedź to plik CSV
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=employees.csv");
            headers.add("Content-Type", "text/csv");

            // Zwróć odpowiedź z danymi pliku CSV
            return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}