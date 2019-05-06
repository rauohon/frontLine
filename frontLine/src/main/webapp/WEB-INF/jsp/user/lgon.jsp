<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!--<link rel="shortcut icon" href="../images/favicon.png" type="image/png">-->

<title>Quirk Responsive Admin Templates</title>

<link rel="stylesheet" href="/resources/lib/fontawesome/css/font-awesome.css">

<link rel="stylesheet" href="/resources/css/quirk.css">

<script src="/resources/lib/modernizr/modernizr.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="../lib/html5shiv/html5shiv.js"></script>
  <script src="../lib/respond/respond.src.js"></script>
  <![endif]-->
</head>

<body class="signwrapper">

	<div class="sign-overlay"></div>
	<div class="signpanel"></div>

	<div class="panel signin">
		<div class="panel-heading">
			<h1>Quirk</h1>
			<h4 class="panel-title">Welcome! Please signin.</h4>
		</div>
		<div class="panel-body">
			<button class="btn btn-primary btn-quirk btn-fb btn-block invisible">
				Connect with Facebook
			</button>
			<div class="or">or</div>
			<form id='valid' action="lgon.do" method="post">
				<div class="form-group mb10">
					<div class="error"><span style="color : tomato;">${errorDetail }</span></div>
					<div class="input-group">
						<span class="input-group-addon">
							<i class="glyphicon glyphicon-user"></i>
						</span>
						<input type="text" class="form-control" name="mbId" placeholder="ID" required>
					</div>
				</div>
				<div class="form-group nomargin">
					<div class="input-group">
						<span class="input-group-addon">
							<i class="glyphicon glyphicon-lock"></i>
						</span>
						<input type="password" class="form-control"name="mbPwd" placeholder="password" required>
					</div>
				</div>
				<div>
					<a href="" class="forgot">Forgot password?</a>
				</div>
				<div class="form-group">
					<button class="btn btn-success btn-quirk btn-block">
						Sign In
					</button>
					<a href="signUpPage.do"
						class="btn btn-default btn-quirk btn-stroke btn-stroke-thin btn-block btn-sign">
						Not	a member? Sign up now!
					</a>
				</div>
			</form>
			<hr>
		</div>
	</div>
	<!-- panel -->
	<script src="/resources/lib/jquery/jquery.js"></script>
	<script src="/resources/lib/jquery-validate/jquery.validate.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#valid').validate({
				errorLabelContainer: jQuery('#valid div.error')
			  });
		});
	</script>
</body>
</html>
