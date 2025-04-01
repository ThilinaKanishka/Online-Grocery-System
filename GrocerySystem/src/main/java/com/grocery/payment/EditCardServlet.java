package com.grocery.payment;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editCard")
public class EditCardServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String cardNo = request.getParameter("cardNo");
        String expiry = request.getParameter("expiry");
        String cvv = request.getParameter("cvv");
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
            String sql = "UPDATE paymentDetails SET expiry = ?, cvv = ? WHERE cardNo = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, expiry);
            stmt.setString(2, cvv);
            stmt.setString(3, cardNo);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
            	response.sendRedirect("manageCards.jsp");
            } else {
                out.println("<h3>Failed to update card details.</h3>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                out.println("<h3>Error closing database connection: " + e.getMessage() + "</h3>");
            }
            out.close();
        }
    }
}
