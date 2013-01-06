/**
 * ***************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 ****************************************************************************
 */
package org.zdevra.guice.mvc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TestRequest implements HttpServletRequest {

    private Map<String, Object> attributes;
    private Map<String, String> parameters;
    private Map<String, String[]> headerItems;
    private List<Cookie> cookies;
    private String characterEncoding;
    private String protocol;
    private boolean secure;
    private String authType;
    private String method;
    private InputStream content;
    private InnerInputStream contentServlet;
    private int contentSize;
    private String contentType;
    private String scheme;
    private String serverName;
    private int serverPort;
    private String contextPath;
    private String servletPath;
    private String infoPath;
    private String query;
    private String remoteAddr;
    private String remoteHost;
    private int remotePort;
    private String localAddr;
    private String localHost;
    private int localPort;

    public TestRequest() {
        this.attributes = new HashMap<String, Object>();
        this.parameters = new HashMap<String, String>();
        this.headerItems = new HashMap<String, String[]>();
        this.cookies = new LinkedList<Cookie>();

        this.characterEncoding = "UTF8";
        this.protocol = "HTTP/1.1";
        this.secure = false;
        this.authType = "";
        this.method = "GET";

        this.content = new ByteArrayInputStream(new byte[]{});
        this.contentSize = 0;
        this.contentType = "text/html";
        this.contentServlet = new InnerInputStream();


        this.scheme = "http";
        this.serverName = "localhost";
        this.serverPort = 80;
        this.contextPath = "";
        this.servletPath = "";
        this.infoPath = "";
        this.query = "";

        this.remoteAddr = "";
        this.remoteHost = "";
        this.remotePort = 0;

        this.localAddr = "127.0.0.1";
        this.localHost = "localhost";
        this.localPort = 80;

    }

    public TestRequest(TestRequest copy) {
        this.parameters = copy.parameters;
        this.attributes = copy.attributes;
        this.headerItems = copy.headerItems;
        this.cookies = copy.cookies;

        this.characterEncoding = copy.characterEncoding;
        this.protocol = copy.protocol;
        this.secure = copy.secure;
        this.authType = copy.authType;
        this.method = copy.method;

        this.content = copy.content;
        this.contentSize = copy.contentSize;
        this.contentType = copy.contentType;

        this.scheme = copy.scheme;
        this.serverName = copy.serverName;
        this.serverPort = copy.serverPort;
        this.contextPath = copy.contextPath;
        this.servletPath = copy.servletPath;
        this.infoPath = copy.infoPath;
        this.query = copy.query;

        this.remoteAddr = copy.remoteAddr;
        this.remoteHost = copy.remoteHost;
        this.remotePort = copy.remotePort;

        this.localAddr = copy.localAddr;
        this.localHost = copy.localHost;
        this.localPort = copy.localPort;
    }

    class InnerInputStream extends ServletInputStream {

        @Override
        public int read() throws IOException {
            return content.read();
        }
    };

    public static class Builder {

        private final TestRequest req = new TestRequest();

        public Builder setParameter(String name, String value) {
            req.parameters.put(name, value);
            return this;
        }

        public Builder setHeaderItm(String name, String value) {
            req.headerItems.put(name, new String[]{value});
            return this;
        }

        public Builder setHeaderItm(String name, String[] values) {
            req.headerItems.put(name, values);
            return this;
        }

        public Builder setCookie(Cookie cookie) {
            req.cookies.add(cookie);
            return this;
        }

        public Builder setAttribute(String name, Object value) {
            req.attributes.put(name, value);
            return this;
        }

        public Builder setCharacterEncoding(String encoding) {
            req.characterEncoding = encoding;
            return this;
        }

        public Builder setContent(String content) {
            byte[] buf = content.getBytes();
            req.contentSize = buf.length;
            req.content = new ByteArrayInputStream(buf);
            return this;
        }

        public Builder setContentType(String contentType) {
            req.contentType = contentType;
            return this;
        }

        public Builder setUrl(String scheme, String serverName, int port, String contextPath, String servletPath, String infoPath, String query) {
            req.scheme = scheme;
            req.serverName = serverName;
            req.serverPort = port;
            req.contextPath = contextPath;
            req.servletPath = servletPath;
            req.infoPath = infoPath;
            req.query = query;
            return this;
        }

        public Builder setRemote(String addr, String host, int port) {
            req.remoteAddr = addr;
            req.remoteHost = host;
            req.remotePort = port;
            return this;
        }

        public Builder setLocal(String addr, String host, int port) {
            req.localAddr = addr;
            req.localHost = host;
            req.localPort = port;
            return this;
        }

        public Builder setMethod(String method) {
            req.method = method;
            return this;
        }

        public HttpServletRequest build() {
            return new TestRequest(req);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override
    public int getContentLength() {
        return this.contentSize;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.contentServlet;
    }

    @Override
    public String getLocalAddr() {
        return this.localAddr;
    }

    @Override
    public String getLocalName() {
        return this.localHost;
    }

    @Override
    public int getLocalPort() {
        return this.localPort;
    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Enumeration getLocales() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    @Override
    public Map getParameterMap() {
        return this.parameters;
    }

    @Override
    public Enumeration getParameterNames() {
        return Collections.enumeration(this.parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String arg0) {
        String[] out = this.parameters.values().toArray(new String[]{});
        return out;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(content));
    }

    @Override
    public String getRealPath(String arg0) {
        throw new IllegalStateException("getRealPath() is deprecated");
    }

    @Override
    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    @Override
    public String getRemoteHost() {
        return this.remoteHost;
    }

    @Override
    public int getRemotePort() {
        return this.remotePort;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }

    @Override
    public int getServerPort() {
        return serverPort;
    }

    @Override
    public boolean isSecure() {
        return this.secure;
    }

    @Override
    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    @Override
    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    @Override
    public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
        this.characterEncoding = encoding;
    }

    @Override
    public String getAuthType() {
        return this.authType;
    }

    @Override
    public String getContextPath() {
        return this.contextPath;
    }

    @Override
    public Cookie[] getCookies() {
        return cookies.toArray(new Cookie[]{});
    }

    @Override
    public long getDateHeader(String name) {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return this.headerItems.get(name)[0];
    }

    @Override
    public Enumeration getHeaderNames() {
        return Collections.enumeration(this.headerItems.keySet());
    }

    @Override
    public Enumeration getHeaders(String name) {
        String[] vals = this.headerItems.get(name);
        Collection<String> x = Arrays.asList(vals);
        return Collections.enumeration(x);
    }

    @Override
    public int getIntHeader(String name) {
        return Integer.parseInt(this.headerItems.get(name)[0]);
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getPathInfo() {
        return this.infoPath;
    }

    @Override
    public String getPathTranslated() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getQueryString() {
        return this.query;
    }

    @Override
    public String getRemoteUser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRequestURI() {
        return this.servletPath + "/" + this.infoPath;
    }

    @Override
    public StringBuffer getRequestURL() {
        return new StringBuffer(
                this.scheme + "://"
                + this.serverName + ":"
                + this.serverPort + "/"
                + this.contextPath + "/"
                + this.servletPath + "/"
                + this.infoPath + "?"
                + this.query);
    }

    @Override
    public String getRequestedSessionId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getServletPath() {
        return this.servletPath;
    }

    @Override
    public HttpSession getSession() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HttpSession getSession(boolean arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Principal getUserPrincipal() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isUserInRole(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }
}
