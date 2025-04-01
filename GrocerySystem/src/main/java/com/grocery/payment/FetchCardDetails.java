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

@WebServlet("/fetchCardDetails")
public class FetchCardDetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

            String sql = "SELECT expiry, cvv FROM paymentDetails WHERE cardNo=?";
            stmt = conn.prepareStatement(sql);
            String cardNo = request.getParameter("cardNo");
            stmt.setString(1, cardNo);
            
            System.out.println(cardNo);

            rs = stmt.executeQuery();

            if (rs.next()) {
                String expiryDate = rs.getString("expiry");
                String cvv = rs.getString("cvv");
                
                out.print(expiryDate + "|" + cvv);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Error fetching payment details.");
        } finally {
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
