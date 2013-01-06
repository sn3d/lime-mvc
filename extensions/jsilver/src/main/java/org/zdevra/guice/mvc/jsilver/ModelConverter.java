/**
 * ***************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 ****************************************************************************
 */
package org.zdevra.guice.mvc.jsilver;

import com.google.clearsilver.jsilver.data.Data;

/**
 * You will implement this interface for transformations any data to the JSilver
 * data.
 *
 * All objects are transformed to JSilver Data structure via Model Service. For
 * your custom objects in the Lime Model use the {@link ModelObjectConverter}.
 *
 * @see ModelObjectConverter
 * @see JSilverModule
 */
public interface ModelConverter {

    public boolean convert(String name, Object obj, Data data, ModelService service);
}
