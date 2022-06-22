package com.revature.dao;

import java.util.ArrayList;
import java.util.List;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.revature.models.employee.Employee;
import com.revature.models.employee.EmployeeRole;
import com.revature.utils.SessionHelper;

public class EmployeeDao implements IDao<Employee>{

  @Override
  public List<Employee> selectAll() {
    List<Employee> employees = new ArrayList<Employee>();
    try {
      Session session = SessionHelper.getSession();
      Query query = session.createQuery("FROM Employee");
      employees = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return employees;
  }

  @Override
  public Employee selectById(int id) {
    try {
      Session session = SessionHelper.getSession();
      session.beginTransaction();
      Employee entity = (Employee) session.get(Employee.class, id);
      session.getTransaction().commit();
      return entity;
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return null;
  }

  
  public static void main(String[] args) {
    List<Employee> employees;
    EmployeeDao employeeDao = new EmployeeDao();
    Employee employee = new Employee("James", "May", "Top Gear", "Slow", "Email", EmployeeRole.EMPLOYEE);
    employeeDao.insert(employee);
    
    employees = employeeDao.selectAll();
    System.out.println(employees);

    System.out.println("\n\n\n");
    System.out.println("UPDATING");
    employee = employees.get(0);
    employee.setFirstName("John");

    employees = employeeDao.selectAll();
    System.out.println(employees);

    System.out.println("\n\n\n");
    System.out.println("DELETING");
    employee = employees.get(0);
    employeeDao.delete(employee);
    employees = employeeDao.selectAll();
    System.out.println(employees);
  }
}
