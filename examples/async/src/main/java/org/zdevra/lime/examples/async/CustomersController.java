package org.zdevra.lime.examples.async;

import org.zdevra.lime.examples.async.Book;
import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.ModelName;
import org.zdevra.guice.mvc.Path;
import org.zdevra.guice.mvc.RequestParameter;
import org.zdevra.guice.mvc.views.ToView;

import java.util.LinkedList;
import java.util.List;

/**
 * Main application controller with actions
 */
@Controller
@ToView("main")
public class CustomersController {

    /**
     * Method is simulating the long term operation with some delay and returns customers
     * @return
     */
    @Path("/get") @ModelName("customers")
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
