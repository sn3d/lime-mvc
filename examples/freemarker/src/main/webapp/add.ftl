<#import "common.ftl" as cmn>
<@cmn.mainbody>

    <@cmn.shownames/>
    
    <br>
	<#if addName == "true" >
		<DIV> Name has been added </DIV>
	<#else>
		<DIV> Limit of names has been exhausted </DIV>
	</#if>	
	
	<a href="names">back</a>
	
</@cmn.mainbody>