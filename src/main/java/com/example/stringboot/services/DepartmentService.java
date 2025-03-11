package com.example.stringboot.services;

import com.example.stringboot.entities.Department;
import com.example.stringboot.entities.Employee;
import com.example.stringboot.entities.repositiories.DepartmentRepository;
import com.example.stringboot.entities.repositiories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }


    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();  // Pobiera wszystkie działy
    }

    public Department addDepartment(Department department) {
        // Dodatkowa logika walidacji lub sprawdzania może być tutaj
        return departmentRepository.save(department);  // Zapisuje nowy dział
    }

    // Metoda do usuwania grupy
 /*   public boolean deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            return false;
        }

        // Upewniamy się, że nie ma pracowników w tym dziale
        List<Employee> employees = employeeRepository.findByDepartmentId(id);
        if (!employees.isEmpty()) {
            throw new IllegalStateException("Cannot delete department with employees");
        }

        departmentRepository.delete(department);
        return true;
    }*/
    public boolean deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            System.out.println("Department not found with ID: " + id);  // Logowanie, jeśli dział nie istnieje
            return false;
        }

        // Upewniamy się, że nie ma pracowników w tym dziale
        List<Employee> employees = employeeRepository.findByDepartmentId(id);
        if (!employees.isEmpty()) {
            System.out.println("Cannot delete department with employees: " + department.getName());  // Logowanie, jeśli są pracownicy
            throw new IllegalStateException("Cannot delete department with employees");
        }

        departmentRepository.delete(department);
        System.out.println("Deleted department: " + department.getName());  // Logowanie po usunięciu
        return true;
    }

    // Metoda do pobierania pracowników w grupie
    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

}