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

import org.zdevra.guice.mvc.ModelAndView;
import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.RequestMapping;
import org.zdevra.guice.mvc.annotations.View;
import org.zdevra.guice.mvc.annotations.UriParameter;

@Controller
public interface IBlogArticlesController {
	
	@RequestMapping(path="/blog/allarticles/namedview", nameOfResult="allarticles")
	@View("allarticles.jsp")
	public List<String> showAllArticles();

	
	@RequestMapping(path="/blog/allarticles/directview")
	public ModelAndView showAllArticlesDirect();

	
	@RequestMapping(path="/blog/article/(\\d+)/namedview", nameOfResult="article")
	@View("article.jsp")
	public String showArticle(@UriParameter(1) int id);


	@RequestMapping(path="/blog/article/(\\d+)/directview")
	public ModelAndView showArticleDirect(@UriParameter(1) int id);
}
