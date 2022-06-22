package com.revature.service;

import java.util.List;

import com.revature.dao.ReimbursementDao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;

public class ReimbursementService {

    private ReimbursementDao rDao;

    public ReimbursementService(ReimbursementDao rDao) {
        this.rDao = rDao;
    }

    public int submitReimbursement(Reimbursement r) {
        // TODO Implement .insert() method in DAO layer
        return this.rDao.insert(r);
    }

    public List<Reimbursement> getPendingReimbursements(Employee e) {
        return this.rDao.findAll()
                .stream()
                .filter(r -> (r.getAuthor().equals(e) == id && r.getStatus.equals(ReimbursementStatus.PENDING)))
                .toList();
    }

    public List<Reimbursement> getApprovedReimbursements(Employee e) {
        return this.rDao.findAll()
                .stream()
                .filter(r -> (r.getAuthor().equals(e) && r.getStatus.equals(ReimbursementStatus.APPROVED)))
                .toList();
    }

}
