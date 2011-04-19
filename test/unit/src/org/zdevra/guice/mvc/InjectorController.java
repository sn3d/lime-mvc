package org.zdevra.guice.mvc;

import javax.inject.Inject;
import javax.inject.Singleton;

class BillingService {
	
	private String bill;
	
	public BillingService(String bill) {
		this.bill = bill;
	}
	
	public String getBill() {
		return this.bill;
	}
}

class BillingServiceImplA extends BillingService {
	public BillingServiceImplA() {
		super("impl a");
	}	
}

class BillingServiceImplB extends BillingService {
	public BillingServiceImplB() {
		super("impl b");
	}	
}

class Printer {
	public void print() {		
	}
}


@Controller
@Singleton
public class InjectorController {
	
	@Inject
	@RequestMapping(path="/inject1")
	public String inject1(@javax.inject.Named("ImplA") BillingService billingService, Printer printer) {
		printer.print();
		return "inject1 " + billingService.getBill();
	}

	
	@Inject
	@RequestMapping(path="/inject2")
	public String inject2(@com.google.inject.name.Named("ImplA") BillingService billingService, Printer printer) {
		printer.print();
		return "inject2 " + billingService.getBill();
	}

	
	@Inject
	@RequestMapping(path="/inject3")
	public String inject3(@javax.inject.Named("ImplB") BillingService billingService, Printer printer) {
		printer.print();
		return "inject3 " + billingService.getBill();
	}

	
	@Inject
	@RequestMapping(path="/inject4")
	public String inject4(@com.google.inject.name.Named("ImplB") BillingService billingService, Printer printer) {
		printer.print();
		return "inject4 " + billingService.getBill();
	}
		
	

}
