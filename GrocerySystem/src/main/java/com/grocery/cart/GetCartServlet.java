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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/GetCartServlet")
public class GetCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

                // Get the cart items from the database using the user id
                PreparedStatement pstmt = con.prepareStatement("SELECT * FROM cart WHERE user_id = ?");
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();

                Map<String, Map<String, Object>> cart = new HashMap<>();

                while (rs.next()) {
                    // Get the product details from the products table
                    PreparedStatement pstmtProduct = con.prepareStatement("SELECT * FROM products WHERE id = ?");
                    pstmtProduct.setInt(1, rs.getInt("product_id"));
                    ResultSet rsProduct = pstmtProduct.executeQuery();

                    if (rsProduct.next()) {
                        // Create a map to store the product data
                        Map<String, String> product = new HashMap<>();
                        product.put("product_name", rsProduct.getString("pname"));
                        product.put("product_description", rsProduct.getString("pdescription"));
                        product.put("product_image", rsProduct.getString("image_url"));
                        product.put("product_price", rsProduct.getString("price"));

                        // Get the count from the cart table
                        int count = rs.getInt("count");

                        // Create a map to store the cart item data
                        Map<String, Object> cartItem = new HashMap<>();
                        cartItem.put("product", product);
                        cartItem.put("count", count);

                        // Add the cart item to the cart
                        cart.put(rs.getString("product_id"), cartItem);
                    }
                }

                // Store the cart in the session
                session.setAttribute("cart", cart);

                con.close();
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException(e);
            }
        }

        // Forward the request to the cart page
        RequestDispatcher dispatcher = request.getRequestDispatcher("cart.jsp");
        dispatcher.forward(request, response);
    }
}

