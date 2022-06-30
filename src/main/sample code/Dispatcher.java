package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.revature.exceptions.PasswordInvalidException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Employee;

/**
 * Servlet implementation class FrontController
 */
public class Dispatcher extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// set up a switch case statement in which we call the appropriate functionality
		// based on the URI returned
		String path = request.getServletPath() == null ? "/" : request.getServletPath();
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute("the-user");


		switch (path) {
			case "/login":
				try {
					RequestHelper.processLogin(request, response);
				} catch (UserNotFoundException e) {
					write(request, response, "<p>User not found</p>");
					e.printStackTrace();
				} catch (PasswordInvalidException e) {
					write(request, response, "<p>Password is invalid</p>");
					e.printStackTrace();
				}
				break;

			case "/employees":
				RequestHelper.processEmployees(request, response);
				break;

			case "/register":
				RequestHelper.processRegistration(request, response);
				break;

			default:
				write(request, response,
						"<p>" + path + " is not a valid URI</p>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	void write(HttpServletRequest request, HttpServletResponse response, String s) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println(s);
	}
}