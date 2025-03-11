package com.example.stringboot.entities.repositiories;

import com.example.stringboot.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//klasy komunikujące się z bazą danych poprzez JpaRepository
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
