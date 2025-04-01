package com.grocery.order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/PlaceOrderServlet")
public class PlaceOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

            // Get the user id from the session
            HttpSession session = request.getSession(true);
            int userId = (int) session.getAttribute("userId");

            // Get the cart items for the user
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM cart WHERE user_id = ?");
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            // Create the orders table if it doesn't exist
            PreparedStatement createTableStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS orders (id INT AUTO_INCREMENT, user_id INT, product_id INT, count INT, product_sum DOUBLE, PRIMARY KEY(id))");
            createTableStmt.execute();

            // Insert the cart items into the orders table
            while (rs.next()) {
                String productId = rs.getString("product_id");
                int count = rs.getInt("count");
                float productSum = rs.getFloat("product_sum");

                PreparedStatement insertOrderStmt = con.prepareStatement("INSERT INTO orders (user_id, product_id, count, product_sum) VALUES (?, ?, ?, ?)");
                insertOrderStmt.setInt(1, userId);
                insertOrderStmt.setString(2, productId);
                insertOrderStmt.setInt(3, count);
                insertOrderStmt.setFloat(4, productSum);
                insertOrderStmt.execute();
            }

            // Clear the cart for the user
            PreparedStatement clearCartStmt = con.prepareStatement("DELETE FROM cart WHERE user_id = ?");
            clearCartStmt.setInt(1, userId);
            clearCartStmt.execute();

            // Redirect the user back to the product page
            response.sendRedirect("index.jsp");

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            response.getWriter().println(e);
        }
    }
}
