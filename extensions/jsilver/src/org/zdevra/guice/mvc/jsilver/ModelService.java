package org.zdevra.guice.mvc.jsilver;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.clearsilver.jsilver.data.Data;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ModelService {
	
// ------------------------------------------------------------------------
	
	private final Collection<ModelConvertor> convertors;
	private ModelConvertor defaultConvertor = new ModelStringConvertor();
	
// ------------------------------------------------------------------------

	public static ModelService createTestService() {
		return new ModelService(new ModelConvertor[] {
			new ModelCollectionConvertor(),
			new ModelMapConvertor()
		});
	}
	
	public static ModelService createTestService(ModelConvertor customConvertor) {
		return new ModelService(new ModelConvertor[] {
			new ModelCollectionConvertor(),
			new ModelMapConvertor(),
			customConvertor
		});
	}
	
// ------------------------------------------------------------------------
		
	@Inject
	public ModelService(Set<ModelConvertor> convertors) {
		this((Collection<ModelConvertor>)convertors);
	}
	
	
	public ModelService(ModelConvertor[] convertors) {
		this(Arrays.asList(convertors));
	}
	
	
	public ModelService(Collection<ModelConvertor> convertors) {
		this.convertors = Collections.unmodifiableCollection(convertors);
	}
		
// ------------------------------------------------------------------------
	
	public void convert(String name, Object obj, Data data) {		
		for (ModelConvertor binding : convertors) {
			boolean res = binding.convert(name, obj, data, this);
			if (res) {
				return;
			}
		}				
		defaultConvertor.convert(name, obj, data, this);
	}

// ------------------------------------------------------------------------

}
