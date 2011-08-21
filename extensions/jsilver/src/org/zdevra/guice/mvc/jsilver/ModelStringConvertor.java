package org.zdevra.guice.mvc.jsilver;

import com.google.clearsilver.jsilver.data.Data;

public class ModelStringConvertor implements ModelConvertor{
	
	@Override
	public boolean convert(String name, Object obj, Data data, ModelService service) {
		if (obj == null) {
			data.setValue(name, "null"); 
		}
		data.setValue(name, obj.toString());
		return true;
	}

}
