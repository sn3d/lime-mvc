<#macro shownames>
    <#list names as name>
		<DIV> Name: ${name} </DIV>
	</#list>
</#macro>


<#macro mainbody>
  <HTML>
    <BODY>
	  <#nested>
    </BODY>
  </HTML>
</#macro>