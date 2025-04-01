package com.grocery.cart;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/getProducts")
public class GetProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM products");
            ResultSet rs = pstmt.executeQuery();

            // Create a list to store the product data
            List<Map<String, String>> products = new ArrayList<>();

            while (rs.next()) {
                // Create a map to store the product data for each row
                Map<String, String> product = new HashMap<>();
                product.put("product_id", String.valueOf(rs.getString("id")));
                product.put("product_name", rs.getString("pname"));
                product.put("product_description", rs.getString("pdescription"));
                product.put("product_image", rs.getString("image_url"));
                product.put("product_price", rs.getString("price"));

                // Add the map to the list
                products.add(product);
            }

            if (!products.isEmpty()) {
                // Debug statement to check the size of the products list
                System.out.println("Number of products fetched: " + products.size());

                // Get the current session or create one if it doesn't exist
                HttpSession session = request.getSession(true);

                // Store the product data in the session
                session.setAttribute("products", products);
                con.close();
            } else {
                out.println("No products found.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println(e);
        }
    }
}
