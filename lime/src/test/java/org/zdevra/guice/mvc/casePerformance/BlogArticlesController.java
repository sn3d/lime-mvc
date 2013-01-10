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
package org.zdevra.guice.mvc.casePerformance;

import java.util.List;

import org.zdevra.guice.mvc.ModelMap;
import org.zdevra.guice.mvc.ModelAndView;
import org.zdevra.guice.mvc.annotations.UriParameter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BlogArticlesController implements IBlogArticlesController {

    private final BlogDao dao;

    @Inject
    public BlogArticlesController(BlogDao dao) {
        this.dao = dao;
    }

    public List<String> showAllArticles() {
        return dao.getAllArticles();
    }

    public ModelAndView showAllArticlesDirect() {
        ModelMap m = new ModelMap("allarticles", dao.getAllArticles());
        return new ModelAndView(m, new ViewAllArticles());
    }

    public String showArticle(@UriParameter(1) int id) {
        return dao.getArticle(id);
    }

    public ModelAndView showArticleDirect(@UriParameter(1) int id) {
        ModelMap m = new ModelMap("article", dao.getArticle(id));
        return new ModelAndView(m, new ViewArticle());
    }
}
