package org.zdevra.guice.mvc.jsilver;

import com.google.clearsilver.jsilver.data.Data;

public abstract class ModelObjectConvertor<T> implements ModelConvertor {

// ------------------------------------------------------------------------

	private final Class<T> clazz;
	
// ------------------------------------------------------------------------
	
	public abstract void convertObject(T obj, Data data, ModelService convService);
	
// ------------------------------------------------------------------------

	/**
	 * Constructor
	 */
	public ModelObjectConvertor(Class<T> clazz) {
		this.clazz = clazz;
	}
		
// ------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public final boolean convert(String name, Object obj, Data data, ModelService convService) {
		if (obj != null) {
			if (clazz.isInstance(obj)) {
				Data objectData = data.createChild(name);
				convertObject( (T) obj, objectData, convService );
				return true;
			}
		}
		return false;
	}
	
// ------------------------------------------------------------------------

}
