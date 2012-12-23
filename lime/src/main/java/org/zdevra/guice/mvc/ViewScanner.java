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
package org.zdevra.guice.mvc;

import java.lang.annotation.Annotation;

/**
 * You will implement this interface if you want to add 
 * your custom annotation scanner for your custom view.
 * 
 * @see ViewScannerService
 */
public interface ViewScanner {	
	
	/**
	 * Here you will implement looking for your annotation.
	 * If your annotation is not presents, then you will return 
	 * View.NULL_VIEW.
	 *   
	 * @param annotations - annotations of the controller or controller's method 
	 * 
	 * @return instance of view for your annotation or View.NULL. Avoid to
	 * null value.
	 * 
	 * @throws Exception
	 */
	public ViewPoint scan(Annotation[] annotations);
}
