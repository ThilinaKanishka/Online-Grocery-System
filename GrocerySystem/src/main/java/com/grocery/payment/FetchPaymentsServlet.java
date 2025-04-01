package com.grocery.payment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/fetchPayments")
public class FetchPaymentsServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC driver
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

            // SQL query to fetch payment details
            String sql = "SELECT cardNo FROM paymentDetails";
            stmt = conn.prepareStatement(sql);

            // Execute the query
            rs = stmt.executeQuery();

            // Write HTML response directly with retrieved data
            out.println("<option value=''>Select a saved payment</option>");
            while (rs.next()) {
                String cardNo = rs.getString("cardNo");
                out.println("<option value='" + cardNo + "'>" + cardNo + "</option>");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("<option value=''>Error fetching payment details</option>");
        } finally {
            // Close JDBC objects
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
