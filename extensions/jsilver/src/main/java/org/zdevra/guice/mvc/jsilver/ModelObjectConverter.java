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

import com.google.clearsilver.jsilver.data.Data;

/**
 * You will extends this class for your custom objects in model.
 * @param <T>
 * 
 * @See {@link JSilverModule}
 */
public abstract class ModelObjectConverter<T> implements ModelConverter {

// ------------------------------------------------------------------------
    private final Class<T> clazz;

// ------------------------------------------------------------------------
    public abstract void convertObject(T obj, Data data, ModelService convService);

// ------------------------------------------------------------------------
    /**
     * Constructor
     */
    public ModelObjectConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

// ------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    @Override
    public final boolean convert(String name, Object obj, Data data, ModelService convService) {
        if (obj != null) {
            if (clazz.isInstance(obj)) {
                Data objectData = data.createChild(name);
                convertObject((T) obj, objectData, convService);
                return true;
            }
        }
        return false;
    }
// ------------------------------------------------------------------------
}
