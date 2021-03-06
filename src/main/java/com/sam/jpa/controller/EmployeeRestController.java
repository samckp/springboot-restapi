package com.sam.jpa.controller;

import com.sam.jpa.dao.Employee;
import com.sam.jpa.exception.EmpNotFoundException;
import com.sam.jpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeRestController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping(path = "/emps")
    public ResponseEntity<List<Employee>> getEmployees(){

        List<Employee> emps = employeeRepository.findAll();
        return new ResponseEntity<List<Employee>>(emps, HttpStatus.OK);
    }

    @GetMapping(path = "/emp/{id}")
    public ResponseEntity<Optional<Employee> > getEmpById(@PathVariable(name = "id") Long empId){

        Optional<Employee>emp = employeeRepository.findById(empId);

        if(!emp.isPresent()){
            throw new EmpNotFoundException(empId + " EmployeeId Not found");
        }
        return new ResponseEntity<Optional<Employee> >(emp, HttpStatus.OK);
    }

    @PostMapping(path = "/emp")
    public ResponseEntity<Void>  createEmp(@Valid @RequestBody Employee emp){

        if(emp != null){
            employeeRepository.save(emp);
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping(path = "/emp/{id}")
    public void updateEmp(@RequestBody Employee emp, @PathVariable(value = "id") Long empId){

        Optional<Employee> employee = employeeRepository.findById(empId);

        emp.setId(empId);

        if(employee != null){
            employeeRepository.save(emp);
        }
    }

    @DeleteMapping(path = "/emp/{id}")
    public void deleteEmp(@PathVariable(value = "id") Long empId){

        Optional<Employee> employee = employeeRepository.findById(empId);
        if (employee != null){

            employeeRepository.deleteById(empId);
        }
    }
}
