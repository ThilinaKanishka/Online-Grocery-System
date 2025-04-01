package com.grocery.seller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewProductsServlet")
public class ViewProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM products");
            ResultSet rs = pstmt.executeQuery();

            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("pname"));
                product.setDescription(rs.getString("pdescription"));
                product.setQuantity(rs.getInt("pqty"));
                product.setPrice(rs.getFloat("price"));
                product.setImageUrl(rs.getString("image_url"));
                productList.add(product);
            }

            if (!productList.isEmpty()) {
                request.setAttribute("productList", productList);
                con.close();
                request.getRequestDispatcher("seller.jsp").forward(request, response);
            } else {
                // Handle case where productList is empty
                // For example, forward to an error page or display a message
                out.println("No products found.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && !action.isEmpty()) {
            if (action.equals("update")) {
                updateProduct(request, response);
            } else if (action.equals("delete")) {
                deleteProduct(request, response);
            }
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = 0;
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int quantity = 0;
        float price = 0;
        String imageUrl = request.getParameter("imageUrl");

        // Validate and parse productId
        String productIdStr = request.getParameter("productId");
        if (productIdStr != null && !productIdStr.isEmpty()) {
            productId = Integer.parseInt(productIdStr);
        }

        // Validate and parse quantity
        String quantityStr = request.getParameter("quantity");
        if (quantityStr != null && !quantityStr.isEmpty()) {
            quantity = Integer.parseInt(quantityStr);
        }

        // Validate and parse price
        String priceStr = request.getParameter("price");
        if (priceStr != null && !priceStr.isEmpty()) {
            price = Float.parseFloat(priceStr);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("UPDATE products SET pname=?, pdescription=?, pqty=?, price=?, image_url=? WHERE id=?");
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, quantity);
            pstmt.setFloat(4, price);
            pstmt.setString(5, imageUrl);
            pstmt.setInt(6, productId);
            pstmt.executeUpdate();
            con.close();
            response.sendRedirect("ViewProductsServlet");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle error
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM products WHERE id=?");
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
            con.close();
            response.sendRedirect("ViewProductsServlet");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle error
        }
    }
}
