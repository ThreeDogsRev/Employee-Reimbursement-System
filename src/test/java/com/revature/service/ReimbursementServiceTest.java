package com.revature.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.ReimbursementDao;
import com.revature.models.Employee;
import com.revature.models.EmployeeRole;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;

public class ReimbursementServiceTest {

    private ReimbursementService rServ;
    private ReimbursementDao mockDao;

    @Before
    public void setUp() throws Exception {
        this.mockDao = mock(ReimbursementDao.class);
        this.rServ = new ReimbursementService(this.mockDao);
    }

    @After
    public void tearDown() throws Exception {
        this.mockDao = null;
        this.rServ = null;
    }

    @Test
    public void testSubmitReimbursement() {
        int id = 101;
        Reimbursement r = new Reimbursement(12345L, ReimbursementType.OTHER, "Test");
        when(this.mockDao.insert()).thenReturn(id);

        int expected = id;
        int actual = this.rServ.submitReimbursement(r);
        assertEquals(expected, actual);
    }

    public List<Reimbursement> getReimbursementsList() {
        Employee e1 = new Employee(1, "A", "User", "auser", "asdf", "auser@threedogs.tld", EmployeeRole.EMPLOYEE);
        Employee e2 = new Employee(2, "Another", "User", "a2user", "asdf", "a2user@threedogs.tld",
            EmployeeRole.EMPLOYEE);
        Employee m1 = new Employee(3, "A", "Manager", "amanager", "asdf", "amanager@threedogs.tld",
            EmployeeRole.MANAGER);

        List<Reimbursement> rList = new ArrayList<>();
        rList.add(new Reimbursement(1, new Date(), 12345L, "Test item 1", ReimbursementStatus.PENDING,
            ReimbursementType.OTHER, e1, null, null, null));
        rList.add(new Reimbursement(2, new Date(), 200000L, "Test item 2", ReimbursementStatus.APPROVED,
            ReimbursementType.LODGING, e2, new Date(), m1, null));
        rList.add(new Reimbursement(3, new Date(), 3509L, "Catering", ReimbursementStatus.APPROVED,
            ReimbursementType.FOOD, e1, new Date(), m1, null));
        rList.add(new Reimbursement(4, new Date(), 81538L, "Laptop", ReimbursementStatus.PENDING,
            ReimbursementType.OTHER, e1, null, m1, null));
        rList.add(new Reimbursement(5, new Date(), 41317L, "Stand-up desk", ReimbursementStatus.DENIED,
            ReimbursementType.OTHER, e1, new Date(), m1, null));
        return rList;
    }

    @Test
    public void testGetPendingReimbursements_Success() {
        List<Reimbursement> rList = getReimbursementsList();
        when(this.mockDao.findAll()).thenReturn(rList);

        Reimbursement r = rList.get(0);
        Employee e = r.getAuthor();
        List<Reimbursement> expected = new ArrayList<>();
        expected.add(r);
        expected.add(rList.get(3));

        List<Reimbursement> actual = this.rServ.getPendingReimbursements(e);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testGetPendingReimbursements_Success_EmptyList() {
        List<Reimbursement> rList = getReimbursementsList();
        when(this.mockDao.findAll()).thenReturn(rList);

        Reimbursement r = rList.get(1);
        Employee e = r.getAuthor();
        List<Reimbursement> expected = new ArrayList<>();
        List<Reimbursement> actual = this.rServ.getPendingReimbursements(e);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetApprovedReimbursements_Success() {
        List<Reimbursement> rList = getReimbursementsList();
        when(this.mockDao.findAll()).thenReturn(rList);

        Reimbursement r = rList.get(1);
        Employee e = r.getAuthor();
        List<Reimbursement> expected = new ArrayList<>();
        expected.add(r);

        List<Reimbursement> actual = this.rServ.getApprovedReimbursements(e);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testGetApprovedReimbursements_Success_EmptyList() {
        Employee e = new Employee(999, "Frist", "Name", "frist", "frist", "frist@threedogs.tld", EmployeeRole.EMPLOYEE);
        List<Reimbursement> rList = getReimbursementsList();
        when(this.mockDao.findAll()).thenReturn(rList);

        List<Reimbursement> expected = new ArrayList<>();
        List<Reimbursement> actual = this.rServ.getApprovedReimbursements(e);
        assertEquals(expected, actual);
    }

}
