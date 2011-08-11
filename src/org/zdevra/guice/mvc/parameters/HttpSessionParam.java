package org.zdevra.guice.mvc.parameters;

import javax.servlet.http.HttpSession;

import org.zdevra.guice.mvc.InvokeData;

/**
 * The parameter's processor forwards the session object into the method's parameter. 
 * <p>
 * 
 * <pre class="prettyprint">
 *  {@literal @}RequestMapping(path="/somepath") 
 *  public void getInfoAboutLoggedUser(HttpSession session) {
 *    ...			
 *	}
 * </pre>
 * <p>
 *
 */
public class HttpSessionParam implements ParamProcessor {

/*----------------------------------------------------------------------*/
	
	public static class Factory implements ParamProcessorFactory {
		
		private final ParamProcessor processor = new HttpSessionParam();
		
		@Override
		public ParamProcessor buildParamProcessor(ParamMetadata metadata) {			
			if (metadata.getType() != HttpSession.class) {
				return null;
			}
			return processor;
		}		
	}

/*---------------------------- constructors ----------------------------*/

	/**
	 * Constructor
	 */
	private HttpSessionParam() {
	}

	
	@Override
	public Object getValue(InvokeData data) {
		HttpSession session = data.getRequest().getSession();
		return session;
	}

/*----------------------------------------------------------------------*/
}
