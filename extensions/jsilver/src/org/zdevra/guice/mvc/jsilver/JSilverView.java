package org.zdevra.guice.mvc.jsilver;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.View;

import com.google.clearsilver.jsilver.JSilver;
import com.google.clearsilver.jsilver.data.Data;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class JSilverView implements View {

// ------------------------------------------------------------------------
	
	private final String viewFile;
	@Inject private JSilver jSilver;		
	@Inject private ModelService modelService;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor used by {@link JSilverModule}
	 */
	JSilverView(String file) {
		this.viewFile = file;
	}
	
	
	/**
	 * Constructor
	 */
	public JSilverView(String file, Injector injector) {
		this(
			file,
			(JSilver)injector.getInstance(JSilver.class),
			(ModelService)injector.getInstance(ModelService.class) );
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param jSilver
	 * @param viewFile
	 * @param modelName
	 */
	JSilverView(String viewFile, JSilver jSilver, ModelService modelService) {
		this.jSilver = jSilver;
		this.viewFile = viewFile;
		this.modelService = modelService;
	}

// ------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public void render(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) 
		throws Exception 
	{		
		//prepare data
		Data data = jSilver.createData();
		List<String> attrNames = Collections.list(request.getAttributeNames());
		for (String attrName : attrNames) {
			Object attr = request.getAttribute(attrName);
			modelService.convert(attrName, attr, data);
		}
		
		//render view
		StringBuffer buf = new StringBuffer();
		jSilver.render(viewFile, data, buf);
		response.getOutputStream().write(buf.toString().getBytes());
		response.getOutputStream().flush();
	}
	
// ------------------------------------------------------------------------
}
