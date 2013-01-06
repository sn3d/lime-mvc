package org.zdevra.guice.mvc.case9;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

/**
 * This is integration test which testing behaviour in Jetty.
 * The test starts the jetty server under port 9191 and executes
 * several requests.
 * 
 */
public class Case9Test extends WebTest {

    //------------------------------------------------------------------------------------
    // setup
    //------------------------------------------------------------------------------------
    @Override
    protected void setupWebserver() {
        setPort(9191);
        addWebapp("src/test/java/org/zdevra/guice/mvc/case9/webapp", "/");
    }

    //------------------------------------------------------------------------------------
    // tests
    //------------------------------------------------------------------------------------
    @Test
    public void testPeople() throws InterruptedException, HttpException, IOException {
        HttpMethod req = new GetMethod("http://localhost:9191/case9/people");
        client.executeMethod(req);
        String out = req.getResponseBodyAsString();
        Assert.assertEquals("SUCCESS", out);
    }

    @Test
    public void testPeopleNew() throws InterruptedException, HttpException, IOException {
        HttpMethod req = new GetMethod("http://localhost:9191/case9/people/new");
        client.executeMethod(req);
        String out = req.getResponseBodyAsString();
        Assert.assertEquals("FORM", out);
    }

    @Test
    public void testPeopleNewSecond() throws InterruptedException, HttpException, IOException {
        HttpMethod req = new GetMethod("http://localhost:9191/case9/people/new/second");
        client.executeMethod(req);
        String out = req.getResponseBodyAsString();
        Assert.assertEquals("FORM 2", out);
    }

    @Test
    public void testGetPeople() throws InterruptedException, HttpException, IOException {
        HttpMethod req = new GetMethod("http://localhost:9191/case9/people/rest");
        client.executeMethod(req);
        String out = req.getResponseBodyAsString();
        Assert.assertEquals("FORM GET", out);
    }

    @Test
    public void testPostPeople() throws InterruptedException, HttpException, IOException {
        HttpMethod req = new PostMethod("http://localhost:9191/case9/people/rest");
        client.executeMethod(req);
        String out = req.getResponseBodyAsString();
        Assert.assertEquals("FORM POST", out);
    }
}
