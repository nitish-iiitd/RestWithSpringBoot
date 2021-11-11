package com.nitish2794.demo.restwithspringboot.controller.rmmlevel0;

import com.nitish2794.demo.restwithspringboot.entity.Employee;
import com.nitish2794.demo.restwithspringboot.exception.ActionInvalidException;
import com.nitish2794.demo.restwithspringboot.exception.EmployeeNotFoundException;
import com.nitish2794.demo.restwithspringboot.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rmm-level0")
@Tag(name="RMM Level 0 Employee Resource APIs")
public class Level0CommonController {

    private final EmployeeRepository employeeRepository;

    Level0CommonController(EmployeeRepository repository) {
        this.employeeRepository = repository;
    }

    @PostMapping("/employeeService")
    @Operation(summary="Single URI for Employee Service")
    Object employeeService(@RequestBody EmployeeServiceRequest employeeServiceRequest) {
        String action = employeeServiceRequest.getAction();
        Employee employeeBody = employeeServiceRequest.getEmployee();
        switch (action){
            case "create" :
                return employeeRepository.save(employeeBody);
            case "read" :
                return employeeRepository.findById(employeeBody.getId())
                        .orElseThrow(() -> new EmployeeNotFoundException(employeeBody.getId()));
            case "readAll" :
                return employeeRepository.findAll();
            case "update" :
                return employeeRepository.findById(employeeBody.getId())
                        .map(employee -> {
                            employee.setName(employeeBody.getName());
                            employee.setRole(employeeBody.getRole());
                            return employeeRepository.save(employee);
                        })
                        .orElseGet(() -> {
                            employeeBody.setId(employeeBody.getId());
                            return employeeRepository.save(employeeBody);
                        });
            case "delete" :
                employeeRepository.deleteById(employeeBody.getId());
            default :
                throw new ActionInvalidException(action);
        }
    }


}
