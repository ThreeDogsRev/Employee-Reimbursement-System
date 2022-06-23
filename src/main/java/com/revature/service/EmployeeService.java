package com.revature.service;

import java.util.List;
import java.util.Optional;

import com.revature.dao.Dao;
import com.revature.exceptions.PasswordInvalidException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.*;

public class EmployeeService {

  private Dao dao;

  public EmployeeService(Dao ed) {
    this.dao = ed;
  }

  public List<Employee> getEmployees() {
    return dao.selectAll();
  }

  public Optional<Employee> getEmployee(int id) {
    return Optional.ofNullable(dao.selectById(id));
  }

  public Employee getEmployee(String username) throws UserNotFoundException {
    Employee emp = dao.selectByUsername(username);
    if (emp == null) {
      throw new UserNotFoundException("Employee " + username + " not found");
    }
    return emp;
  }

  public Employee createEmployee(Employee employee) {
    return dao.insert(employee);
  }

  public Employee updateEmployee(Employee employee) {
    return dao.update(employee);
  }

  public Employee deleteEmployee(Employee employee){
    return dao.delete(employee);
  }

  public Employee confirmLogin(String username, String password) 
    throws UserNotFoundException, PasswordInvalidException{
    if(password.equals(getEmployee(username).getPassword()))  {
        return getEmployee(username);
    }
    throw new PasswordInvalidException("Password is invalid");
  }
}