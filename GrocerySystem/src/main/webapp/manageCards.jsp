<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Manage Saved Cards</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f2f2f2;
        margin: 0;
        padding: 0;
    }
    h1 {
        text-align: center;
        margin-top: 20px;
        margin-bottom: 20px;
        color: #333;
    }
    table {
        width: 80%;
        margin: 0 auto;
        border-collapse: collapse;
        border: 1px solid #ddd;
        background-color: #fff;
    }
    th, td {
        padding: 10px;
        border: 1px solid #ddd;
        text-align: left;
    }
    th {
        background-color: #f2f2f2;
        color: #333;
    }
    tr:nth-child(even) {
        background-color: #f9f9f9;
    }
    tr:hover {
        background-color: #f2f2f2;
    }
    .center {
        text-align: center;
    }
    .edit-form {
	    display: none;
	    padding: 20px;
	    background-color: #f9f9f9;
	    border-radius: 5px;
	    margin-top: 20px;
	    width: 60%; /* Set width to 60% */
	    margin: 0 auto; /* Center horizontally */
	}
    .edit-form.show {
        display: block;
    }
    .edit-form label {
        display: block;
        margin-bottom: 10px;
        font-weight: bold;
    }
    .edit-form input[type="text"] {
        width: calc(100% - 22px); /* Adjust for padding and border */
        padding: 8px;
        margin-bottom: 10px;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 5px;
    }
    .edit-form button {
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white;
        border: none;
        cursor: pointer;
        border-radius: 5px;
    }
    .edit-form button:hover {
        background-color: #45a049;
    }
    .action-buttons button {
        padding: 8px 15px;
        margin-right: 5px;
        background-color: #007bff;
        color: white;
        border: none;
        cursor: pointer;
        border-radius: 5px;
    }
    .action-buttons button:hover {
        background-color: #0056b3;
    }
    .action-buttons form {
        display: inline;
        margin: 0;
    }
</style>
<script>
    function showEditForm(cardNo, expiryDate, cvv) {
        document.getElementById('edit-cardNo').value = cardNo;
        document.getElementById('edit-expiryDate').value = expiryDate;
        document.getElementById('edit-cvv').value = cvv;
        document.getElementById('editForm').classList.add('show');
    }
</script>
</head>
<body>
<h1>Manage Saved Cards</h1>
<table>
    <thead>
        <tr>
            <th>Card Number</th>
            <th>Expiry Date</th>
            <th>CVV</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <% 
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery", "root", "root");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM paymentDetails");
                while (resultSet.next()) {
        %>
        <tr>
            <td><%= resultSet.getString("cardNo") %></td>
            <td><%= resultSet.getString("expiry") %></td>
            <td><%= resultSet.getString("cvv") %></td>
            <td class="action-buttons">
                <button onclick="showEditForm('<%= resultSet.getString("cardNo") %>', '<%= resultSet.getString("expiry") %>', '<%= resultSet.getString("cvv") %>')">Edit</button>
                <form method="post" action="deleteCard">
                    <input type="hidden" name="cardNo" value="<%= resultSet.getString("cardNo") %>">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
        <%
                }
            } catch (Exception e) {
                out.print(e.getMessage());
            }
        %>
    </tbody>
</table>
<br/><br/><br/>

<div id="editForm" class="edit-form">
    <h2>Edit Card</h2>
    <form method="post" action="editCard">
        <input type="hidden" id="edit-cardNo" name="cardNo">
        <label for="edit-expiryDate">Expiry Date:</label>
        <input type="text" id="edit-expiryDate" name="expiry" required>
        <label for="edit-cvv">CVV:</label>
        <input type="text" id="edit-cvv" name="cvv" required>
        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>

