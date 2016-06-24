<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://d3js.org/d3.v3.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/diagram.js' />" ></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.8.0.js' />" ></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.js' />" ></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/diagram.css' />" media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.css' />" media="screen" />
</head>
<body>
	<h1>PSC healthchecker</h1>
	
<h3>${siteNodesObject}</h3>
<br><hr>
<c:forEach var="entry" items="${siteNodesObject}">
	Site:  ${entry.site_cn} <br/>
	replication: ${entry.pscReplicationServers} <br/>
	
</c:forEach>

	<svg id="treesvg" height="460" width="958">
	<g id="g_lines">
		<line y2="100" x2="280" y1="30" x1="300"></line>
		<line y2="100" x2="320" y1="30" x1="300"></line>
	</g>
	<g id="g_circles">
		<circle r="12" cy="30" cx="300"></circle>
		<circle r="12" cy="100" cx="280"></circle>
		<circle r="12" cy="100" cx="320"></circle>
	</g>
	<g id="g_labels">
		<text y="35" x="300">?</text>
		<text y="105" x="280">?</text>
		<text y="105" x="320">?</text></g>
	<g id="g_elabels">
		<text y="65" x="282">se</text>
		<text y="65" x="318">xe</text>
	</g>
	
	</svg>

</body></html>