package com.example.spring.springjdbc.employee.dao;

import com.example.spring.springjdbc.employee.dto.Employee;

public interface EmployeeDao {

	int create(Employee employee);

	int update(Employee employee);

	int delete(Employee employee);
}
