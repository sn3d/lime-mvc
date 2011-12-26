/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *****************************************************************************/
package org.zdevra.guice.mvc;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.RequestMapping;

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
