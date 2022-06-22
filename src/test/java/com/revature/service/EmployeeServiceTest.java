package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.models.EmployeeRole;

public class EmployeeServiceTest {

    private EmployeeService eServ;
    private EmployeeDao mockDao;

    @Before
    public void setUp() throws Exception {
        this.mockDao = mock(EmployeeDao.class);
        this.eServ = new EmployeeService(this.mockDao);
    }

    @After
    public void tearDown() throws Exception {
        this.mockDao = null;
        this.eServ = null;
    }

    /**
     * Generates a fake list of employees.
     */
    private List<Employee> getFakeEmployees() {
        List<Employee> emps = new ArrayList<>();
        emps.add(
            new Employee(1, "Nico", "Yazawa", "niconico", "niconiconi", "nico@threedogs.tld", EmployeeRole.MANAGER));
        emps.add(
            new Employee(2, "Kotori", "Minami", "kotori", "chunchun", "kotori@threedogs.tld", EmployeeRole.EMPLOYEE));
        return emps;
    }

    @Test
    public void testLogin_Success() {
        List<Employee> emps = getFakeEmployees();
        Employee e1 = emps.get(0);

        when(this.mockDao.selectByUsername()).thenReturn(e1);

        Employee expected = e1;
        Employee actual = this.eServ.login(e1.getUserName(), e1.getPassword());
        assertEquals(expected, actual);
    }

    @Test
    public void testLogin_Fail_IncorrectPassword() {
        List<Employee> emps = getFakeEmployees();
        Employee e1 = emps.get(0);

        when(this.mockDao.selectByUsername()).thenReturn(e1);

        Employee expected = new Employee();
        Employee actual = this.eServ.login(e1.getUserName(), "bestidol1234");
        assertEquals(expected, actual);
    }

    @Test
    public void testLogin_Fail_IncorrectUsername() {
        List<Employee> emps = getFakeEmployees();
        Employee e1 = emps.get(1);

        when(this.mockDao.selectByUsername()).thenReturn(e1);

        Employee expected = new Employee();
        Employee actual = this.eServ.login("boilerplate", "sucks");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetEmployeeInfo_Success() {
        Employee e = new Employee(100, "Steven", "ThreeDogs", "steve", "sabersams", "sabersams@threedogs.tld",
                EmployeeRole.ADMIN);

        when(this.mockDao.selectById()).thenreturn(e);

        Employee expected = e;
        Employee actual = this.eServ.getEmployeeInfo(e.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetEmployeeInfo_Fail_UnknownUser() {
        Employee expected = new Employee();
        when(this.mockDao.selectById()).thenreturn(expected);

        Employee actual = this.eServ.getEmployeeInfo(999);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateEmployeeInfo_Success() {
        Employee e = new Employee(100, "Timothy", "ThreeDogs", "timothy", "acarasimon96", "acarasimon96@threedogs.tld",
                EmployeeRole.ADMIN);

        when(this.mockDao.update()).thenreturn(true);

        Employee expected = e;
        Employee actual = this.eServ.getEmployeeInfo(e.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateEmployeeInfo_Fail_UnknownUser() {
        Employee e = new Employee(100, "Chris", "ThreeDogs", "chris", "12345678", "chrisv@threedogs.tld",
                EmployeeRole.ADMIN);

        when(this.mockDao.update()).thenreturn(true);

        Employee expected = e;
        Employee actual = this.eServ.getEmployeeInfo(e.getId());
        assertEquals(expected, actual);
    }

}
