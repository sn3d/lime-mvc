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
package org.zdevra.guice.mvc.jsilver;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.clearsilver.jsilver.data.Data;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The class provide transformation of object to JSilver data. The class
 * go through all registered converters. If there is no matching converter,
 * then is used a default converter {@link ModelStringConverter}.  
 */
@Singleton
class ModelService {
	
// ------------------------------------------------------------------------
	
	private final Collection<ModelConverter> convertors;
	private ModelConverter defaultConvertor = new ModelStringConverter();
	
// ------------------------------------------------------------------------

	public static ModelService createTestService() {
		return new ModelService(new ModelConverter[] {
			new ModelCollectionConverter(),
			new ModelMapConverter()
		});
	}
	
	public static ModelService createTestService(ModelConverter customConvertor) {
		return new ModelService(new ModelConverter[] {
			new ModelCollectionConverter(),
			new ModelMapConverter(),
			customConvertor
		});
	}
	
// ------------------------------------------------------------------------
		
	@Inject
	public ModelService(Set<ModelConverter> convertors) {
		this((Collection<ModelConverter>)convertors);
	}
	
	
	public ModelService(ModelConverter[] convertors) {
		this(Arrays.asList(convertors));
	}
	
	
	public ModelService(Collection<ModelConverter> convertors) {
		this.convertors = Collections.unmodifiableCollection(convertors);
	}
		
// ------------------------------------------------------------------------
	
	public void convert(String name, Object obj, Data data) {		
		for (ModelConverter binding : convertors) {
			boolean res = binding.convert(name, obj, data, this);
			if (res) {
				return;
			}
		}				
		defaultConvertor.convert(name, obj, data, this);
	}

// ------------------------------------------------------------------------

}
