<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up Form</title>

    <!-- Font Icon -->
    <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="css/style.css">

    <!-- Sweet alert library -->
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/sweetalert/dist/sweetalert.css">
</head>
<body>

<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">

<div class="main">

    <!-- Sign up form -->
    <section class="signup">
        <div class="container">
            <div class="signup-content">
                <div class="signup-form">
                    <h2 class="form-title">Sign up</h2>

                    <form method="post" action="register" class="register-form" id="register-form" onsubmit="return validateForm()">
                        <div class="form-group">
                            <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                            <input type="text" name="name" id="name" placeholder="Your Name" required/>
                        </div>
                        <div class="form-group">
                            <label for="email"><i class="zmdi zmdi-email"></i></label>
                            <input type="email" name="email" id="email" placeholder="Your Email" required/>
                        </div>
                        <div class="form-group">
                            <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                            <input type="password" name="pass" id="pass" placeholder="Password" required/>
                        </div>
                        <div class="form-group">
                            <label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
                            <input type="password" name="re_pass" id="re_pass" placeholder="Repeat your password" required/>
                        </div>
                        <div class="form-group">
                            <label for="contact"><i class="zmdi zmdi-lock-outline"></i></label>
                            <input type="text" name="contact" id="contact" placeholder="Contact no" required/>
                        </div>
                        <div class="form-group">
                            <input type="checkbox" name="agree-term" id="agree-term" class="agree-term" required/>
                            <label for="agree-term" class="label-agree-term"><span><span></span></span>I
                                agree all statements in <a href="#" class="term-service">Terms
                                    of service</a></label>
                        </div>
                        <div class="form-group form-button">
                            <input type="submit" name="signup" id="signup" class="form-submit" value="Register"/>
                        </div>
                    </form>
                </div>
                <div class="signup-image">
                    <figure>
                        <img src="images/signup-image.jpg" alt="sing up image">
                    </figure>
                    <a href="login.jsp" class="signup-image-link">I am already
                        member</a>
                </div>
            </div>
        </div>
    </section>
</div>
<!-- JS -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="js/main.js"></script>

<script type="text/javascript">
    var status = document.getElementById("status").value;
    if(status == "success"){
        swal("Congrats", "Account Created Successfully", "success");
    }

    function validateForm() {
        var password = document.getElementById("pass").value;
        var repeatPassword = document.getElementById("re_pass").value;
        var termsChecked = document.getElementById("agree-term").checked;
        var contactNumber = document.getElementById("contact").value;


        if (password !== repeatPassword) {
            swal("Oops!", "Passwords do not match", "error");
            return false;
        }

        if (!termsChecked) {
            swal("Oops!", "Please agree to the terms and conditions", "error");
            return false;
        }
        
        if (contactNumber.length !== 10 || isNaN(contactNumber)) {
            swal("Oops!", "Please enter a valid 10-digit contact number", "error");
            return false;
        }

        return true;
    }
</script>

</body>
</html>
