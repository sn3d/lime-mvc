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
package org.zdevra.guice.mvc.velocity;

import javax.servlet.ServletContext;

import org.apache.velocity.app.VelocityEngine;
import org.zdevra.guice.mvc.ViewModule;
import org.zdevra.guice.mvc.velocity.annotations.VelocityView;

/**
 * Install this module if you want to use the Velocity as a template engine. The
 * module register and bind Velocity engine instance into Guice.
 *
 * @see VelocityView
 * @see VelocityView
 */
public class VelocityModule extends ViewModule {

// ------------------------------------------------------------------------
    private final ServletContext context;

// ------------------------------------------------------------------------
    /**
     * Constructor where template files are loaded as resources from classpath.
     */
    public VelocityModule() {
        this.context = null;
    }

    /**
     * Constructor for MvcModule where template files are loaded from WAR as
     * regular JSP-s
     *
     * @param context
     */
    public VelocityModule(ServletContext context) {
        this.context = context;
    }

// ------------------------------------------------------------------------
    @Override
    protected final void configureViews() {
        VelocityEngine velocity = new VelocityEngine();
        configureVelocity(velocity);
        velocity.init();
        bind(VelocityEngine.class).toInstance(velocity);
        registerViewScanner(VelocityScanner.class);
    }

    /**
     * You will implement this method your velocity configuration
     */
    protected void configureVelocity(VelocityEngine velocity) {
        if (context != null) {
            velocity.addProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            velocity.addProperty("file.resource.loader.path", context.getRealPath("/"));
        } else {
            velocity.addProperty("resource.loader", "class");
            velocity.addProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        }
    }
// ------------------------------------------------------------------------	
}
