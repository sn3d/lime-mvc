<#import "common.ftl" as cmn>
<@cmn.mainbody>

    <@cmn.shownames/>
    		
	<FORM action="add" method="post">
		<b>Add name</b><br>
		Name: <input type="text" name="name" /> <input type="submit" value="Submit" />
	</FORM>

</@cmn.mainbody>