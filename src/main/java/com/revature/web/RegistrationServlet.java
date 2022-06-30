package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.dao.Dao;
import com.revature.models.Employee;
import com.revature.models.EmployeeRole;
import com.revature.service.EmployeeService;
import com.revature.utils.FormInputValidator;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static EmployeeService es = new EmployeeService(new Dao());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. extract all values from the parameters
		String firstname = req.getParameter("firstname").toLowerCase();
		String lastname = req.getParameter("lastname").toLowerCase();
		String username = req.getParameter("username");
		String email = req.getParameter("email").toLowerCase();
		String password = req.getParameter("password");

		System.out.println("RegistrationServlet.doPost()");
		System.out.println("firstname: <" + firstname + ">");
		System.out.println("lastname: <" + lastname + ">");
		System.out.println("username: <" + username + ">");
		System.out.println("email: <" + email + ">");
		System.out.println("password: <" + password + ">");

		if (!FormInputValidator.checkName(firstname) ||
				!FormInputValidator.checkName(lastname) ||
				!FormInputValidator.checkUsername(username) ||
				!FormInputValidator.checkEmail(email) ||
				!FormInputValidator.checkPassword(password)) {
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("<h1>Registration failed.  One or more fields missing</h1>");
			out.println("<a href=\"registration.html\">Back</a>");
			return;
		}

		// 2. construct a new employee object
		Employee user = new Employee(firstname, lastname, username, password, email, EmployeeRole.EMPLOYEE);

		// 3. call the register() method from the service layer
		user = es.register(user);
		System.out.println("user: " + user);

		// 4. check it's ID...if it's > 0 it's successfull
		if (user != null) {

			// add the user to the session
			req.getSession().setAttribute("user", user);
			req.getRequestDispatcher("employeeHomePage.html").forward(req, resp);
			// using the request dispatcher, forward the request and response to a new
			// resource...
			// send the user to a new page -- welcome.html

		} else {

			PrintWriter out = resp.getWriter();
			resp.setContentType("text/json");
			out.println("{\"message\":\"Registration failed.  Username already exists\"}");
		}
	}
}
