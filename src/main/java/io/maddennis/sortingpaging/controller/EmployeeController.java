package io.maddennis.sortingpaging.controller;

import io.maddennis.sortingpaging.entity.Employee;
import io.maddennis.sortingpaging.exception.RecordNotFoundException;
import io.maddennis.sortingpaging.repository.EmployeeRepository;
import io.maddennis.sortingpaging.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<Employee> list = employeeService.getAllEmployees(pageNo, pageSize, sortBy);

        return new ResponseEntity<List<Employee>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        Employee entity = employeeService.getEmployeeById(id);

        return new ResponseEntity<Employee>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> createOrUpdateEmployee(Employee employee)
            throws RecordNotFoundException {
        Employee updated = employeeService.createOrUpdateEmployee(employee);
        return new ResponseEntity<Employee>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteEmployeeById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        employeeService.deleteEmployeeById(id);
        return HttpStatus.FORBIDDEN;
    }
}
