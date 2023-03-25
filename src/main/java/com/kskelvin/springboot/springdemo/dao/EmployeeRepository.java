package com.kskelvin.springboot.springdemo.dao;


import com.kskelvin.springboot.springdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(path="employees")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // sort by last name
    public List<Employee> findAllByOrderByLastNameAsc();
}
