package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.FakeDao;
import com.revature.models.Employee;
import com.revature.models.EmployeeRole;
import com.revature.service.EmployeeService;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static EmployeeService es = new EmployeeService(new FakeDao());

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

		if (checkName(firstname) || 
				checkName(lastname) || 
				checkUsername(username) || 
				checkEmail(email) ||
				checkPassword(password)) {
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
			resp.setContentType("text/html");
			out.println("<h1>Registration failed.  Username already exists</h1>");
			out.println("<a href=\"index.html\">Back</a>");
		}
	}

	private static boolean checkUsername(String username) {
		return username.matches("[a-zA-Z10-9]{4,24}");
	}
	
	private static boolean checkEmail(String email) {
		return email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
	}

	private static boolean checkPassword(String password) {
		return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$");
	}

	private static boolean checkName(String name) {
		return name.matches("^[a-zA-Z]{2,24}");
	}

}
