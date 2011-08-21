package org.zdevra.guice.mvc.jsilver;

import com.google.clearsilver.jsilver.data.Data;

public class ModelSilverConvertor implements ModelConvertor {
	
	@Override
	public boolean convert(String name, Object obj, Data data, ModelService convertService) {
		if (Data.class.isInstance(obj)) {
			data.setSymlink(name, (Data)obj);
			return true;
		}
		return false;		
	}

}
