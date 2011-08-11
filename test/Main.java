import org.zdevra.guice.mvc.case1.Case1Test;


public class Main {

	public static void main(String[] args) {
		try {
			//GuiceExceptionResolverTest t = new GuiceExceptionResolverTest();
			//t.testHandling();
			//t.testHandlingMethodException();
			//t.testHandlingInheritance();
			
			Case1Test t = new Case1Test();
			t.prepare();
			t.testSimpleRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
