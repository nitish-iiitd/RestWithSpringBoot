package com.nitish2794.demo.restwithspringboot.controller.rmmlevel3;

import com.nitish2794.demo.restwithspringboot.entity.Employee;
import com.nitish2794.demo.restwithspringboot.exception.EmployeeNotFoundException;
import com.nitish2794.demo.restwithspringboot.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rmm-level3")
@Tag(name="RMM Level 3 Employee Resource APIs")
public class Level3EmployeeController {

    private final EmployeeRepository repository;

    Level3EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    @Operation(summary="Get all the Employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(employee -> EntityModel.of(employee,
                        linkTo(methodOn(Level3EmployeeController.class).one(employee.getId())).withSelfRel(),
                        linkTo(methodOn(Level3EmployeeController.class).all()).withRel("employees")))
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(Level3EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    @Operation(summary="Create new Employee")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    @Operation(summary="Get an Employee")
    EntityModel<Employee> one(@PathVariable Long id) {

        Employee employee = repository.findById(id) //
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return EntityModel.of(employee, //
                linkTo(methodOn(Level3EmployeeController.class).one(id)).withSelfRel(),
                linkTo(methodOn(Level3EmployeeController.class).all()).withRel("employees"));
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
