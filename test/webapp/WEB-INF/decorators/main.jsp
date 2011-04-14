<%@ taglib 
  prefix="decorator" 
  uri="http://www.opensymphony.com/sitemesh/decorator" 
%>
<html>
<head>
  <title>decorated - <decorator:title default="main"/></title>
  <decorator:head />
</head>
<body>
  <div class="menu">
  </div>
  <decorator:body/>
</body>
</html>