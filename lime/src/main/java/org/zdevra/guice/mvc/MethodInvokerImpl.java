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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.zdevra.guice.mvc.annotations.Priority;
import org.zdevra.guice.mvc.exceptions.MethodInvokingException;
import org.zdevra.guice.mvc.parameters.ParamMetadata;
import org.zdevra.guice.mvc.parameters.ParamProcessor;
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;

/**
 * Class prepare data for controller's method, call the method
 * and process the method's result.
 * 
 * For data preparation is used {@link ParamProcessorsService} which use
 * different processors defined in a package {@link org.zdevra.guice.mvc.parameters} 
 *
 */
class MethodInvokerImpl implements MethodInvoker {

    /*---------------------------- m. variables ----------------------------*/
    private final Class<?> controllerClass;
    private final ViewPoint defaultView;
    private final String resultName;
    private final Method method;
    private final List<ParamProcessor> paramProcs;
    private final int priority;

    /*---------------------------- constructors ----------------------------*/
    public static MethodInvoker createInvoker(MappingData reqMapping) {
        ParamProcessorsService paramService = reqMapping.injector.getInstance(ParamProcessorsService.class);
        ConversionService convertService = reqMapping.injector.getInstance(ConversionService.class);
        ViewScannerService viewScannerService = reqMapping.injector.getInstance(ViewScannerService.class);

        ViewPoint defaultView = viewScannerService.scan(reqMapping.method.getAnnotations());
        if (defaultView == ViewPoint.NULL_VIEW) {
            defaultView = viewScannerService.scan(reqMapping.controllerClass.getAnnotations());
        }

        List<ParamProcessor> processors = scanParams(reqMapping.method, paramService, convertService);
        String resultName = reqMapping.resultName;

        Priority priorityAnnotation = reqMapping.method.getAnnotation(Priority.class);
        int priority = Priority.DEFAULT;
        if (priorityAnnotation != null) {
            priority = priorityAnnotation.value();
        }

        MethodInvoker invoker = new MethodInvokerImpl(reqMapping.controllerClass, reqMapping.method, defaultView, resultName, processors, priority);
        return invoker;
    }

    /**
     * Hidden constructor. The Invoker is constructed through the factory methods.
     * @param defaultView
     * @param resultName
     * @param method
     * @param paramProcs
     */
    MethodInvokerImpl(Class<?> controllerClass, Method method, ViewPoint defaultView, String resultName, List<ParamProcessor> paramProcs, int priority) {
        this.controllerClass = controllerClass;
        this.defaultView = defaultView;
        this.resultName = resultName;
        this.method = method;
        this.paramProcs = Collections.unmodifiableList(paramProcs);
        this.priority = priority;
    }

    /*----------------------------------------------------------------------*/
    private static final List<ParamProcessor> scanParams(Method method, ParamProcessorsService paramService, ConversionService convertService) {
        Annotation[][] annotations = method.getParameterAnnotations();
        Class<?>[] types = method.getParameterTypes();
        List<ParamProcessor> result = new LinkedList<ParamProcessor>();

        for (int i = 0; i < types.length; ++i) {
            ParamMetadata metadata = new ParamMetadata(types[i], annotations[i], convertService, method);
            ParamProcessor processor = paramService.createProcessor(metadata);
            result.add(processor);
        }

        return result;
    }

    public ModelAndView invoke(InvokeData data) {
        try {
            Object controllerObj = data.getInjector().getInstance(controllerClass);
            if (controllerObj == null) {
                throw new NullPointerException("null controller");
            }

            Object[] args = getValues(data);
            Object result = method.invoke(controllerObj, args);
            ModelAndView mav = processResult(result);

            args = null;
            return mav;
        } catch (IllegalArgumentException e) {
            throw new MethodInvokingException(method, e);
        } catch (IllegalAccessException e) {
            throw new MethodInvokingException(method, e);
        } catch (InvocationTargetException e) {
            throw new MethodInvokingException(method, e.getCause());
        }
    }

    private Object[] getValues(InvokeData data) {
        Object[] out = new Object[paramProcs.size()];
        for (int i = 0; i < paramProcs.size(); ++i) {
            ParamProcessor processor = paramProcs.get(i);
            out[i] = processor.getValue(data);
        }
        return out;
    }

    private ModelAndView processResult(Object result) {
        ModelAndView out = new ModelAndView(this.defaultView);

        if (result == null) {
            return out;
        } else if (result instanceof ModelMap) {
            ModelMap resultModel = (ModelMap) result;
            out.addModel(resultModel);
        } else if (result instanceof ModelAndView) {
            return (ModelAndView) result;
        } else if (result instanceof ViewPoint) {
            ViewPoint resultView = (ViewPoint) result;
            out.addView(resultView);
        } else {
            String name = getResultModelName();
            out.getModel().addObject(name, result);
        }

        return out;
    }

    private String getResultModelName() {
        if (this.resultName == null || this.resultName.length() == 0) {
            return this.method.getName();
        }
        return this.resultName;
    }

    /*----------------------------------------------------------------------*/
    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(MethodInvoker o) {
        if (this.getPriority() < o.getPriority()) {
            return -1;
        } else if (this.getPriority() > o.getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }
}
