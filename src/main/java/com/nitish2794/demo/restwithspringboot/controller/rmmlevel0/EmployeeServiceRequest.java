package com.nitish2794.demo.restwithspringboot.controller.rmmlevel0;

import com.nitish2794.demo.restwithspringboot.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeServiceRequest {

    private String action;
    private Employee employee;

}
