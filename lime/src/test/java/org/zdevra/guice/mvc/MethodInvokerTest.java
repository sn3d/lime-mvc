package org.zdevra.guice.mvc;

import junit.framework.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.parameters.ParamProcessor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Zdenko Vrabel (zdenko.vrabel@celum.com)
 */
@Test
public class MethodInvokerTest {

    @Test
    public void sortMethods() {
        List<ParamProcessor> paramProcs = Collections.emptyList();
        MappingData data = new MappingData(null, null, HttpMethodType.POST, "", "", null);


        MethodInvoker a = new MethodInvokerImpl(null, null, null, null, paramProcs, 0);
        MethodInvoker b = new MethodInvokerImpl(null, null, null, null, paramProcs, -10);
        MethodInvoker c = new MethodInvokerImpl(null, null, null, null, paramProcs, 0);
        MethodInvoker d = new MethodInvokerFilter(data, new MethodInvokerImpl(null, null, null, null, paramProcs, 20));
        MethodInvoker e = new MethodInvokerImpl(null, null, null, null, paramProcs, 10);

        List<MethodInvoker> invokers = new LinkedList<MethodInvoker>();
        invokers.add(a);
        invokers.add(b);
        invokers.add(c);
        invokers.add(d);
        invokers.add(e);

        Collections.sort(invokers);

        Assert.assertEquals(b, invokers.get(0));
        Assert.assertEquals(e, invokers.get(3));
        Assert.assertEquals(d, invokers.get(4));

    }
}
