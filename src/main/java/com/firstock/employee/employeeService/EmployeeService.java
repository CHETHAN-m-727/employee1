package com.firstock.employee.employeeService;

import com.firstock.employee.entity.Employee;


import java.util.List;

public interface EmployeeService {
    public Employee saveEmp(Employee employee);

    public List<Employee> getAllEmp();

    public Employee getEmpById(int id);

    public void deleteEmp(int id);

    public Employee updateEmp(int id, Employee employee);

    boolean exisById(Integer id);
}
