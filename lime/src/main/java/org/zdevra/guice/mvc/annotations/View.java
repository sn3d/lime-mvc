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
package org.zdevra.guice.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.zdevra.guice.mvc.views.NamedView;
import org.zdevra.guice.mvc.views.NamedViewScanner;

/**
 * Annotation for controller and controller's methods provide information that
 * the {@link NamedView} should be used for controller or method.
 *
 * This annotation extracted 'view' parameter from {@link Controller} and
 * {@link RequestMapping}.
 *
 * The annotation is processed by {@link NamedViewScanner}
 *
 * @see NamedView
 * @see NamedViewScanner
 * @see ViewScannerService
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface View {

    public String value();
}
