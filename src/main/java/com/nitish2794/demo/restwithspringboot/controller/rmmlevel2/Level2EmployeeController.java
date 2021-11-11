package com.nitish2794.demo.restwithspringboot.controller.rmmlevel2;

import java.util.List;

import com.nitish2794.demo.restwithspringboot.entity.Employee;
import com.nitish2794.demo.restwithspringboot.exception.EmployeeNotFoundException;
import com.nitish2794.demo.restwithspringboot.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rmm-level2")
@Tag(name="RMM Level 2 Employee Resource APIs")
public class Level2EmployeeController {

    private final EmployeeRepository repository;

    Level2EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    @Operation(summary="Get all the Employees")
    List<Employee> all() {
        return repository.findAll();
    }

    @PostMapping("/employees")
    @Operation(summary="Create new Employee")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    @Operation(summary="Get an Employee")
    Employee one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    @Operation(summary="Update an Employee")
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

    @DeleteMapping("/employees/{id}")
    @Operation(summary="Delete an Employee")
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
