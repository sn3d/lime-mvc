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
package org.zdevra.guice.example;

import java.util.LinkedList;
import java.util.List;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.RequestParameter;
import org.zdevra.guice.mvc.UriParameter;


@Controller
public class SimpleController {
	
	@RequestMapping(path = "/departments", nameOfResult="departments")
	public Department getDepartments() {
		return new Department("some department");
	}
	
	
	@RequestMapping(path = "/employees/([a-z]+)", nameOfResult="employees", toView="/employees.jsp")
	public List<Employee> getEmployeesForDepartment(
			@UriParameter(1) String departmentId) 
	{
		List<Employee> employees = new LinkedList<Employee>();
		employees.add(new Employee("Scott", "Hartnell", departmentId));
		employees.add(new Employee("Chris", "Pronger", departmentId));
		return employees;
	}
	
	
	@RequestMapping(path = "/catalog/([a-z]+)", nameOfResult="catalogId")
	public String getCatalog(@UriParameter(1) String catalogId, @RequestParameter("val1") String val1) {
		return catalogId + " val1=" + val1;
	}

    
    @RequestMapping(path="/exception")
    public void raiseException() {
        throw new IllegalStateException("test exception");
    }
	
}
