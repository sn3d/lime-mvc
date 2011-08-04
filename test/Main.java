import org.zdevra.guice.mvc.GuiceExceptionResolverTest;


public class Main {

	public static void main(String[] args) {
		try {
			GuiceExceptionResolverTest t = new GuiceExceptionResolverTest();
			t.testHandling();
			t.testHandlingInheritance();
		} catch (Exception e) {
			
		}
	}

}
