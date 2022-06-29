package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.FakeDao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;
import com.revature.service.EmployeeService;
import com.revature.service.ReimbursementService;

@WebServlet("/submitReimbursement")
public class SubmitReimbursementServlet extends HttpServlet {

    private static EmployeeService es = new EmployeeService(new FakeDao());
    private static ReimbursementService rs = new ReimbursementService(new FakeDao());
    private static ObjectMapper om = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

        // TODO: send HTTP 400 (bad request) if any of the required params are missing
        Employee e = (Employee) req.getSession().getAttribute("user");
        String username = req.getParameter("username");
        long amount = (long) (Double.parseDouble(req.getParameter("amount")) * 100);
        ReimbursementType rType = ReimbursementType.valueOf(req.getParameter("type"));
        String desc = req.getParameter("description");

        Reimbursement r = new Reimbursement(amount, rType, desc);
        r.setAuthor(e);
        e.addReimbursement(r);
        req.setAttribute("message", "Reimbursement request submitted");
        resp.sendRedirect("employeeHome");
    }
}
