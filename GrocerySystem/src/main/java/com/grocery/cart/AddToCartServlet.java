package com.grocery.cart;

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

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

            // Get the user id from the session
            HttpSession session = request.getSession(true);
            int userId = (int) session.getAttribute("userId");

            // Get the price of the product
            PreparedStatement pstmt = con.prepareStatement("SELECT price FROM products WHERE id = ?");
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();
            float price = 0;
            if (rs.next()) {
                price = rs.getFloat("price");
            }

            // Check if the product is already in the cart
            pstmt = con.prepareStatement("SELECT * FROM cart WHERE user_id = ? AND product_id = ?");
            pstmt.setInt(1, userId);
            pstmt.setString(2, productId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // If the product is already in the cart, increment the count
                int count = rs.getInt("count");
                float productSum = rs.getFloat("product_sum");
                pstmt = con.prepareStatement("UPDATE cart SET count = ?, product_sum = ? WHERE user_id = ? AND product_id = ?");
                pstmt.setInt(1, count + 1);
                pstmt.setFloat(2, productSum + (price * count));
                pstmt.setInt(3, userId);
                pstmt.setString(4, productId);
                pstmt.executeUpdate();
            } else {
                // If the product is not in the cart, add it
                pstmt = con.prepareStatement("INSERT INTO cart (user_id, product_id, count, product_sum) VALUES (?, ?, 1, ?)");
                pstmt.setInt(1, userId);
                pstmt.setString(2, productId);
                pstmt.setFloat(3, price);
                pstmt.executeUpdate();
            }

            // Redirect the user back to the product page
            response.sendRedirect("index.jsp");

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            response.getWriter().println(e);
        }
    }
}

