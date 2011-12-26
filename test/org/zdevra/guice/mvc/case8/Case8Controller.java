package org.zdevra.guice.mvc.case8;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.ModelName;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.RequestParameter;
import org.zdevra.guice.mvc.annotations.ToView;

@Controller
@ToView("default")
public class Case8Controller {
	
	
	@Path("/int/single") @ModelName("msg")
	public String convertInt(@RequestParameter("number") int x) 
	{
		if (x == 1) {
			return "ok";
		}
		return "fail";
	}
	
	
	@Path("/int/array") @ModelName("msg")
	public String convertIntArray(@RequestParameter("array") int[] x) 
	{
		if (x == null) return "fail";
		if (x.length != 2) return "fail";
		if (x[0] != 1) return "fail";
		if (x[1] != 2) return "fail";		
		return "ok";
	}
	
	
	@Path("/integer/single") @ModelName("msg")
	public String convertInteger(@RequestParameter("number") Integer x) 
	{
		if (x == null) return "fail";
		if (x != 1) return "fail";		
		return "ok";
	}
	
	
	@Path("/integer/array") @ModelName("msg")
	public String convertInteger(@RequestParameter("array") Integer[] x) 
	{
		if (x == null) return "fail";
		if (x.length != 2) return "fail";
		return "ok";
	}


    @Path("/person/default") @ModelName("msg")
    public String convertPersonDefault( @RequestParameter("person") Person p )
    {
        return "default: " + p.toString();
    }


    @Path("/person/one") @ModelName("msg")
    public String convertPersonOne( @RequestParameter(value="person", converterFactory=FirstPersonConverterFactory.class) Person p )
    {
        return "one: " +  p.toString();
    }


    @Path("/person/two") @ModelName("msg")
    public String convertPersonTwo( @RequestParameter(value="person", converterFactory=SecondPersonConverterFactory.class) Person p )
    {
        return "two: " +  p.toString();
    }


}
