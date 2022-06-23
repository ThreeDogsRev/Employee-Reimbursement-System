package com.revature.service;

import java.util.List;
import java.util.Optional;

import com.revature.dao.EmployeeDao;
import com.revature.exceptions.PasswordInvalidException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.*;

public class EmployeeService {

  private EmployeeDao ed;

  public EmployeeService(EmployeeDao ed) {
    this.ed = ed;
  }

  public List<Employee> getEmployees() {
    return ed.selectAll();
  }

  public Optional<Employee> getEmployee(int id) {
    return Optional.ofNullable(ed.selectById(id));
  }

  public Employee getEmployee(String username) throws UserNotFoundException {
    Employee e = ed.selectByUsername(username);
    if (e == null) {
      throw new UserNotFoundException("Employee " + username + " not found");
    }
    return e;
  }

  public Employee createEmployee(Employee employee) {
    return ed.insert(employee);
  }

  public Employee updateEmployee(Employee employee) {
    return ed.update(employee);
  }

  public Employee deleteEmployee(Employee employee){
    return ed.delete(employee);
  }

  public Employee confirmLogin(String username, String password) 
    throws UserNotFoundException, PasswordInvalidException{
    if(password.equals(getEmployee(username).getPassword()))  {
        return getEmployee(username);
    }
    throw new PasswordInvalidException("Password is invalid");
  }
}