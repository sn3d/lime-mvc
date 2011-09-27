package org.zdevra.guice.mvc;

import com.google.inject.Binder;
import org.zdevra.guice.mvc.ControllerDefinition;

import javax.servlet.http.HttpServlet;
import java.util.logging.Logger;

/**
 * The class is extended version of the {@link org.zdevra.guice.mvc.ControllerDefinition} which
 * creates not regular Servlet Dispatcher but Async version of the Servlet Dispatcher
 */
public class AsyncControllerDefinition extends ControllerDefinition {

// ------------------------------------------------------------------------

    private static final Logger  logger  = Logger.getLogger(AsyncControllerDefinition.class.getName());
    public  static final Factory FACTORY = new Factory();

// ------------------------------------------------------------------------

    /**
     * Hidden constructor. The class is instantiated via Factory
     * interface.
     */
    private AsyncControllerDefinition(String urlPattern) {
        super(urlPattern);
    }


    @Override
    public HttpServlet createServlet(Binder binder) {
        logger.info("for path '" + getUrlPattern() + "' should be registered follwing async controllers: " + this.controllers);
        return new MvcDispatcherServlet(this.controllers);
    }
}
