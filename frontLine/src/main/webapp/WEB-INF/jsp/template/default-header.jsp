<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!--<link rel="shortcut icon" href="/images/favicon.png" type="image/png">-->

<title>Web style RPG</title>

<link rel="stylesheet" href="/resources/lib/Hover/hover.css">
<link rel="stylesheet" href="/resources/lib/fontawesome/css/font-awesome.css">
<link rel="stylesheet" href="/resources/lib/weather-icons/css/weather-icons.css">
<link rel="stylesheet" href="/resources/lib/ionicons/css/ionicons.css">
<link rel="stylesheet" href="/resources/lib/fairytale-icons/flaticon.css">
<link rel="stylesheet" href="/resources/lib/martial-icons/flaticon.css">
<link rel="stylesheet" href="/resources/lib/security-icons/flaticon.css">
<link rel="stylesheet" href="/resources/lib/jquery-toggles/toggles-full.css">
<link rel="stylesheet" href="/resources/lib/morrisjs/morris.css">

<link rel="stylesheet" type="text/css" href="/resources/css/quirk.css">

<script src="/resources/lib/modernizr/modernizr.js"></script>
<script src="/resources/lib/jquery/jquery.js"></script>
<script type="text/javascript" src="/resources/js/cmmn/cmmn_util.js"></script>
<script type="text/javascript" src="/resources/js/cmmn/popup.js"></script>
<script type="text/javascript" src="/resources/js/cmmn/transaction.js"></script>


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="/lib/html5shiv/html5shiv.js"></script>
<script src="/lib/respond/respond.src.js"></script>
<![endif]-->

<script>
	var header = {
		pageSubmitFn : function(pageName) {
			$("#pageName").val(pageName);

			console.log($("#frm").attr("action"));
			
			if (pageName == "main") {
				$("#frm").attr("action", "/public/main.do");
			} else if (pageName == "signInPage") {
				$("#frm").attr("action", "/public/lgonPage.do");
			} else if (pageName == "signIn") {
				$("#frm").attr("action", "/public/signIn.do");
			} else if (pageName == "signUpPage") {
				$("#frm").attr("action", "/public/signUpPage.do");
			} else if (pageName == "lgout") {
				$("#frm").attr("action", "/user/lgout.do");
				$("#frm").attr("method", "post");
			} else if (pageName == "gameHome") {
				$("#frm").attr("action", "/game/gameHome.do");
				$("#gameHome").addClass('active');
				$("#frm").attr("method", "post");
			}
			$("#frm").submit();
		}
	}

</script>