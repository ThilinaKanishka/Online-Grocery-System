package com.grocery.order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteOrderServlet")
public class DeleteOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderId = request.getParameter("orderId");
		
		System.out.println("Order id : " + orderId);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");

			// Delete the order
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM orders WHERE id = ?");
			pstmt.setString(1, orderId);
			pstmt.executeUpdate();

			// Get the order details from the session
			HttpSession session = request.getSession(true);
			List<Map<String, Object>> orderDetails = (List<Map<String, Object>>) session.getAttribute("orderDetails");

			// Remove the order from the session
			if (orderDetails != null) {
				orderDetails.removeIf(orderDetail -> orderDetail.get("order_id").equals(Integer.parseInt(orderId)));
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
