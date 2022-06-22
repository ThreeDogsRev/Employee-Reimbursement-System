package com.revature.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.*;
import org.junit.*;

import com.revature.dao.EmployeeDao;
import com.revature.models.employee.Employee;

public class EmployeeServiceTest {
  
  @Test
  public void testConfirmLogin() {
    EmployeeService eserv = new EmployeeService(new EmployeeDao());
    Employee e = eserv.confirmLogin("admin", "password");
    assertEquals(1, e.getId());
  }

}
