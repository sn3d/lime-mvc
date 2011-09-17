<HTML>
<BODY>

	#foreach( $name in $names )
	<DIV> Name: $name </DIV>
	#end	
	<br>
		
	<FORM action="add" method="post">
		<b>Add name</b><br>
		Name: <input type="text" name="name" /> <input type="submit" value="Submit" />
	</FORM>
</BODY>
</HTML>