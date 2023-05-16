package com.example.spring.springjdbc.employee.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.spring.springjdbc.employee.dao.EmployeeDao;
import com.example.spring.springjdbc.employee.dto.Employee;

public class Test {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"com/example/spring/springjdbc/employee/test/config.xml");
		EmployeeDao dao = (EmployeeDao) context.getBean("employeedao");
		Employee employee=new Employee();
		employee.setId(3);
		employee.setFname("Harsh");
		employee.setLname("Sharma");
		int result=dao.create(employee);
//		int result=dao.update(employee);
//		int result=dao.delete(employee);
		System.out.println("Number of records updated are: "+result);
	}
}
