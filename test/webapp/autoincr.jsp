<%@ page import="org.zdevra.guice.example.*" %>
<%
Integer i = (Integer) session.getAttribute("autoincr1");
if (i == null) {%>
	autoincr1=null
<%} else {%>
	autoincr1=<%=i%>
<%}%>


<%
i = (Integer) session.getAttribute("autoincr2");
if (i == null) {%>
	autoincr2=null
<%} else {%>
	autoincr2=<%=i%>
<%}%>


<%
i = (Integer) session.getAttribute("autoincr3");
if (i == null) {%>
	autoincr3=null
<%} else {%>
	autoincr3=<%=i%>
<%}%>

<%
i = (Integer) session.getAttribute("autoincr4");
if (i == null) {%>
	autoincr4=null
<%} else {%>
	autoincr4=<%=i%>
<%}%>