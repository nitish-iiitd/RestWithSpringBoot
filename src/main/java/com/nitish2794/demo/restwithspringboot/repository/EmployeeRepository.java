package com.nitish2794.demo.restwithspringboot.repository;

import com.nitish2794.demo.restwithspringboot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}