package org.zdevra.guice.mvc.velocity;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.zdevra.guice.mvc.View;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * The view provide rendering of output HTML via Velocity template
 * engine
 */
public class VelocityView implements View {
	
// ------------------------------------------------------------------------
	
	private final String viewFile;
	@Inject private VelocityEngine velocity;
	
// ------------------------------------------------------------------------

	/**
	 * Only {@link VelocityModule} can use this constructor 
	 */
	VelocityView(String viewFile) {
		this.viewFile = viewFile;
	}

	/**
	 * Constructor 
	 * @param viewFile
	 * @param injector
	 */
	public VelocityView(String viewFile, Injector injector) {
		this.viewFile = viewFile;
		this.velocity = injector.getInstance(VelocityEngine.class);  
	}
	
	/**
	 * Constructor
	 * @param viewFile
	 * @param velocity
	 */
	public VelocityView(String viewFile, VelocityEngine velocity) 
	{
		this.viewFile = viewFile;
		this.velocity = velocity;
	}

// ------------------------------------------------------------------------
	
	@Override
	@SuppressWarnings("unchecked")
	public void render(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) 
		throws Exception 
	{
		Template velocityTemplate = velocity.getTemplate(viewFile);
	
		if (velocityTemplate != null) {
			//prepare data
			VelocityContext context = new VelocityContext();
			List<String> attrNames = Collections.list(request.getAttributeNames());
			for (String attrName : attrNames) {
				Object attr = request.getAttribute(attrName);
				context.put(attrName, attr);
			}
			
			//render
			ByteArrayOutputStream bout = new ByteArrayOutputStream(2048);		
			Writer out = new OutputStreamWriter(new BufferedOutputStream(bout));	
			velocityTemplate.merge(context, out);
			out.flush();
			response.getWriter().write(bout.toString());
		}
	}
	
// ------------------------------------------------------------------------
	
}
