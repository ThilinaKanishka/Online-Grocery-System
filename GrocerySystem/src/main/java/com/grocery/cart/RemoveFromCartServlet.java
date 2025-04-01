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

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");

        HttpSession session = request.getSession(true);
        int userId = (int) session.getAttribute("userId");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

            // Remove the product from the cart table
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM cart WHERE user_id = ? AND product_id = ?");
            pstmt.setInt(1, userId);
            pstmt.setString(2, productId);
            pstmt.executeUpdate();

            // Update the cart object in the session
            Map<String, Map<String, Object>> cart = (Map<String, Map<String, Object>>) session.getAttribute("cart");
            if (cart != null) {
                cart.remove(productId);
                session.setAttribute("cart", cart);
            }

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            response.getWriter().println(e);
        }

        response.sendRedirect("cart.jsp");
    }
}




