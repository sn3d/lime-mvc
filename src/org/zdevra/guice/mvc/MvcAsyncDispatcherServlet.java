package org.zdevra.guice.mvc;

import com.google.inject.Injector;
import org.zdevra.guice.mvc.exceptions.NoMethodInvoked;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

class MvcAsyncDispatcherServlet extends MvcDispatcherServlet {

// ------------------------------------------------------------------------

    private final ThreadPoolExecutor tpe = new ThreadPoolExecutor(4, 10, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(200));
    private final Task.ITask<AsyncMessage> task = new InvokeTask();

// ------------------------------------------------------------------------

    /**
     * Class represents a message
     */
    private static class AsyncMessage
    {
        public final ClassInvoker invoker;
        public final InvokeData data;
        public final List<ModelAndView> output;
        public final List<RuntimeException> exceptions;

        public AsyncMessage(ClassInvoker invoker, InvokeData data, List<ModelAndView> output, List<RuntimeException> exceptions) {
            this.invoker = invoker;
            this.data = data;
            this.output = output;
            this.exceptions = exceptions;
        }
    }


    /**
     * Class provides invoke of the controller's method in separated thread
     */
    private static class InvokeTask implements Task.ITask<AsyncMessage> {
        @Override
        public void execute(AsyncMessage message) {
            try {
                ModelAndView mav = message.invoker.invoke(message.data);
                if (mav != null) {
                    message.output.add(mav);
                }
            } catch (RuntimeException e) {
                message.exceptions.add(e);
            }
        }
    }

// ------------------------------------------------------------------------

    /**
     * Constructor for testing purpose
     * @param controllerClass
     * @param injector
     */
	public MvcAsyncDispatcherServlet(Class<?> controllerClass, Injector injector) {
		super(controllerClass, injector);
	}


    /**
     * Constructor for testing purpose
     * @param controllers
     * @param injector
     */
    public MvcAsyncDispatcherServlet(Class<?>[] controllers, Injector injector) {
        super(controllers, injector);
    }


    /**
     * Constructor used by MvcModule
     * @param controllerClass
     */
    public MvcAsyncDispatcherServlet(Class<?> controllerClass) {
        super(controllerClass);
    }


    /**
     * Constructor used by MvcModule
     * @param controllers
     */
    public MvcAsyncDispatcherServlet(Class<?>[] controllers) {
        super(controllers);
    }


    /**
     * Main constructor used by MvcModule
     * @param controllers
     */
    public MvcAsyncDispatcherServlet(List<Class<?>> controllers) {
        super(controllers);
    }

// ------------------------------------------------------------------------

    @Override
    protected ModelAndView invoke(InvokeData data)
    {
        try {
            List<ModelAndView> mavs = Collections.synchronizedList(new LinkedList<ModelAndView>());
            List<RuntimeException> exceptions = Collections.synchronizedList(new LinkedList<RuntimeException>());
            CountDownLatch cdl = new CountDownLatch(classInvokers.size());

            //invoke all methods parallely
            for (ClassInvoker invoker : this.classInvokers) {
                AsyncMessage msg = new AsyncMessage(invoker, data, mavs, exceptions);
                Runnable command = new Task<AsyncMessage>(cdl, msg, task);
                tpe.execute(command);
            }

            cdl.await();

            if (exceptions.size() > 0) {
                throw exceptions.get(0);
            }

            if (mavs.size() == 0) {
                throw new NoMethodInvoked(data.getRequest());
            }

            //merge the result Model and Views into one
            ModelAndView mavResult = new ModelAndView();
            for (ModelAndView mav : mavs) {
                mavResult.mergeModelAndView(mav);
            }

            //post-production of model
            for (ClassInvoker classInvoker : this.classInvokers) {
                classInvoker.moveDataToSession(mavResult.getModel(), data.getRequest().getSession(true));
            }
            mavResult.getModel().moveObjectsToRequestAttrs(data.getRequest());

            return mavResult;
        } catch (InterruptedException e) {
            throw new NoMethodInvoked(data.getRequest());
        }
    }

// ------------------------------------------------------------------------

}
