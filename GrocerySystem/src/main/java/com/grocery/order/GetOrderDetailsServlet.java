package com.grocery.order;

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

@WebServlet("/GetOrderDetailsServlet")
public class GetOrderDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

			// Get the user id from the session
			HttpSession session = request.getSession(true);
			int userId = (int) session.getAttribute("userId");

			// Get the orders for the user
			PreparedStatement pstmt = con.prepareStatement(
				"SELECT orders.id, orders.product_sum, users.uname, products.pname, products.pdescription, products.pqty, products.price, products.image_url, SUM(orders.count) as total_count FROM orders INNER JOIN users ON orders.user_id = users.id INNER JOIN products ON orders.product_id = products.id WHERE user_id = ? GROUP BY products.id");
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			// Store the order details in the session
			List<Map<String, Object>> orderDetails = new ArrayList<>();
			while (rs.next()) {
				Map<String, Object> orderDetail = new HashMap<>();
				orderDetail.put("order_id", rs.getInt("id"));
				orderDetail.put("user_name", rs.getString("uname"));
				orderDetail.put("product_name", rs.getString("pname"));
				orderDetail.put("product_description", rs.getString("pdescription"));
				orderDetail.put("product_quantity", rs.getInt("pqty"));
				orderDetail.put("product_sum", rs.getFloat("product_sum"));
				orderDetail.put("product_image_url", rs.getString("image_url"));
				orderDetail.put("count", rs.getInt("total_count")); // use the total_count here
				orderDetails.add(orderDetail);
			}


			session.setAttribute("orderDetails", orderDetails);

			// Redirect the user to the orders page
			response.sendRedirect("order.jsp");

			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			response.getWriter().println(e);
		}
	}
}
