package org.zdevra.guice.mvc;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

@Test
public class ParamProcessorTest {
	
	private Injector injector;
	private ParamProcessorsService paramService;
	
	
	
	@BeforeClass
	public void prepare() {
		this.injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(BillingService.class).annotatedWith(Names.named("ImplA")).to(BillingServiceImplA.class);
				bind(BillingService.class).annotatedWith(Names.named("ImplB")).to(BillingServiceImplB.class);
				bind(Printer.class).toInstance(new Printer());
			}			
		});
		
		this.paramService = new ParamProcessorsService();		
	}
	
	
	
	@Test
	public void testInjectorParam() throws SecurityException, NoSuchMethodException 
	{			
		Method method = null;
		
		method = InjectorController.class.getMethod("inject1", BillingService.class, Printer.class);		
		invokeAndTestInjectorMethod(method, "inject1 impl a");
		
		method = InjectorController.class.getMethod("inject2", BillingService.class, Printer.class);		
		invokeAndTestInjectorMethod(method, "inject2 impl a");
		
		method = InjectorController.class.getMethod("inject3", BillingService.class, Printer.class);		
		invokeAndTestInjectorMethod(method, "inject3 impl b");

		method = InjectorController.class.getMethod("inject4", BillingService.class, Printer.class);		
		invokeAndTestInjectorMethod(method, "inject4 impl b");
	}
	
	
	private void invokeAndTestInjectorMethod(Method method, String expectedResult) {	
		MethodInvoker invoker = MethodInvokerImpl.createInvokerForTest(method, paramService);		
		InvokeData data = new InvokeData(null, null, null, null, new InjectorController(), null, injector);		
		ModelAndView mav = invoker.invoke(data);				
		
		Model m = mav.getModel();
		String res = (String)m.getObject("test-result");		
		Assert.assertNotNull(res);
		Assert.assertEquals(expectedResult, res);		
	}
	
	
	@Test
	public void testHttpSessionParam() throws SecurityException, NoSuchMethodException 
	{
		//prepare mocks and data
		HttpSession session = createMock(HttpSession.class);
		expect(session.getAttribute("test")).andReturn("testval");
		
		HttpServletRequest req = createMock(HttpServletRequest.class);
		expect(req.getSession()).andReturn(session);
		
		
		InvokeData data = new InvokeData.Builder()
			.newInstance(new HttpSessinonController())
			.withHttpRequest(req)
			.get();	
		
		//get & invoke method
		replay(session);
		replay(req);
		Method method = HttpSessinonController.class.getMethod("sessionMethod", HttpSession.class);
		MethodInvoker invoker = MethodInvokerImpl.createInvokerForTest(method, paramService);						
		ModelAndView mav = invoker.invoke(data);				

		//process result
		Model m = mav.getModel();
		String res = (String)m.getObject("test-result");
		Assert.assertEquals("sessionMethod:testval", res);
	}
	
	
}
