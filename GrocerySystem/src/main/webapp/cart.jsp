<%@ page import="java.util.*,javax.servlet.http.*"%>
<%
if (session.getAttribute("name") == null) {
	response.sendRedirect("login.jsp");
}
%>
<!DOCTYPE html>
<html lang="en">
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
</head>
<body id="page-top">
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
						class="nav-link py-3 px-0 px-lg-3 rounded" href="#contact">Cart</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded"
						href="GetOrderDetailsServlet">Order</a></li>
					<li class="nav-item mx-0 mx-lg-1"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href="logout">Logout</a></li>
					<li class="nav-item mx-0 mx-lg-1 bg-danger"><a
						class="nav-link py-3 px-0 px-lg-3 rounded" href=""> <%=session.getAttribute("name")%></a></li>

				</ul>
			</div>
		</div>
	</nav>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>

	<!-- Cart Items -->
	<div class="container">
		<h1>Your Cart</h1>
		<div class="row">
			<%
			// Get the cart from the session
			Map<String, Map<String, Object>> cart = (Map<String, Map<String, Object>>) session.getAttribute("cart");
			double totalSum = 0;
			if (cart != null && !cart.isEmpty()) {
				for (Map.Entry<String, Map<String, Object>> entry : cart.entrySet()) {
					Map<String, String> product = (Map<String, String>) entry.getValue().get("product");
					int count = (Integer) entry.getValue().get("count");
					double price = Double.parseDouble(product.get("product_price"));
					double sum = price * count;
					totalSum += sum;
			%>
			<div class="col-md-3">
				<div class="card">
					<img class="card-img-top" src="<%=product.get("product_image")%>"
						alt="Product image">
					<div class="card-body">
						<h5 class="card-title"><%=product.get("product_name")%></h5>
						<p class="card-text"><%=product.get("product_description")%></p>
						<p class="card-text">
							Price of the Product:
							<%=price%></p>
						<p class="card-text">
							Total Sum:
							<%=sum%></p>
						<form action="UpdateCartServlet" method="post">
							<input type="hidden" name="productId" value="<%=entry.getKey()%>">
							<input type="number" name="count" value="<%=count%>">
							<button type="submit">Update</button>
						</form>
						<form action="RemoveFromCartServlet" method="post">
							<input type="hidden" name="productId" value="<%=entry.getKey()%>">
							<button type="submit">X</button>
						</form>
					</div>
				</div>
			</div>
			<%
			}
			%>
			<!-- Total Sum and Place Order Button -->
			<div class="col justify-content-end">
				<div class="col-md-4">
					<h4>
						Total Sum:
						<%=totalSum%></h4>
					<form action="PlaceOrderServlet" method="post">
						<button type="submit" class="btn btn-primary">Place Order</button>
					</form>
				</div>
			</div>
			<%
			} else {
			%>
			<p>Your cart is empty.</p>
			<%
			}
			%>
		</div>
	</div>




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
