package com.revature.service;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;

public class EmployeeService {

    private EmployeeDao eDao;

    public EmployeeService(EmployeeDao eDao) {
        this.eDao = eDao;
    }

    public Employee login(String username, String password) {
        /*
         * TODO Implement selectByUsername in DAO layer
         * - Returns an Employee instance
         * - Login successful if Employee.id > 0, 0 (default int value) otherwise
         */
        return this.eDao.selectByUsername(username);
    }

    public Employee getEmployeeInfo(int id) {
        return this.eDao.selectById(id);
    }

    public Employee updateEmployeeInfo(Employee emp) {
        return this.eDao.update(emp);
    }
}
