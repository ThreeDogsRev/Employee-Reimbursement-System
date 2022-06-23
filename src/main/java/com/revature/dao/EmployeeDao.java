package com.revature.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.revature.models.Employee;
import com.revature.models.EmployeeRole;
import com.revature.models.Reimbursement;
import com.revature.utils.SessionHelper;


public class EmployeeDao extends Dao<Employee> {

  public EmployeeDao() {
    super();
  }

  @Override
  public List<Employee> selectAll() {
    List<Employee> employees = new ArrayList<Employee>();
    Session session = null;
    try {
      session = SessionHelper.getSession();
      Query query = session.createQuery("FROM Employee");
      employees = query.list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      try {if(session != null) session.close();} catch(Exception ex) {}
    }
    return employees;
  }

  @Override
  public Employee selectById(int id) {
    Employee employee = null;
    Session session = null;
    try {
      session = SessionHelper.getSession();
      session.beginTransaction();
      employee = (Employee) session.get(Employee.class, id);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      try {if(session != null) session.close();} catch(Exception ex) {}
    }
    return employee;
  }

  /**
   * Searches for an employee by their username.
   * 
   * @param userName
   * @return The Employee or null if no employee was found.
   */
  public Employee selectByUsername(String userName) {
    Employee employee = null;
    try {
      Session session = SessionHelper.getSession();
      session.beginTransaction();
      Query query = session.createQuery("FROM Employee WHERE userName = :userName");
      query.setParameter("userName", userName);
      employee = (Employee) query.uniqueResult();
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return employee;
  }

  public static void main(String[] args) {
    List<Employee> employees;
    EmployeeDao ed = new EmployeeDao();
    Employee employee = new Employee("James", "May", "Top Gear", "Slow", "Email", EmployeeRole.EMPLOYEE);
    int id = ed.insert(employee).getId();

    System.out.println();
    System.out.println("Inserted employee with id: " + id);
    System.out.println();
    employees = ed.selectAll();
    System.out.println(employees);

    System.out.println("\n\n\n");
    System.out.println("UPDATING");
    employee = employees.get(0);
    employee.setFirstName("John");

    employees = ed.selectAll();
    System.out.println(employees);

    System.out.println("\n\n\n");
    System.out.println("DELETING");
    employee = employees.get(0);
    ed.delete(employee);
    employees = ed.selectAll();
    System.out.println(employees);
  }
}
