<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.8.0.js' />" ></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.js' />" ></script>

<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.css' />" media="screen" />

<title>Connect to DB</title>
</head>
<body>
<div class="container" style="width:500px">
	<h1>PSC Health Checker <br><small>Connect to your LDAP</small></h1>
  <div class="row" id="login-container">
    <div class="span8 offset2">
			<form action="/PSCHealthChecker/connect" method="post">
			  <fieldset class="form-group">
			    <label for="host">Host</label>
			    <input type="text" class="form-control" name="host" placeholder="Enter host">
			  </fieldset>
			  <fieldset class="form-group">
			    <label for="port">Port</label>
			    <input type="text" class="form-control" name="port" placeholder="Enter port">
			  </fieldset>
			  <fieldset class="form-group">
			    <label for="protocols">Protocol</label>
			    <select class="form-control" id="protocols">
			      <option>LDAP V3</option>
			      <option>LDAP V2</option>
			      <option>DSML V2</option>
			    </select>
			  </fieldset>

			  <div class="checkbox">
			    <label>
			      <input type="checkbox"> Read Only
			    </label>
			  </div>
			    <div class="form-group">
			    <label for="username">User DN</label>
			    <input type="text" class="form-control" name="username" placeholder="cn=Administrator,cn=users,dc=vSphere,dc=local">
			  </div>
			  <div class="form-group">
			    <label for="password">Password</label>
			    <input type="password" class="form-control" name="password" placeholder="Password">
			  </div>
			  <button type="submit" class="btn btn-info">Submit</button>
			</form>
			</div>
  		</div>
	</div>
</body>
</html>