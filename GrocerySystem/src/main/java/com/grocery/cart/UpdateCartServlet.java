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

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        int count = Integer.parseInt(request.getParameter("count"));

        HttpSession session = request.getSession(true);
        int userId = (int) session.getAttribute("userId");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

            // Get the price of the product
            PreparedStatement pstmt = con.prepareStatement("SELECT price FROM products WHERE id = ?");
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();
            float price = 0;
            if (rs.next()) {
                price = rs.getFloat("price");
            }

            // Update the count and product_sum in the cart table
            float productSum = price * count;
            pstmt = con.prepareStatement("UPDATE cart SET count = ?, product_sum = ? WHERE user_id = ? AND product_id = ?");
            pstmt.setInt(1, count);
            pstmt.setFloat(2, productSum);
            pstmt.setInt(3, userId);
            pstmt.setString(4, productId);
            pstmt.executeUpdate();
            
            // Update the cart object in the session
            Map<String, Map<String, Object>> cart = (Map<String, Map<String, Object>>) session.getAttribute("cart");
            if (cart != null) {
                Map<String, Object> productDetails = cart.get(productId);
                if (productDetails != null) {
                    productDetails.put("count", count);
                    productDetails.put("product_sum", productSum);
                    cart.put(productId, productDetails);
                    session.setAttribute("cart", cart);
                }
            }

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            response.getWriter().println(e);
        }

        response.sendRedirect("cart.jsp");
    }
}



