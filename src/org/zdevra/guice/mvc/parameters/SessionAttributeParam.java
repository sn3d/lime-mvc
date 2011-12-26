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
package org.zdevra.guice.mvc.parameters;

import javax.servlet.http.HttpSession;

import org.zdevra.guice.mvc.InvokeData;
import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.annotations.SessionParameter;

/**
 * The parameter's processor is associated with {@link SessionParameter} annotation
 * and supports building process of arguments with this annotation.
 * <p>
 * 
 * for example:
 * <pre class="prettyprint">
 *  {@literal @}Path(path="/loggeduserinfo") 
 *  public void getInfoAboutLoggedUser({@literal @}SessionParameter("loggeduser") Customer customer) {
 *    ...			
 *	}
 * </pre>
 * 
 * 
 * In this case, processor build the customer's argument and fills it with the value from the 
 * session with the name 'loggeduser'. If this attribute doesn't exist in the session, then is 
 * filled by null. In case when in 'loggeduser' session attribute is object
 * which is not instance of customer, then exception is throwed.
 *  
 */
public class SessionAttributeParam implements ParamProcessor {
/*---------------------------- m. variables ----------------------------*/

	private final String nameInSession;
	private final Class<?> paramType;
	
/*----------------------------------------------------------------------*/
	
	/**
	 * Factory class for {@link SessionAttributeParam}
	 */
	public static class Factory implements ParamProcessorFactory {

		@Override
		public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
			SessionParameter annotation = Utils.getAnnotation(SessionParameter.class, metadata.getAnnotations());
			if (annotation == null) {
				return null;
			}						
			return new SessionAttributeParam(annotation.value(), metadata.getType());
		}		
	}


/*---------------------------- constructors ----------------------------*/

	/**
	 * Constructor
	 */
	private SessionAttributeParam(String nameInSession, Class<?> paramType) {
		this.nameInSession = nameInSession;
		this.paramType = paramType;
	}
	
/*------------------------------- methods ------------------------------*/

	@Override
	public Object getValue(InvokeData data) {
		HttpSession session = data.getRequest().getSession(true);
		Object obj = session.getAttribute(this.nameInSession);
		if (obj != null) {
			if (!paramType.isInstance(obj)) {
				throw new IllegalStateException("the object in the session is not " + paramType.getName() + " but is " + obj.getClass().getName());
			}
		}
		return obj;
	}

/*----------------------------------------------------------------------*/
}
