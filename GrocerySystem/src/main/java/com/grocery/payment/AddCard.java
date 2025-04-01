package com.grocery.payment;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/paymentAdd")
public class AddCard extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> Payment = new ArrayList<String>();
        
		Payment.add(request.getParameter("cardNumber"));
		Payment.add(request.getParameter("expiryDate"));
		Payment.add(request.getParameter("cvv"));
        
        PaymentService stm=new PaymentService(Payment);
        stm.AddPayment();
        response.sendRedirect("payment.jsp");
	}
	
}
