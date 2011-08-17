import org.zdevra.guice.mvc.case6.Case6Test;


public class Main {

	public static void main(String[] args) {
		try {
			Case6Test t = new Case6Test();
			t.prepare();
			
			t.testCarsMethod();
			t.testPeopleMethod();
			t.testCommonMethod();
			
			t.testUnknownMethod();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
