package com.revature.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.dao.IDao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;

public class ReimbursementService {

    private IDao<Employee> dao;

    public ReimbursementService(IDao<Employee> ed) {
        this.dao = ed;
    }

    public Reimbursement getReimbursement(int id) throws SQLException {
        Optional<Reimbursement> possibleReimbursment = getReimbursements().stream()
            .filter(reimb -> reimb.getId() == id)
            .findFirst();

        if (possibleReimbursment.isPresent()) {
            return possibleReimbursment.get();
        }
        throw new SQLException("No reimbursement with id " + id + " found");
    }

    public List<Reimbursement> getReimbursementsCopy() {
        List<Reimbursement> reimbursements = new ArrayList<>();
        List<Employee> employees = this.dao.selectAll();
        for (Employee employee : employees) {
            for (Reimbursement reimbursement : employee.getReimbursements()) {
                Employee e = new Employee(employee);
                Reimbursement r = new Reimbursement(reimbursement);
                e.setReimbursements(null);
                r.setAuthor(e);
                reimbursements.add(r);
            }
        }
        return reimbursements;
    }

    public List<Reimbursement> getReimbursements() {
        List<Reimbursement> reimbursements = new ArrayList<>();
        List<Employee> employees = this.dao.selectAll();
        for (Employee employee : employees) {
            reimbursements.addAll(employee.getReimbursements());
        }
        return reimbursements;
    }

}
