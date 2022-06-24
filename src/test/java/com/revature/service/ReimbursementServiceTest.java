package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.dao.Dao;
import com.revature.models.*;

class ReimbursementServiceTest {

    private ReimbursementService rServ;
    private Dao mockDao;
    private List<Employee> mockEmps;
    private List<Reimbursement> mockReimbursements;

    @BeforeEach
    void setUp() throws Exception {
        // Set up mock employees
        Employee e1 = new Employee(1, "Steve", "Threedogs", "sabersams", "nikonikoni", "saber@threedogs.tld",
            EmployeeRole.ADMIN);
        Employee e2 = new Employee(2, "Timothy", "Threedogs", "tjb", "asdf", "tj@threedogs.tld", EmployeeRole.EMPLOYEE);
        Employee e3 = new Employee(3, "Chris", "Threedogs", "chrisv", "12345", "chrisv@threedogs.tld",
            EmployeeRole.MANAGER);

        this.mockEmps = new ArrayList<>();
        this.mockEmps.addAll(Arrays.asList(e2, e1, e3));

        // Set up mock reimbursements
        this.mockReimbursements = new LinkedList<>();
        Reimbursement r1 = new Reimbursement(1, new Date(), 12345L, "Train ticket", ReimbursementStatus.PENDING,
            ReimbursementType.TRAVEL, e2, null, null, null);
        Reimbursement r2 = new Reimbursement(2, new Date(), 5656L, "Catering", ReimbursementStatus.APPROVED,
            ReimbursementType.FOOD, e3, new Date(), e1, null);
        Reimbursement r3 = new Reimbursement(3, new Date(), 90000L, "Outside-work expenses", ReimbursementStatus.DENIED,
            ReimbursementType.OTHER, e2, new Date(), e3, null);
        this.mockReimbursements.addAll(Arrays.asList(r1, r2, r3));
        for (Reimbursement r : this.mockReimbursements) {
            r.getAuthor().addReimbursement(r);
        }

        this.mockDao = mock(Dao.class);
        this.rServ = new ReimbursementService(this.mockDao);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.rServ = null;
        this.mockDao = null;
        this.mockEmps = null;
    }

    @Test
    void testGetReimbursements() {
        when(this.mockDao.selectAll()).thenReturn(this.mockEmps);

        List<Reimbursement> expected = this.mockReimbursements;
        List<Reimbursement> actual = this.rServ.getReimbursements();
        assertAll(() -> expected.forEach(r -> assertTrue(actual.contains(r))));
    }

    @Test
    void testGetReimbursements_EmptyList() {
        // Clears all mock reimbursement records from each Employee
        for (Employee employee : this.mockEmps) {
            employee.setReimbursements(new ArrayList<Reimbursement>());
        }
        when(this.mockDao.selectAll()).thenReturn(this.mockEmps);

        Reimbursement[] expected = {};
        Object[] actual = this.rServ.getReimbursements().toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetPendingReimbursements() {
        when(this.mockDao.selectAll()).thenReturn(this.mockEmps);

        Reimbursement[] expected = { this.mockReimbursements.get(0) };
        Object[] actual = this.rServ.getPendingReimbursements().toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetPendingReimbursements_EmptyList() {
        Reimbursement r = this.mockReimbursements.get(0);
        r.getAuthor().getReimbursements().remove(r);
        this.mockReimbursements.remove(0);
        when(this.mockDao.selectAll()).thenReturn(this.mockEmps);

        Reimbursement[] expected = {};
        Object[] actual = this.rServ.getPendingReimbursements().toArray();
        assertArrayEquals(expected, actual);

        r = null;
    }

}
