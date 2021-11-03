package com.nitish2794.demo.restwithspringboot.controller.rmmlevel1;

import java.util.List;

import com.nitish2794.demo.restwithspringboot.entity.Employee;
import com.nitish2794.demo.restwithspringboot.exception.EmployeeNotFoundException;
import com.nitish2794.demo.restwithspringboot.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rmm-level1")
public class Level1EmployeeController {

    private final EmployeeRepository repository;

    Level1EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }

    @PostMapping("/employees/add")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @PostMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PostMapping("/employees/{id}/update")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @PostMapping("/employees/{id}/delete")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        repository.findById(id)
                .map(employee -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return null;
    }
}
