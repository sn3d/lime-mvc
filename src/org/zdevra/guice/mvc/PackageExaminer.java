package org.zdevra.guice.mvc;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


/**
 * The package examiner go through all classes in given package
 * and his subpackages and for each class calls the {@link ClassExamineFunctor}'s
 * clazz() method.
 */
public class PackageExaminer 
{	
// ------------------------------------------------------------------------
	
	private final ClassExamineFunctor functor;
	
// ------------------------------------------------------------------------

	/**
	 * Constructor
	 */
	public PackageExaminer(ClassExamineFunctor functor) {
		this.functor = functor;
	}
	
// ------------------------------------------------------------------------	
	
	/**
	 * The method examines all classes in given packages.
	 * 
	 * @param packageName
	 */
	public void examinePackage(Class<?> someClassInPackage, String packageName)  
	{
		try {
			URL resource = someClassInPackage.getProtectionDomain().getCodeSource().getLocation();
			String resourcePath = resource.getFile();
			String packagePath = packageName.replaceAll("\\.", "/");
			
			if (resourcePath.endsWith(".jar")) {
				exploreJar(new File(resource.toURI()), packagePath);			
			} else {
				exploreDir(new File(resource.toURI()), packagePath);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void exploreJar(File jar, String packageName) throws FileNotFoundException, IOException, ClassNotFoundException 
	{
		JarInputStream jarIs = new JarInputStream(new FileInputStream(jar));		
		JarEntry jarEntry = null; 
		
		while ( (jarEntry = jarIs.getNextJarEntry()) != null ) 
		{
			String className = jarEntry.getName();
			if (className.startsWith(packageName)) 
			{
				if (className.endsWith(".class")) 
				{
					className = className.substring(0, (className.length() - ".class".length()) );
					className = className.replace("/",   ".");
					Class<?> clazz = Class.forName(className);
					functor.clazz(clazz);
				}
			}
		}		
	}
	
	
	private void exploreDir(File dir, String packageName) throws IOException, ClassNotFoundException {
		//open the package's directory
		packageName = packageName.replace("/", File.separator);
		String pckPath = dir.getAbsoluteFile() + File.separator + packageName;
		File packageDir = new File(pckPath);		
		explorePackageDir(packageDir, packageName);
	}
	
	
	private void explorePackageDir(File packageDir, String packagePath) throws IOException, ClassNotFoundException {
		if (packageDir.exists()) {
			if (packageDir.isDirectory()) 
			{					
				String packageName =  transformPackageName(packageDir, packagePath);				
				File[] files = packageDir.listFiles();
				for (int i = 0; i < files.length; ++i) {
					File file = files[i];
					if (!file.isDirectory()) 
					{
						String className = file.getName();
						if (className.endsWith(".class")) {
							className = className.substring(0, (className.length() - 6));
							className = packageName + "." + className;							
							Class<?> clazz = Class.forName(className);
							functor.clazz(clazz);
						}						
					} else {
						explorePackageDir(file, packagePath);
					}
				}
			}
		}				
	}
	
	
	private String transformPackageName(File packageDir, String rootPackagePath) 
	{
		String packagePath = packageDir.getPath();
		int index = packagePath.indexOf(rootPackagePath);		
		String out = "";
		
		if (File.separator.equals("\\")) 
		{
			out = packagePath.substring(index);
			out = out.replaceAll("\\\\",".");
		} else {
			out = packagePath.substring(index).replaceAll("/",".");
		}
		
		return out;
	}
	
// ------------------------------------------------------------------------
}
