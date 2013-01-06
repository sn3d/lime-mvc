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
package org.zdevra.guice.mvc.case5;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.TestView;
import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.ViewPoint;
import org.zdevra.guice.mvc.ViewScanner;

public class TestViewScanner implements ViewScanner {

    @Override
    public ViewPoint scan(Annotation[] annotations) {
        ToTestView anot = Utils.getAnnotation(ToTestView.class, annotations);
        if (anot != null) {
            return new TestView("9");
        }
        return ViewPoint.NULL_VIEW;
    }
}
