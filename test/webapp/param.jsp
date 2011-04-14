<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
    import="org.zdevra.guice.example.*"
%>

<%
String param1 = (String) request.getAttribute("param1");
if (param1 == null) {
	param1 = "null";
}

String param2 = (String) request.getAttribute("param2");
if (param2 == null) {
	param2 = "null";
}

String param3 = (String) request.getAttribute("param3");
if (param3 == null) {
	param3 = "null";
}
%>

param1='<%=param1 %>', 
param2='<%=param2 %>',
param3='<%=param3 %>',