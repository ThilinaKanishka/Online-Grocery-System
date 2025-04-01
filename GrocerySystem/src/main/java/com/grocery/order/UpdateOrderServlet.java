package com.grocery.order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateOrderServlet")
public class UpdateOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        int newCount = Integer.parseInt(request.getParameter("newCount"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

            // Get the product_id of the order
            PreparedStatement pstmt = con.prepareStatement("SELECT product_id FROM orders WHERE id = ?");
            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            int productId = 0;
            if (rs.next()) {
                productId = rs.getInt("product_id");
            }

            // Get the price of the product from the products table
            pstmt = con.prepareStatement("SELECT price FROM products WHERE id = ?");
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();
            float price = 0;
            if (rs.next()) {
                price = rs.getFloat("price");
            }

            // Update the order
            float productSum = price * newCount;
            pstmt = con.prepareStatement("UPDATE orders SET count = ?, product_sum = ? WHERE id = ?");
            pstmt.setInt(1, newCount);
            pstmt.setFloat(2, productSum);
            pstmt.setString(3, orderId);
            pstmt.executeUpdate();

            // Get the order details from the session
            HttpSession session = request.getSession(true);
            List<Map<String, Object>> orderDetails = (List<Map<String, Object>>) session.getAttribute("orderDetails");

            // Update the order in the session
            if (orderDetails != null) {
                for (Map<String, Object> orderDetail : orderDetails) {
                    if (orderDetail.get("order_id").equals(Integer.parseInt(orderId))) {
                        orderDetail.put("count", newCount);
                        orderDetail.put("product_sum", productSum);
                        break;
                    }
                }
                session.setAttribute("orderDetails", orderDetails);
            }

            // Redirect the user back to the orders page
            response.sendRedirect("order.jsp");

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            response.getWriter().println(e);
        }
    }
}
