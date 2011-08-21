package org.zdevra.guice.mvc.jsilver;

import com.google.clearsilver.jsilver.data.Data;

public interface ModelConvertor {

	public boolean convert(String name, Object obj, Data data, ModelService service);
	
}
