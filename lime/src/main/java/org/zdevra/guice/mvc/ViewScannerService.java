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

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This service provide scanning of controller class or controller's method,
 * where is looking for view annotations, then constructs and returns a view
 * instance for concrete controller's class or method.
 *
 * This service contains various scanners for various views. If you want create
 * your own view annotation, you will create own scanner as a implementation of
 * {@link ViewScanner} interface and then you will register it in
 * {@link MvcModule}.
 *
 * <p>How to register custom scanner into service <pre class="prettyprint">
 * class CustomModule extends MvcModule { protected void configureControllers()
 * { ... registerViewScanner(Custom1ViewScanner.class); registerViewScanner(new
 * Custom2ViewScanner()); ... } }
 * </pre>
 *
 */
@Singleton
class ViewScannerService {

    private Collection<ViewScanner> scanners;

    @Inject
    public ViewScannerService(Set<ViewScanner> scanners) {
        this.scanners = Collections.unmodifiableCollection(scanners);
    }

    public ViewPoint scan(Annotation[] controllerAnnotations) {
        for (ViewScanner scanner : scanners) {
            ViewPoint view = scanner.scan(controllerAnnotations);
            if (view != null && view != ViewPoint.NULL_VIEW) {
                return view;
            }
        }
        return ViewPoint.NULL_VIEW;
    }
}
