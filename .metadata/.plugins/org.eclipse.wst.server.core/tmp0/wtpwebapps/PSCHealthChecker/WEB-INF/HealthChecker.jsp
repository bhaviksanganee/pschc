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
<div class="container">
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
    <li><a data-toggle="tab" href="#menu1">List View</a></li>
    <li><a data-toggle="tab" href="#menu2">Other Data</a></li>
  </ul>

  <div class="tab-content">
    <div id="home" class="tab-pane fade in active">
      <h3>Graph Representation</h3>
		<div id="canvas"></div>
		<button id="redraw" onclick="redraw();">redraw</button>
		<button id="hide_penguin" onclick="hide('penguin');">hide penguin (beta)</button>
		<button id="hide_penguin" onclick="show('penguin');">show penguin (beta)</button>
    </div>
    <div id="menu1" class="tab-pane fade">
      <h3>List View</h3>
      
      <table  class="table table-striped">
      	<thead>
      		<tr>
      			<th>Site name</th>
      			<th>PSC links</th>
      			<th>Services</th>
      			<th>Load Balancer</th>
      			<th>VCenter</th>
      		</tr>
      	</thead>
		<c:forEach var="siteNode" items="${siteNodes}">
			<tr>
				<td>${siteNode.site_cn}</td>
				<td>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>PSC Node</th>
								<th>PSC Replication</th>
							</tr>
						</thead>
						<c:forEach var="entry" items="${siteNode.pscReplicationServers}">
						<tr>
						<td>
						  ${entry.key} </td>
						<td>${entry.value }</td>
						</tr>
						</c:forEach>
					</table>
				</td>				
				<td>${siteNode.services }</td>
				<td>${siteNode.lbPSC }</td>		
				<td>${siteNode.vcServices }</td>		
			</tr>
		</c:forEach>
		</table>
${sitesNodes}<hr>     
    </div>
    <div id="menu2" class="tab-pane fade">
      <h3>Menu 2</h3>
      <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
    </div>
  </div>
</div>




 
</body></html>