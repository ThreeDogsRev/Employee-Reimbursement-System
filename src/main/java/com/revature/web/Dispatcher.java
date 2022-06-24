package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.exceptions.PasswordInvalidException;
import com.revature.exceptions.UserNotFoundException;

/**
 * Servlet implementation class FrontController
 */
public class Dispatcher extends HttpServlet {
	/**
	 * This method will be responsible for determining what resource the client is
	 * requesting
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out;

		// 1. URI rewriting
		// http://localhost:8080/employee-servlet-app/login -- we want to capture login
		// http://localhost:8080/employee-servlet-app/employees -- if they go here it
		// returns all employees in the DB
		final String URI = request.getRequestURI().replace("/employee-reimbursement-system/", "");
		// we're capturing the very last part of the URI

		// set up a switch case statement in which we call the appropriate functionality
		// based on the URI returned
		switch (URI) {
			case "login":
				// invoke some function from the RequestHelper
				try {
					RequestHelper.processLogin(request, response);
				} catch (UserNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (PasswordInvalidException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "employees":
				// invoke some functionality from the request helper which would return all
				// employees
				RequestHelper.processEmployees(request, response);
				break;

			case "register":
				RequestHelper.processRegistration(request, response);
				break;

			case "test":
				out = response.getWriter();
				response.setContentType("text/html");
				out.println("<h1> TEST </h1>");
				break;

			default:
				out = response.getWriter();
				response.setContentType("text/html");
				out.println(request.getRequestURI());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}