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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.exceptions.MethodInvokingException;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

class Output {

    public String msg;
    private static Output instance;

    public static Output getInstance() {
        if (instance == null) {
            instance = new Output();
        }
        return instance;
    }
}

class Custom1ArgumentException extends IllegalArgumentException {
}

class Custom2ArgumentException extends Custom1ArgumentException {
}

class Custom3ArgumentException extends Custom2ArgumentException {
}

class Custom4ArgumentException extends Custom3ArgumentException {
}

class FirstCustomHandler implements ExceptionHandler {

    @Override
    public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        Output.getInstance().msg = "FirstCustomHandler";
    }
}

class SecondCustomHandler implements ExceptionHandler {

    private final String msg;

    public SecondCustomHandler(String msg) {
        this.msg = msg;
    }

    @Override
    public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        Output.getInstance().msg = "SecondCustomHandler:" + msg;
    }
}

class DefaultHandler implements ExceptionHandler {

    @Override
    public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        Output.getInstance().msg = "DefaulHandler:" + t.getMessage();
    }
}

class TestExceptionModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<ExceptionBind> exceptionBinder = Multibinder.newSetBinder(binder(), ExceptionBind.class);

        exceptionBinder.addBinding().toInstance(
                ExceptionBind.toClass(FirstCustomHandler.class, NullPointerException.class, 1));

        exceptionBinder.addBinding().toInstance(
                ExceptionBind.toInstance(new SecondCustomHandler("Custom1ArgumentException"), Custom1ArgumentException.class, 2));

        exceptionBinder.addBinding().toInstance(
                ExceptionBind.toInstance(new SecondCustomHandler("IllegalArgumentException"), IllegalArgumentException.class, 3));

        bind(ExceptionHandler.class)
                .annotatedWith(Names.named(GuiceExceptionResolver.DEFAULT_EXCEPTIONHANDLER_NAME))
                .to(DefaultHandler.class);

        bind(ExceptionResolver.class)
                .to(GuiceExceptionResolver.class);
    }
}

class TestExceptionInheritModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(ExceptionResolver.class)
                .to(GuiceExceptionResolver.class);

        Multibinder<ExceptionBind> exceptionBinder = Multibinder.newSetBinder(binder(), ExceptionBind.class);

        exceptionBinder.addBinding().toInstance(
                ExceptionBind.toInstance(new SecondCustomHandler("Custom4ArgumentException"), Custom4ArgumentException.class, 1));

        exceptionBinder.addBinding().toInstance(
                ExceptionBind.toInstance(new SecondCustomHandler("Custom3ArgumentException"), Custom3ArgumentException.class, 2));

        exceptionBinder.addBinding().toInstance(
                ExceptionBind.toInstance(new SecondCustomHandler("Custom2ArgumentException"), Custom2ArgumentException.class, 3));

        exceptionBinder.addBinding().toInstance(
                ExceptionBind.toInstance(new SecondCustomHandler("Custom1ArgumentException"), Custom1ArgumentException.class, 4));

        bind(ExceptionHandler.class)
                .annotatedWith(Names.named(GuiceExceptionResolver.DEFAULT_EXCEPTIONHANDLER_NAME))
                .to(DefaultHandler.class);
    }
}

@Test
public class GuiceExceptionResolverTest {

    @Test
    public void testHandling() {
        Injector injector = Guice.createInjector(new TestExceptionModule());
        ExceptionResolver resolver = injector.getInstance(ExceptionResolver.class);

        resolver.handleException(new NullPointerException(), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("FirstCustomHandler"));

        resolver.handleException(new Custom1ArgumentException(), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("SecondCustomHandler:Custom1ArgumentException"));

        resolver.handleException(new IllegalArgumentException(), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("SecondCustomHandler:IllegalArgumentException"));

        resolver.handleException(new RuntimeException("runtime"), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("DefaulHandler:runtime"));

    }

    @Test
    public void testHandlingMethodException() {
        Injector injector = Guice.createInjector(new TestExceptionModule());
        ExceptionResolver resolver = injector.getInstance(ExceptionResolver.class);

        resolver.handleException(new MethodInvokingException(null, new NullPointerException()), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("FirstCustomHandler"));

        resolver.handleException(new MethodInvokingException(null, new Custom1ArgumentException()), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("SecondCustomHandler:Custom1ArgumentException"));

        resolver.handleException(new MethodInvokingException(null, new IllegalArgumentException()), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("SecondCustomHandler:IllegalArgumentException"));

        resolver.handleException(new MethodInvokingException(null, new RuntimeException("runtime")), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("DefaulHandler:runtime"));

    }

    @Test
    public void testHandlingInheritance() {
        Injector injector = Guice.createInjector(new TestExceptionInheritModule());
        ExceptionResolver resolver = injector.getInstance(ExceptionResolver.class);

        resolver.handleException(new Custom1ArgumentException(), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("SecondCustomHandler:Custom1ArgumentException"));

        resolver.handleException(new Custom2ArgumentException(), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("SecondCustomHandler:Custom2ArgumentException"));

        resolver.handleException(new Custom3ArgumentException(), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("SecondCustomHandler:Custom3ArgumentException"));

        resolver.handleException(new Custom4ArgumentException(), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("SecondCustomHandler:Custom4ArgumentException"));

        resolver.handleException(new IllegalArgumentException("illegal"), null, null, null);
        Assert.assertTrue(Output.getInstance().msg.contains("DefaulHandler:illegal"));
    }
}
