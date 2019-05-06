<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<script src="/resources/lib/jquery-ui/jquery-ui.js"></script>
<script src="/resources/lib/bootstrap/js/bootstrap.js"></script>
<script src="/resources/lib/jquery-toggles/toggles.js"></script>

<script src="/resources/lib/raphael/raphael.js"></script>

<script src="/resources/lib/flot/jquery.flot.js"></script>
<script src="/resources/lib/flot/jquery.flot.resize.js"></script>
<script src="/resources/lib/flot-spline/jquery.flot.spline.js"></script>

<script src="/resources/lib/jquery-knob/jquery.knob.js"></script>

<script src="/resources/frontFrame/quirk.js"></script>

<form id="frm" name="frm">
	<input type="hidden" id="pageName" name="pageName" />
</form>


<script type="text/javascript">
	$(function () {
		var profile	= document.getElementById("mb-Profile");
		var lgon	= document.getElementById("lgon");
		var signUp	= document.getElementById("signUp");
		var lgout	= document.getElementById("lgout");
		var session = "${sessionId}"
		
		if (session != "") {
			profile.style.display	= "block";
			lgout.style.display	= "";
			lgon.style.display		= "none";
			signUp.style.display	= "none";
		} else {
		}
		
		$("#home").click(function () {
			header.pageSubmitFn("main");	
		});
	})
</script>