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

/**
 * This package contains all classes and related annotations, which provide conversions 
 * from strings to concrete data types.
 * 
 * Numbers converts automatically. Special cases are Boolean and Date types. For
 * parameters of these types are used special annotations: 
 * 
 * <dl>
 * <dt>{@link org.zdevra.guice.mvc.convertors.BooleanConv}
 * <dd>The annotation define how the string will convert to boolean.
 * 
 * <dt>{@link org.zdevra.guice.mvc.convertors.DateConv}
 * <dd>The annotation define how the string will convert to Date.
 * </dl>
 * 
 *  
 *  
 */
package org.zdevra.guice.mvc.convertors;