import org.zdevra.guice.mvc.case5.Case5Test;


public class Main {

	public static void main(String[] args) {
		try {
			Case5Test t = new Case5Test();
			t.prepare();
			t.testDefaultView();
			t.testToViewAnnotation();
			t.testToViewParam();
			t.testCustomAnnotation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
