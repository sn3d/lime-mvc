import org.zdevra.guice.mvc.case1.Case1Test;


public class Main {

	public static void main(String[] args) {
		try {
			Case1Test t = new Case1Test();
			t.prepare();
			t.testSimpleRequest();
			t.testFromSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
