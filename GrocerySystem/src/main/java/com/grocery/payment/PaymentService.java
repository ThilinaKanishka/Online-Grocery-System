package com.grocery.payment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentService {
	private String cardNum , expiry , cvv;
	
	public PaymentService(ArrayList<String> PaymentService) {
        setCardNum(PaymentService.get(0));
        setExpiry(PaymentService.get(1));
        setCvv(PaymentService.get(2));

    }

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	public String AddPayment() {
		String message="";
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
            PreparedStatement pstmt = con.prepareStatement("insert into paymentDetails(cardNo, expiry, cvv) values(?,?,?);");
            pstmt.setString(1, getCardNum());
            pstmt.setString(2, getExpiry());
            pstmt.setString(3, getCvv());
            pstmt.executeUpdate();
            con.close();
            message="added success";
        } catch (ClassNotFoundException | SQLException e) {
        	message="error occured";
            e.printStackTrace();
        }
		return message;
    }
	
}
