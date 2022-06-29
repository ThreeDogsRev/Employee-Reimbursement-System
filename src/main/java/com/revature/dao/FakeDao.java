package com.revature.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.revature.models.*;

public class FakeDao implements IDao<Employee> {
    private static HashMap<Integer, Employee> employees = new HashMap<>();

    public FakeDao() {
        if (employees.isEmpty()){
            Employee e1 = new Employee("Alice", "Able", "atest", "test", "test@test", EmployeeRole.EMPLOYEE);
            Employee e2 = new Employee("Bob", "Bobby", "btest", "test", "test2@test", EmployeeRole.EMPLOYEE);
            e1.addReimbursement(new Reimbursement(1000L, ReimbursementType.FOOD, "Wendies"));
            e1.addReimbursement(new Reimbursement(200000L, ReimbursementType.LODGING, "Hotel California"));
            e1.addReimbursement(new Reimbursement(100000L, ReimbursementType.TRAVEL, "American Airlines"));
            insert(e1);
            insert(e2);
        }
    }

    @Override
    public Employee insert(Employee entity) {
        int id = employees.size();
        entity.setId(id);
        employees.put(id, entity);
        return employees.get(id);
    }

    @Override
    public List<Employee> selectAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public void persist (Employee entity) {
        employees.put(entity.getId(), entity);
    }

    @Override
    public Employee selectById(int id) throws SQLException {
        if(employees.containsKey(id)) {
            return employees.get(id);
        }
        throw new SQLException("employee not found");
    }

    @Override
    public Employee selectByUsername(String userName) {
        for (Employee employee : employees.values()) {
            if (employee.getUsername().equals(userName)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public Employee update(Employee entity) {
        return employees.replace(entity.getId(), entity);
    }


}

