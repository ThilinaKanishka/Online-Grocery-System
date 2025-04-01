package com.grocery.seller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        float price = Float.parseFloat(request.getParameter("price"));
        String imageUrl = request.getParameter("imageUrl");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO products (pname, pdescription, pqty, price, image_url) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, quantity);
            pstmt.setFloat(4, price);
            pstmt.setString(5, imageUrl);
            pstmt.executeUpdate();
            con.close();
            response.sendRedirect("ViewProductsServlet");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
