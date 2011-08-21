package org.zdevra.guice.mvc.jsilver;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.jsilver.ModelService;
import org.zdevra.guice.mvc.jsilver.ModelObjectConvertor;

import com.google.clearsilver.jsilver.JSilver;
import com.google.clearsilver.jsilver.data.Data;
import com.google.clearsilver.jsilver.resourceloader.ClassLoaderResourceLoader;

class Book {
	public final String name;
	public final String author;
	
	public Book(String name, String author) {
		this.name = name;
		this.author = author;
	}
}

@Test
public class ModelTest {
	
	public static void main(String[] args) {
		try {
			ModelTest t = new ModelTest();
			t.testCollectionConvert();
			t.testMapConvert();
			t.testComplexConvert();
			t.testCustomConvert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCollectionConvert() {
		//prepare data
		JSilver jSilver = new JSilver(
                new ClassLoaderResourceLoader(
                  MvcModule.class.getClassLoader(), 
                    "org/example/views") );
		
		Data data = jSilver.createData();

		List<Integer> x = new LinkedList<Integer>();
		x.add(10);
		x.add(20);
		x.add(30);
		
		//convert
		ModelService service = ModelService.createTestService();
		service.convert("root", x, data);
		
		//check result
		System.out.println(data.toString());		
	}
	
	@Test
	public void testMapConvert() {		
		JSilver jSilver = new JSilver(
                new ClassLoaderResourceLoader(
                  MvcModule.class.getClassLoader(), 
                    "org/example/views") );
		
		Data data = jSilver.createData();
		
		//prepare data
		Map<String, Boolean> x = new HashMap<String, Boolean>();
		x.put("val1", true);
		x.put("val2", false);
		x.put("val3", false);
		x.put("val4", true);
		
		//convert
		ModelService service = ModelService.createTestService();
		service.convert("root", x, data);
		
		//check result
		System.out.println(data.toString());		
	}
	
	
	@Test
	public void testComplexConvert() {
		
		JSilver jSilver = new JSilver(
                new ClassLoaderResourceLoader(
                  MvcModule.class.getClassLoader(), 
                    "org/example/views") );
		
		Data data = jSilver.createData();
		
		//prepare data
		List<Map<String, String>> x = new LinkedList<Map<String, String>>();
		
		Map<String,String> m = new HashMap<String, String>();
		m.put("name", "Home");
		m.put("URL",  "/");
		x.add(m);
		
		m = new HashMap<String, String>();
		m.put("name", "Preferences");
		m.put("URL",  "/pref");
		x.add(m);
		
		m = new HashMap<String, String>();
		m.put("name", "Help");
		m.put("URL",  "/help");
		x.add(m);
		
		//convert
		ModelService service = ModelService.createTestService();
		service.convert("menu", x, data);
		
		//check result
		System.out.println(data.toString());		
	}
	
	@Test
	public void testCustomConvert() {
		
		JSilver jSilver = new JSilver(
                new ClassLoaderResourceLoader(
                  MvcModule.class.getClassLoader(), 
                    "org/example/views") );
		
		Data data = jSilver.createData();
		
		//prepare data
		List<Book> books = new LinkedList<Book>();
		books.add(new Book("Programming Scala", "Wampler & Payne"));
		books.add(new Book("Modern C++ Design", "Andrei Alexandrescu"));
		

		//convert
		ModelService service = 
			ModelService.createTestService(new ModelObjectConvertor<Book>(Book.class) {

				@Override
				public void convertObject(Book obj, Data data, ModelService convService) {
					data.setValue("name",   obj.name);
					data.setValue("author", obj.author);
				}
				
			});
		
		service.convert("books", books, data);

		//check result
		System.out.println(data.toString());		
	}

}
