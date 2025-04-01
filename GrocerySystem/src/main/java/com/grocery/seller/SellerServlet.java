//package com.grocery.seller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebServlet("/ViewProductsServlet")
//public class SellerServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
//
//            while (rs.next()) {
//                out.println("<tr>");
//                out.println("<td>" + rs.getInt("id") + "</td>");
//                out.println("<td>" + rs.getString("pname") + "</td>");
//                out.println("<td>" + rs.getString("pdescription") + "</td>");
//                out.println("<td>" + rs.getInt("pqty") + "</td>");
//                out.println("<td>" + rs.getInt("price") + "</td>");
//                out.println("</tr>");
//            }
//
//            con.close();
//        } catch (ClassNotFoundException | SQLException e) {
//            out.println(e);
//        }
//    }
//}
