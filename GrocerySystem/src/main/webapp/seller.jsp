<%
String name = (String) session.getAttribute("name");
if (name == null || (!"test".equalsIgnoreCase(name) && !"Test".equals(name))) {
    response.sendRedirect("login.jsp");
}
%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,javax.servlet.http.*"%>
<%@ page import="java.util.List" %>
<%@ page import="com.grocery.seller.Product" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Grocery</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Font Awesome icons (free version)-->
<script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js"
	crossorigin="anonymous"></script>
<!-- Google fonts-->
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css" />
<link
	href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
	rel="stylesheet" type="text/css" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="css/index-styles.css" rel="stylesheet" />
<link rel="stylesheet" href="css/seller.css">
    <style>
        /* CSS for modal popup */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            padding-top: 60px;
        }
        
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
        }
    </style>
    <script>
	    window.onload = function() {
	        if (!sessionStorage.getItem("productsLoaded")) {
	            sessionStorage.setItem("productsLoaded", true);
	            window.location.href = "ViewProductsServlet";
	        } else {
	            // Clear sessionStorage when leaving the page
	            window.onbeforeunload = function() {
	                sessionStorage.removeItem("productsLoaded");
	            };
	        }
	    };

        // JavaScript functions for modal popup
        function openEditModal(id, name, description, quantity, price) {
            document.getElementById('editId').value = id;
            document.getElementById('editName').value = name;
            document.getElementById('editDescription').value = description;
            document.getElementById('editQuantity').value = quantity;
            document.getElementById('editPrice').value = price;
            document.getElementById('editModal').style.display = 'block';
        }

        function closeEditModal() {
            document.getElementById('editModal').style.display = 'none';
        }

        function openAddModal() {
            document.getElementById('addModal').style.display = 'block';
        }

        function closeAddModal() {
            document.getElementById('addModal').style.display = 'none';
        }
    </script>
</head>
<body>
<!-- Navigation-->
	<nav
		class="navbar navbar-expand-lg bg-secondary text-uppercase fixed-top"
		id="mainNav">
		<div class="container">
			<a class="navbar-brand" href="#page-top">Online Grocery System</a>
			<button
				class="navbar-toggler text-uppercase font-weight-bold bg-primary text-white rounded"
				type="button" data-bs-toggle="collapse"
				data-bs-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				Menu <i class="fas fa-bars"></i>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="index.jsp">Products</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="seller.jsp">Seller</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="GetCartServlet">Cart</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="#cart">Orders</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="logout">Logout</a></li>
					<li class="nav-item mx-0 mx-lg-1 bg-danger"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href=""> <%=session.getAttribute("name")%></a></li>

				</ul>
			</div>
		</div>
	</nav><br><br><br><br><br>
    <h1>Welcome Seller!</h1>
    <h2>Product List</h2>
    <!-- Add Bootstrap table classes -->
<table class="table table-striped table-bordered">
    <thead class="thead-dark custom-black-heading">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Image</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <% 
            List<Product> productList = (List<Product>) request.getAttribute("productList");
            if (productList != null && !productList.isEmpty()) {
                for (Product product : productList) {
        %>
        <tr>
            <td><%= product.getId() %></td>
            <td><%= product.getName() %></td>
            <td><%= product.getDescription() %></td>
            <td><%= product.getQuantity() %></td>
            <td><%= product.getPrice() %></td>
            <td><img src="<%= product.getImageUrl() %>" alt="Product Image" width="100" height="100"></td>
            <td class="actions">
                <button class="btn btn-primary" onclick="openEditModal(<%= product.getId() %>, '<%= product.getName() %>', '<%= product.getDescription() %>', <%= product.getQuantity() %>, <%= product.getPrice() %>)">Edit</button>
                <a class="btn btn-danger" href="DeleteProductServlet?id=<%= product.getId() %>">Delete</a>
            </td>
        </tr>
        <% 
                }
            } else {
        %>
        <tr>
            <td colspan="7">No products available.</td>
        </tr>
        <% } %>
    </tbody>
</table>


    <!-- Modal for editing product -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeEditModal()">&times;</span>
            <h2>Edit Product</h2>
            <form action="UpdateProductServlet" method="post">
                <input type="hidden" id="editId" name="id">
                <label for="editName">Name:</label>
                <input type="text" id="editName" name="newName">
                <br>
                <label for="editDescription">Description:</label>
                <input type="text" id="editDescription" name="newDescription">
                <br>
                <label for="editQuantity">Quantity:</label>
                <input type="number" id="editQuantity" name="newQuantity">
                <br>
                <label for="editPrice">Price:</label>
                <input type="number" id="editPrice" name="newPrice">
                <br>
                <input type="submit" value="Save">
            </form>
        </div>
    </div>

    <!-- Modal for adding product -->
    <div id="addModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeAddModal()">&times;</span>
            <h2>Add New Product</h2>
            <form action="AddProductServlet" method="post">
                <label for="addName">Name:</label>
                <input type="text" id="addName" name="name">
                <br>
                <label for="addDescription">Description:</label>
                <input type="text" id="addDescription" name="description">
                <br>
                <label for="addQuantity">Quantity:</label>
                <input type="number" id="addQuantity" name="quantity">
                <br>
                <label for="addPrice">Price:</label>
                <input type="number" id="addPrice" name="price">
                <br>
                <label for="addImageUrl">Image URL:</label>
                <input type="text" id="addImageUrl" name="imageUrl">
                <br>
                <input type="submit" value="Add Product">
            </form>
        </div>
    </div>

    <button onclick="openAddModal()">Add New Product</button>
    
    <br>
	<br>
	<br>
	<br>
	<br>

	<!-- Footer-->
	<footer class="footer text-center">
		<div class="container">
			<div class="row">
				<!-- Footer Location-->
				<div class="col-lg-4 mb-5 mb-lg-0">
					<h4 class="text-uppercase mb-4">Location</h4>
					<p class="lead mb-0">
						2215 John Daniel Drive <br /> Clark, MO 65243
					</p>
				</div>
				<!-- Footer Social Icons-->
				<div class="col-lg-4 mb-5 mb-lg-0">
					<h4 class="text-uppercase mb-4">Around the Web</h4>
					<a class="btn btn-outline-light btn-social mx-1" href="#!"><i
						class="fab fa-fw fa-facebook-f"></i></a><a
						class="btn btn-outline-light btn-social mx-1" href="#!"><i
						class="fab fa-fw fa-twitter"></i></a><a
						class="btn btn-outline-light btn-social mx-1" href="#!"><i
						class="fab fa-fw fa-linkedin-in"></i></a><a
						class="btn btn-outline-light btn-social mx-1" href="#!"><i
						class="fab fa-fw fa-dribbble"></i></a>
				</div>
				<!-- Footer About Text-->
				<div class="col-lg-4">
					<h4 class="text-uppercase mb-4">About Freelancer</h4>
					<p class="lead mb-0">
						Freelance is a free to use, MIT licensed Bootstrap theme created
						by <a href="http://startbootstrap.com">Start Bootstrap</a> .
					</p>
				</div>
			</div>
		</div>
	</footer>
	<!-- Copyright Section-->
	<div class="copyright py-4 text-center text-white">
		<div class="container">
			<small>Copyright © Online Grocery System 2024</small>
		</div>
	</div>

	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>
	<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>
