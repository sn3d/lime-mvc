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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.clearsilver.jsilver.data.Data;
import com.google.inject.Singleton;


/**
 * The class is responsible for converting collections
 * to JSilver data
 */
@Singleton
class ModelCollectionConverter implements ModelConverter {
	
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
