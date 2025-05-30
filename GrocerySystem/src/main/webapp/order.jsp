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
	</nav>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>


	<div class="container">
		<h1>Your Orders</h1>
		<%
		// Get the order details from the session
		List<Map<String, Object>> orderDetails = (List<Map<String, Object>>) session.getAttribute("orderDetails");
		if (orderDetails != null && !orderDetails.isEmpty()) {
			for (Map<String, Object> orderDetail : orderDetails) {
		%>
		<div class="card mb-3">
			<div class="row g-0">
				<div class="col-md-2">
					<img src="<%=orderDetail.get("product_image_url")%>"
						class="img-fluid rounded-start" alt="Product image">
				</div>
				<div class="col-md-8">
					<div class="card-body">
						<h5 class="card-title"><%=orderDetail.get("product_name")%></h5>
						<p class="card-text"><%=orderDetail.get("product_description")%></p>
						<p class="card-text">
							<small class="text-muted">Quantity: <%=orderDetail.get("count")%></small>
						</p>
						<p class="card-text" id="productSum"><%=orderDetail.get("product_sum")%></p>
					</div>
					<form action="UpdateOrderServlet" method="post">
						<input type="hidden" name="orderId"
							value="<%=orderDetail.get("order_id")%>"> <input
							type="number" name="newCount" min="1"
							value="<%=orderDetail.get("count")%>">
						<button type="submit" class="btn btn-primary">Update
							Quantity</button>
					</form>
				</div>
				<div class="col-md-2">
					<form action="DeleteOrderServlet" method="post"
						class="position-absolute top-0 end-0 m-2">
						<input type="hidden" name="orderId"
							value="<%=orderDetail.get("order_id")%>">
						<button type="submit" class="btn btn-danger">Cancel Order</button>
					</form>
					<form action="payment.jsp" method="get">
						<input type="hidden" name="amount"
							value="<%=orderDetail.get("product_sum")%>"> <input
							type="hidden" name="oid" value="<%=orderDetail.get("order_id")%>">
						<button type="submit" class="btn btn-success m-2"
							style="position: relative; right: 20px;">Pay Now</button>
					</form>
				</div>
			</div>
		</div>
		<%
		}
		} else {
		%>
		<p>No orders yet.</p>
		<%
		}
		%>
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
						by <a href="http://startbootstrap.com">Start Bootstrap</a>
					</p>
				</div>
			</div>
		</div>
	</footer>
	<!-- Copyright Section-->
	<div class="copyright py-4 text-center text-white">
		<div class="container">
			<small>Copyright � Online Grocery System 2024</small>
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
