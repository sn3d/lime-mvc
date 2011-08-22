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

import java.util.Map;

import com.google.clearsilver.jsilver.data.Data;
import com.google.inject.Singleton;

/**
 * The class provide transformation from Map to JSilver data
 */
@Singleton
class ModelMapConverter implements ModelConverter {
		
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
