package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.revature.models.Employee;
import com.revature.utils.SessionHelper;

public class EmployeeDao implements IDao<Employee> {

    @Override
    public List<Employee> selectAll() {
        List<Employee> employees = new ArrayList<>();
        Session session = null;
        try {
            session = SessionHelper.getSession();
            Query<Employee> query = session.createQuery("FROM Employee");
            employees = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            employee = session.get(Employee.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return employee;
    }

    /**
     * Searches for an employee by their username.
     *
     * @param userName
     * @return The Employee or null if no employee was found.
     */
    @Override
    public Employee selectByUsername(String userName) {
        Employee employee = null;
        try {
            Session session = SessionHelper.getSession();
            session.beginTransaction();
            Query<Employee> query = session.createQuery("FROM Employee WHERE userName = :userName");
            query.setParameter("userName", userName);
            employee = query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return employee;
    }

}
