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
    <script type="text/javascript" src="<c:url value='/resources/js/raphael-min.js'/>" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/dracula_graffle.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/dracula_graph.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/example.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/diagram.css'/>" media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.css'/>" media="screen" />
</head>
<body>
	<h1>PSC healthchecker</h1>
	<!-- <h4>${siteNodesObject}</h4> -->
<div>	
<input type="hidden" id="siteNodeData" value='${siteNodesObject}' />
<input type="hidden" id="host" value='${host}' />
</div>
<br><hr>
<div id="canvas"></div>
<button id="redraw" onclick="redraw();">redraw</button>
<button id="hide_penguin" onclick="hide('penguin');">hide penguin (beta)</button>
<button id="hide_penguin" onclick="show('penguin');">show penguin (beta)</button>

<!-- 

<svg id="cont2" height="160" width="160" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
	<circle id="cir1" cx="300" cy="300" r="40" stroke="yellow" stroke-width="" />
</svg>
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
 <svg id="cont" height="1000" width="1000" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
    <circle id="cir1" cx="300" cy="300" r="40" stroke="yellow" stroke-width="" fill="none" />
</svg>
 -->
 
<script type="text/javascript">
	//window.onload = drawDiag();
</script>
</body></html>