/*****************************************************************************
 * Copyright 2012 Zdenko Vrabel
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
package org.zdevra.guice.mvc.views;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.ViewPoint;
import org.zdevra.guice.mvc.ViewScanner;
import org.zdevra.guice.mvc.annotations.RedirectView;

/**
 * Scanner is looking for {@link RedirectView} annotation and creates initialized
 * instance of the {@link RedirectViewPoint} for annotation.
 * 
 */
public class RedirectViewScanner implements ViewScanner {

    /**
     * {@inheritDoc}
     *
     * @param annotations - annotations of the controller or controller's method
     * @return
     */
    @Override
    public final ViewPoint scan(Annotation[] annotations) {
        RedirectView redirectAnnotation = Utils.getAnnotation(RedirectView.class, annotations);
        if (redirectAnnotation == null) {
            return ViewPoint.NULL_VIEW;
        }
        return new RedirectViewPoint(redirectAnnotation.value(), redirectAnnotation.contextRelative());
    }
}
