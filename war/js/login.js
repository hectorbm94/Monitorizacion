$(function() {
	$('#register-form-link').click(function(e) {
		$(".register-form").delay(100).fadeIn(100);
		$("#login-form-link").delay(100).fadeIn(100);
 		$(".login-form").fadeOut(100);
 		$(".password-form").fadeOut(100);
 		$("#register-form-link").fadeOut(100);
 		$("#password-form-link").fadeOut(100);
		e.preventDefault();
	});	
    $('#login-form-link').click(function(e) {
		$(".login-form").delay(100).fadeIn(100);
		$("#register-form-link").delay(100).fadeIn(100);
		$("#password-form-link").delay(100).fadeIn(100);
 		$(".register-form").fadeOut(100);
 		$(".password-form").fadeOut(100);
 		$("#login-form-link").fadeOut(100);
		e.preventDefault();
	});
	$('#password-form-link').click(function(e) {
		$(".password-form").delay(100).fadeIn(100);
		$("#login-form-link").delay(100).fadeIn(100);
 		$(".register-form").fadeOut(100);
 		$(".login-form").fadeOut(100);
 		$("#register-form-link").fadeOut(100);
 		$("#password-form-link").fadeOut(100);
		e.preventDefault();
	});	
});