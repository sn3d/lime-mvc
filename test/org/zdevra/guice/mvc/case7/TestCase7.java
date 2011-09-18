package org.zdevra.guice.mvc.case7;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.servlet.http.HttpServlet;

import junit.framework.Assert;

import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AutoConfMvcModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.zdevra.guice.mvc.ConversionService;

/**
 * This is a test for automatic configuration module
 */
@Test
public class TestCase7 {

    @Test
	public void testBasic() {
		AutoConfMvcModule module = new AutoConfMvcModule(TestCase7.class, "org.zdevra.guice.mvc.case7");

		Injector injector = Guice.createInjector(module);		
		List<HttpServlet> servlets = module.getServlets();
		
		ServiceA serviceA = injector.getInstance(ServiceA.class);
		ServiceB serviceB = injector.getInstance(ServiceB.class);

		Assert.assertTrue(serviceA instanceof ServiceAImpl);		
		Assert.assertTrue(serviceB instanceof ServiceBImpl);		
		Assert.assertTrue(servlets.size() == 1);

        //autoconf for converters
        ConversionService conversionService = injector.getInstance(ConversionService.class);
        Assert.assertNotNull(conversionService);
        ConversionService.Converter<?> converter = conversionService.getConverter(TestCase7.class, new Annotation[]{});
        Assert.assertNotNull(converter);
		
		System.out.println("END");
	}

}
