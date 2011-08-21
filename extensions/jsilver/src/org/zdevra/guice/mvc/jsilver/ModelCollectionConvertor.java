package org.zdevra.guice.mvc.jsilver;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.clearsilver.jsilver.data.Data;


public class ModelCollectionConvertor implements ModelConvertor {
	

// ------------------------------------------------------------------------

	@Override
	public boolean convert(String name, Object obj, Data data, ModelService convetorService) {
		
		if (!List.class.isInstance(obj)) {
			return false;	
		}
		
		Data collectionData = data.createChild(name);
		Collection<?> collection = (Collection<?>)obj;
		Iterator<?> iterator = collection.iterator();
		
		int pos = 0;
		while (iterator.hasNext()) {
			Object item = iterator.next();
			convetorService.convert(Integer.toString(pos), item, collectionData);
			pos++;
		}
		
		return true;
	}
	
// ------------------------------------------------------------------------

}
