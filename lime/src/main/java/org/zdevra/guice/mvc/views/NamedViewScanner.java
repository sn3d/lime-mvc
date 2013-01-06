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
package org.zdevra.guice.mvc.views;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.ViewPoint;
import org.zdevra.guice.mvc.ViewScanner;
import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.View;

import com.google.inject.Singleton;

/**
 * This implementation of the ViewScanner provides
 * controller's and method's scanning of an annotations 
 * for {@link NamedView}.
 * 
 * This scanner goes though method's and controller's 
 * annotations, looking for {@literal @}Controller and 
 * {@literal @}RequestMapping annotations and from
 * 'View' parameter extracts a name of view.
 *
 */
@Singleton
public class NamedViewScanner implements ViewScanner {

// ------------------------------------------------------------------------
    @Override
    public ViewPoint scan(Annotation[] annotations) {
        ViewPoint view = lookForView(annotations);
        if (view == ViewPoint.NULL_VIEW) {
            view = lookForController(annotations);
        }
        return view;
    }

// ------------------------------------------------------------------------
    /**
     * This method looks for {@link ViewPoint} annotation
     * and create {@link NamedView}
     * 
     * @param annotations
     * @return Named view if annotation is presented, otherwise View.NULL_VIEW.
     */
    private ViewPoint lookForView(Annotation[] annotations) {
        View anot = Utils.getAnnotation(View.class, annotations);
        if (anot != null) {
            return NamedView.create(anot.value());
        }
        return ViewPoint.NULL_VIEW;
    }

    /**
     * This method looks for {@literal @}Controller's view
     * 
     * @param annotations
     * @return
     * 
     * @see Controller
     */
    private ViewPoint lookForController(Annotation[] annotations) {
        Controller ant = Utils.getAnnotation(Controller.class, annotations);
        if (ant != null) {
            return NamedView.create(ant.view());
        }
        return ViewPoint.NULL_VIEW;
    }
}
