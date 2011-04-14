<%@ page import="org.zdevra.guice.example.*" %>
<%
	Department dep = (Department) request.getAttribute("departments");
	if (dep == null) {
		dep = new Department("none");
	}
	
	String catalogId = (String) request.getAttribute("catalogId");
%>
<HTML>
<BODY>
Hello, world
Department is: <%= dep.getName() %>
Catalog is: <%= catalogId %>
</BODY>
</HTML>