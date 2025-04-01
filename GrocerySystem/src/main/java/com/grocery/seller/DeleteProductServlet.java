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

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM products WHERE id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            con.close();
            response.sendRedirect("ViewProductsServlet");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
