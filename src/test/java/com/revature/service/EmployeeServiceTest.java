package com.revature.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.revature.dao.Dao;
import com.revature.exceptions.PasswordInvalidException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Employee;
import com.revature.models.EmployeeRole;

@TestInstance(Lifecycle.PER_CLASS)
class EmployeeServiceTest {

    private EmployeeService eServ;
    private Dao mockDao;
    private List<Employee> mockData;

    @BeforeAll
    void setUpMockData() throws Exception {
        this.mockData = new ArrayList<>();
        this.mockData.add(new Employee(1, "Steve", "Threedogs", "sabersams", "nikonikoni", "saber@threedogs.tld",
            EmployeeRole.ADMIN));
        this.mockData.add(
            new Employee(2, "Timothy", "Threedogs", "tjb", "asdf", "tj@threedogs.tld", EmployeeRole.EMPLOYEE));
        this.mockData.add(
            new Employee(3, "Chris", "Threedogs", "chrisv", "12345", "chrisv@threedogs.tld", EmployeeRole.MANAGER));
    }

    @BeforeEach
    void setUp() throws Exception {
        this.mockDao = mock(Dao.class);
        this.eServ = new EmployeeService(this.mockDao);
    }

    @AfterEach
    void tearDown() throws Exception {
        this.eServ = null;
        this.mockDao = null;
    }

    @Test
    void testGetEmployees() {
        when(this.mockDao.selectAll()).thenReturn(this.mockData);
        List<Employee> expected = this.mockData;
        List<Employee> actual = this.eServ.getEmployees();
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void testGetEmployee_ById_Success() {
        Employee e = this.mockData.get(0);
        int id = e.getId();

        when(this.mockDao.selectById(id)).thenReturn(e);
        Employee expected = e;
        Optional<Employee> actual = this.eServ.getEmployee(id);

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testGetEmployee_ById_Fail_IdNotFound() {
        int id = 999;

        when(this.mockDao.selectById(id)).thenReturn(null);
        Optional<Employee> actual = this.eServ.getEmployee(id);

        assertFalse(actual.isPresent());
        assertThrows(NoSuchElementException.class, () -> actual.get());
    }

    @Test
    void testGetEmployee_ByUsername_Success() throws UserNotFoundException {
        Employee e = this.mockData.get(1);
        String username = e.getUsername();

        when(this.mockDao.selectByUsername(username)).thenReturn(e);
        Employee expected = e;
        Employee actual = this.eServ.getEmployee(username);

        assertEquals(expected, actual);
    }

    @Test
    void testGetEmployee_ByUsername_Fail_UsernameNotFound() {
        String username = "auser";
        when(this.mockDao.selectByUsername(username)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> this.eServ.getEmployee(username));
    }

    @Test
    void testCreateEmployee_Success() {
        Employee e = new Employee(this.mockData.size() + 1, "Nico", "Yazawa", "niconiconi", "nikonikoni",
            "nico@threedogs.tld", EmployeeRole.EMPLOYEE);
        when(this.mockDao.insert(e)).thenReturn(e);

        Employee expected = e;
        Employee actual = this.eServ.createEmployee(e);

        assertEquals(expected, actual);
    }

    @Test
    void testCreateEmployee_Fail_EmployeeExists() {
        Employee e = this.mockData.get(0);
        e.setId(0);
        when(this.mockDao.insert(e)).thenReturn(null);
        assertNull(this.eServ.createEmployee(e));
    }

    @Test
    void testUpdateEmployee_Success() {
        Employee e = this.mockData.get(1);
        e.setPassword("ASDF1235asdf");
        e.setEmail("timothyb@threedogs.tld");
        when(this.mockDao.update(e)).thenReturn(e);

        Employee expected = e;
        Employee actual = this.eServ.updateEmployee(e);

        assertEquals(expected, actual);
    }

    @Test
    void testUpdateEmployee_Fail_EmployeeNotFound() {
        Employee e = new Employee(this.mockData.size() + 1, "Nico", "Yazawa", "niconiconi", "nikonikoni",
            "nico@threedogs.tld", EmployeeRole.EMPLOYEE);
        when(this.mockDao.update(e)).thenReturn(null);

        assertNull(this.eServ.updateEmployee(e));
    }

    @Test
    void testDeleteEmployee_Success() {
        Employee e = this.mockData.get(2);
        when(this.mockDao.delete(e)).thenReturn(e);

        Employee expected = e;
        Employee actual = this.eServ.deleteEmployee(e);

        assertEquals(expected, actual);
    }

    @Test
    void testDeleteEmployee_Fail_EmployeeNotFound() {
        Employee e = new Employee(this.mockData.size() + 1, "Nico", "Yazawa", "niconiconi", "nikonikoni",
            "nico@threedogs.tld", EmployeeRole.EMPLOYEE);
        when(this.mockDao.delete(e)).thenReturn(null);

        assertNull(this.eServ.deleteEmployee(e));
    }

    @Test
    void testConfirmLogin_Success() throws UserNotFoundException, PasswordInvalidException {
        Employee e = this.mockData.get(0);
        String username = e.getUsername();
        when(this.mockDao.selectByUsername(username)).thenReturn(e);

        Employee expected = e;
        Employee actual = this.eServ.confirmLogin(username, e.getPassword());
        assertEquals(expected, actual);
    }

    @Test
    void testConfirmLogin_Fail_WrongPassword() {
        Employee e = this.mockData.get(0);
        String username = e.getUsername();
        String password = "WrongPassword";
        when(this.mockDao.selectByUsername(username)).thenReturn(e);

        assertThrows(PasswordInvalidException.class, () -> this.eServ.confirmLogin(username, password));
    }

    @Test
    void testConfirmLogin_Fail_UsernameNotFound() {
        String username = "auser";
        String password = "asdf";
        when(this.mockDao.selectByUsername(username)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> this.eServ.confirmLogin(username, password));
    }

}
