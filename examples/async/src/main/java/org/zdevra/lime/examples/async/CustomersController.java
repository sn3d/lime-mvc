package org.zdevra.lime.examples.async;

import org.zdevra.lime.examples.async.Book;
import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.RequestParameter;
import org.zdevra.guice.mvc.annotations.View;;

import java.util.LinkedList;
import java.util.List;

/**
 * Main application controller with actions
 */
@Controller
@View("main")
public class CustomersController {

    /**
     * Method is simulating the long term operation with some delay and returns customers
     * @return
     */
    @Path("/get") @Model("customers")
    public List<Customer> getCustomers()
    {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            //do nothing (I know, it's ugly but the try-catch block is only imaginary delay)
        }

        List<Customer> customers = new LinkedList<Customer>();
        Customer c = null;

        c = new Customer(
                "Kenny",
                "kenny@southpark.com"
        );
        customers.add(c);

        c = new Customer(
                "Stan",
                "stan@southpark.com"
        );
        customers.add(c);

        return customers;
    }

}
