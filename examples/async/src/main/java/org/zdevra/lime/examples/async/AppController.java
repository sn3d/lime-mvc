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
public class AppController {


    /**
     * Method is simulating the long term operation with some delay and returns books
     * @return
     */
    @Path("/get") @ModelName("books")
    public List<Book> getBooks()
    {
        System.out.println("books");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            //do nothing (I know, it's ugly but the try-catch block is only imaginary delay)
        }

        List<Book> books = new LinkedList<Book>();
        Book b = null;

        b = new Book(
                "Design Patterns: Elements of Reusable Object-Oriented Software",
                "Gamma,Helm,Johnson,Vlissides"
        );
        books.add(b);

        b = new Book(
                "Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions",
                "Hohpe,Woolf"
        );

        books.add(b);

        return books;
    }


    /**
     * Method is simulating the long term operation with some delay and returns customers
     * @return
     */
    @Path("/get") @ModelName("customers")
    public List<Customer> getCustomers()
    {
        System.out.println("customers");
        try {
            Thread.sleep(2000);
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
