<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
    import="org.zdevra.guice.example.*"
    import="java.util.*"
    %>
<%
List<Employee> empl = (List<Employee>)request.getAttribute("employees");
if (empl == null) {
	empl = Collections.emptyList();
}

for (Employee e : empl) {
%>
	dep=<%=e.getDepartment()%>, name=<%=e.getName()%>, surname=<%=e.getSurname()%> 
<%}%>