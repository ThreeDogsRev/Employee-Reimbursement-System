package com.revature.dao;

import java.util.List;

import com.revature.models.employee.Employee;
import com.revature.models.employee.EmployeeRole;

public class EmployeeDao implements IDao<Employee>{

  @Override
  public List<Employee> selectAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Employee selectById(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Employee update(Employee entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Employee delete(Employee entity) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public static void main(String[] args) {
    EmployeeDao employeeDao = new EmployeeDao();
    Employee employee = new Employee("James", "May", "Top Gear", "Slow", "Email", EmployeeRole.EMPLOYEE);

    System.out.println(employeeDao.insert(employee));
  }
}
