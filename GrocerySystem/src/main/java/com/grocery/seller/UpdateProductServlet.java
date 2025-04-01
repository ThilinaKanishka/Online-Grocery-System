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

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String newName = request.getParameter("newName");
        String newDescription = request.getParameter("newDescription");
        int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));
        int newPrice = Integer.parseInt(request.getParameter("newPrice"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("UPDATE products SET pname=?, pdescription=?, pqty=?, price=? WHERE id=?");
            pstmt.setString(1, newName);
            pstmt.setString(2, newDescription);
            pstmt.setInt(3, newQuantity);
            pstmt.setInt(4, newPrice);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            con.close();
            response.sendRedirect("ViewProductsServlet");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
