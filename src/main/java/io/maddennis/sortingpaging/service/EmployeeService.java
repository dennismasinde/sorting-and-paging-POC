package io.maddennis.sortingpaging.service;

import io.maddennis.sortingpaging.entity.Employee;
import io.maddennis.sortingpaging.exception.RecordNotFoundException;
import io.maddennis.sortingpaging.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Employee> pagedResult = employeeRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Employee>();
        }
    }

    public Employee getEmployeeById(Long id) throws RecordNotFoundException {
        return employeeRepository
                .findById(id).orElseThrow(() -> new RecordNotFoundException(
                        "No employee record exist for given id"));

    }

    public Employee createOrUpdateEmployee(Employee entity) throws RecordNotFoundException
    {
        Optional<Employee> employee = employeeRepository.findById(entity.getId());

        if(employee.isPresent())
        {
            Employee newEntity = employee.get();
            newEntity.setEmail(entity.getEmail());
            newEntity.setFirstName(entity.getFirstName());
            newEntity.setLastName(entity.getLastName());

            newEntity = employeeRepository.save(newEntity);

            return newEntity;
        } else {
            entity = employeeRepository.save(entity);

            return entity;
        }
    }

    public void deleteEmployeeById(Long id) throws RecordNotFoundException
    {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(employee.isPresent())
        {
            employeeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }


}
