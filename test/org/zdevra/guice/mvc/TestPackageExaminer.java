package org.zdevra.guice.mvc;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.google.inject.Guice;

@Test
public class TestPackageExaminer {
	
	@Test
	public void testExaminerDir() {
		final List<Class<?>> out = new LinkedList<Class<?>>();
		
		PackageExaminer examiner = new PackageExaminer( new ClassExamineFunctor() {			
			@Override
			public void clazz(Class<?> clazz) {
				out.add(clazz);
			}
		});
				
		examiner.examinePackage(MvcModule.class, "org.zdevra.guice.mvc");		
		Assert.assertTrue( out.size() > 0 );
		System.out.println(out);
	}
	
	
	@Test
	public void testExaminerJar() {
		final List<Class<?>> out = new LinkedList<Class<?>>();
		
		PackageExaminer examiner = new PackageExaminer( new ClassExamineFunctor() {			
			@Override
			public void clazz(Class<?> clazz) {
				out.add(clazz);
			}
		});
				
		examiner.examinePackage(Guice.class, "com.google.inject");		
		Assert.assertTrue( out.size() > 0 );
		System.out.println(out);
	}

}
