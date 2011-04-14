/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *****************************************************************************/
package org.zdevra.guice.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.Model;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.RequestParameter;
import org.zdevra.guice.mvc.UriParameter;
import org.zdevra.guice.mvc.convertors.BooleanConv;
import org.zdevra.guice.mvc.convertors.DateConv;


@Controller
public class ParameterController {
	
	@RequestMapping(path="/uriparam/([a-z]+)/(\\d+)/((true)|(false))")
	public Model uriParam(
			@UriParameter(1) String param1, 
			@UriParameter(2) String param2, 
			@UriParameter(3) String param3 ) 
	{
		Model result = new Model();
		result.addObject("param1", param1);
		result.addObject("param2", param2);
		result.addObject("param3", param3);		
		return result;
	}
	
	
	@RequestMapping(path="/uriboolparam/((true)|(false))/((Y)|(N))")
	public Model uriBoolParam(
			@UriParameter(1) boolean param1,
			@UriParameter(2) @BooleanConv(trueVal="Y", falseVal="N") boolean param2) 
	{
		Model result = new Model();
		result.addObject("param1", Boolean.toString(param1));
		result.addObject("param2", Boolean.toString(param2));
		return result;
	}
	
	
	
	@RequestMapping(path="/uriintparam/(\\d+)/(\\d+)")
	public Model uriNumberParam( @UriParameter(1) int param1, @UriParameter(2) Integer param2) 
	{
		Model result = new Model();
		result.addObject("param1", Integer.toString(param1));
		result.addObject("param2", Integer.toString(param2));
		return result;
	}
	
	
	@RequestMapping(path="/uridateparam/(\\d+)/(.+)")
	public Model uriDateParam( 
		@UriParameter(1) @DateConv("yyyyMMdd") Date param1,
		@UriParameter(2) @DateConv(value="yyyyMMdd", defaultValue="20010101") Date param2
		) 
	{
		Model result = new Model();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		result.addObject("param1",  df.format(param1) );
		result.addObject("param2",  df.format(param2) );
		return result;
	}
	
	@RequestMapping(path="/arrayparam")
	public Model uriDateParam( @RequestParameter("sampleval") int[] params) 
	{
		Model result = new Model();
		result.addObject("param1",  Integer.toString(params[0]) );
		result.addObject("param2",  Integer.toString(params[1]) );
		result.addObject("param3",  Integer.toString(params[2]) );
		return result;		
	}
}

