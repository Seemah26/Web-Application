package com.bridgelabz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.RepositoryIdHelper;

public class LoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("Name");
		String password = request.getParameter("password");
		PreparedStatement stmt = null;
		Connection con = null;
		ResultSet rs = null;
		String query = "select * from LOGININFORMATION where Name= ? and password= ?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("class loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/seema", "root", "root");
			System.out.println("connected...");
			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, password);

			rs = stmt.executeQuery();
			if (rs.next()) {
				String user = rs.getString(5);
				System.out.println("UserId: " + user);
				PrintWriter out = response.getWriter();
				out.println("Welcome   " + user);

			} else {
				System.out.println("invalid user");
				RequestDispatcher rqst = request.getRequestDispatcher("/Registration.jsp");
				rqst.forward(request, response);
			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

	}
}

