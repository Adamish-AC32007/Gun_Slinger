<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
            <%@ page import="com.example.GunSlinger.stores.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="mystyle.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>main page</title>
</head>
<body>
<div class="Displaydiv2">
<%
System.out.println("In render");
List<GunSlingerStore> score = (List<GunSlingerStore>)request.getAttribute("Scores");
if (score==null){
 %>
	<p>No scores found</p>
	<% 
}else{
%>

<% 
Iterator<GunSlingerStore> iterator;


iterator = score.iterator();     
while (iterator.hasNext()){
	GunSlingerStore ts = (GunSlingerStore)iterator.next();

	%>
	<%=ts.getUsername() %> -
	<%=ts.getHighscore() %>
	<br/>
	<br></br>
<%
}
}
%>
</div>
</body>
</html>