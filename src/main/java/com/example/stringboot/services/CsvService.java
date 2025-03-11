package com.example.stringboot.services;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.example.stringboot.entities.Employee;
import org.springframework.stereotype.Service;

@Service
public class CsvService {

    // generowanie CSV z listy pracowników
    public byte[] generateCsvForEmployees(List<Employee> employees) throws IOException {
        StringBuilder csvBuilder = new StringBuilder();

        // nagłówki kolumn
        csvBuilder.append("id,firstName,lastName,department,salary,dateOfBirth,employeeCondition\n");

        // dodaje dane pracowników
        for (Employee employee : employees) {
            csvBuilder.append(employee.getId())
                    .append(",")
                    .append(employee.getFirstName())
                    .append(",")
                    .append(employee.getLastName())
                    .append(",")
                    .append(employee.getDepartment().getName())  // wyciagam nazwe wydzialu
                    .append(",")
                    .append(employee.getSalary())
                    .append(",")
                    .append(employee.getDateOfBirth())
                    .append(",")
                    .append(employee.getEmployeeCondition())
                    .append("\n");
        }

        return csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }
}