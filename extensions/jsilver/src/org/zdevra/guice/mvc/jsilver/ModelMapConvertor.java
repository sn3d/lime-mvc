package org.zdevra.guice.mvc.jsilver;

import java.util.Map;

import com.google.clearsilver.jsilver.data.Data;
import com.google.inject.Singleton;

@Singleton
public class ModelMapConvertor implements ModelConvertor {
		
	@Override
	public boolean convert(String name, Object obj, Data data, ModelService convertService) {		
		if (!Map.class.isInstance(obj)) {
			return false;
		}
		
		Data mapData = data.createChild(name);
		Map<?, ?> map = (Map<?,?>)obj;
		for (Map.Entry<?,?> e : map.entrySet()) {
			String key = e.getKey().toString();
			Object val = e.getValue();			
			convertService.convert(key, val, mapData);			
		}
		
		return true;
	}

}
