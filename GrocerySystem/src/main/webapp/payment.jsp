<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Make Payment</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f2f2f2;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .container {
        background-color: #fff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        max-width: 500px;
        width: 100%;
    }

    h1 {
        text-align: center;
        margin-bottom: 30px;
    }

    .form-group {
        margin-bottom: 20px;
    }

    label {
        font-weight: bold;
    }

    input[type="text"], input[type="number"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-top: 5px;
    }

    .btn {
        background-color: #4CAF50;
        color: white;
        padding: 15px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        width: 100%;
        font-size: 16px;
        margin-top: 10px;
    }

    .btn:hover {
        background-color: #45a049;
    }

    #savedPayments {
        margin-top: 30px;
    }

    #savedPaymentsList {
        width: calc(100% - 10px); /* Adjust for padding */
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-top: 5px;
    }

    #savedPaymentsList option {
        padding: 10px;
    }
</style>
</head>
<body>
<div class="container">
    <h1>Payment Details</h1>
    <p>Total Amount: <%= request.getParameter("amount") %></p> <!-- Replace $100 with your actual total amount -->
    <form id="paymentForm" action="" method="POST">
    	<input type="hidden" id="amount" name="amount" value="<%= request.getParameter("amount") %>">
    	<input type="hidden" id="oid" name="oid" value="<%=request.getParameter("oid")%>">
        <div class="form-group">
            <label for="cardNumber">Card Number</label>
            <input type="text" id="cardNumber" name="cardNumber" placeholder="Enter card number" required>
        </div>
        <div class="form-group">
            <label for="expiryDate">Expiry Date</label>
            <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YYYY" required>
        </div>
        <div class="form-group">
            <label for="cvv">CVV</label>
            <input type="number" id="cvv" name="cvv" placeholder="Enter CVV" required>
        </div>
        <button type="submit" class="btn" onclick="makepayment()">Make Payment</button>
        <button type="submit" class="btn" formaction="paymentAdd">Save Payment Details</button><br/><br/>
        
    </form>

    <div id="savedPayments">
        <h2>Saved Payment Details</h2>
        <form action="manageCards.jsp" method="GET">
            <select id="savedPaymentsList" onchange="populateForm()">
                <option value="">Select a saved payment</option>
                <!-- Saved payment options will be dynamically added here -->
            </select>
            <button type="submit" class="btn">Manage Cards</button>
        </form>
    </div>
</div>

<script>
    window.onload = function() {
        fetchSavedPayments();
    };
    
    function makepayment() {
        window.alert( document.getElementById("amount").value+" Paid Successfully!")
        const oid=document.getElementById("oid").value;
        
     // Create an XMLHttpRequest object
        var xhttp = new XMLHttpRequest();

        // Define the function to handle the response
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                // Redirect the user back to the orders page after successful deletion
                window.location.href = "order.jsp";
            }
        };

        // Open a POST request to the DeleteOrderServlet
        xhttp.open("POST", "DeleteOrderServlet", true);

        // Set the Content-Type header
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        // Send the request with the orderId as a parameter
        xhttp.send("orderId=" + oid);
    }

    function fetchSavedPayments() {
        fetch("fetchPayments") // Assuming "fetchCardNumbers" is your servlet URL
            .then(response => response.text())
            .then(data => {
                document.getElementById("savedPaymentsList").innerHTML = data;
            })
            .catch(error => console.error("Error fetching saved payments:", error));
    }

    function fetchCardDetails(cardNo) {
        fetch("fetchCardDetails?cardNo=" + cardNo)
            .then(response => response.text())
            .then(data => {
                const [expiryDate, cvv] = data.split("|");
                document.getElementById("expiryDate").value = expiryDate;
                document.getElementById("cvv").value = cvv;
                document.getElementById("cardNumber").value = cardNo;
            })
            .catch(error => console.error("Error fetching card details:", error));
    }

    function populateForm() {
        var savedPaymentsList = document.getElementById("savedPaymentsList");
        var selectedPayment = savedPaymentsList.options[savedPaymentsList.selectedIndex].value;

        if (selectedPayment) {
            fetchCardDetails(selectedPayment);
        }
    }
</script>
</body>
</html>
