#Lime MVC

A Lime MVC is implementation of the MVC pattern for web applications based on Google's Guice. It's trying to be lightweight and simmilar to Spring MVC.

##Motivation
I’m a big fan of Spring’s dependency injection, but when I saw Google Guice for the first time, I said wow. It’s so lightweight and simple. There is no XML stress. I started thinking how I could use the Google Guice in my web projects. I guess that everybody agrees with the opinion that the MVC in Spring is very helpful for that kind of projects. Because most of my projects are web based applications, I want my favourite MVC pattern in Google Guice. Unfortunately, the MVC is missing in Guice. So I took the idea of MVC in Spring and created a Module Extension for Google Guice called Lime.

There are several extensions as well. These extensions implements diverse template engines as Velocity, Freemarker. I did these extensions as optional, because I have though that JSP-s are breaking the MVC pattern. Another advantage of template engines is that designers might focusing on web design without complicated Java code and developers might focusing on code. 

.... This is still a work in progress so I will appreciate any comments and feedback.

##Requirements
Minimal requirements are:

 * Java 1.6 
 * Guice 3.0 
 * Guice servlet extension 3.0 
 * Guice multibinding extension 3.0 

##Getting Started

For easy start You might use the Maven:

	<dependency>
		<groupId>org.zdevra</groupId>
		<artifactId>lime</artifactId>
		<version>0.3.1</version>
	</dependency>

Lime MVC is basically a Servlet Module in Guice. First you will prepare web project with
Guice Servlet. After that you'll code your Controller and a MvcModule implementation.

controller example

	@Controller
	public class MyController 
	{

		@Path("/helloworld/(.*)") 
		@Model("msg") 
		@View("main.jsp")
		public String doAction(@UriParam(1) String name) 
		{
			return "Hello World " + name + "!";
		}
	}

mvc module example

	public class MyModule extends MvcModule 
	{
		protected void configureControllers() 
		{
			control("/*").withController(MyController.class);
		}
	}


##Checking out and Building

First I recommend install Apache IVY because dependencies are managed 
using Ivy. To checkout the project and build from source, do the following:

	$ git clone git://github.com/sn3d/lime-mvc.git
	$ cd lime-mvc
	$ ant

##Troubleshooting

Have a question? Need a help? Ask on mailing list lime-mvc@googlegroups.com.

##Where to go from here?

For documentation, downloads, links, issue tracker, wiki and so on: [code.google.com](http://code.google.com/p/lime-mvc)

##Licencing

Copyright (C) 2011 Zdenko Vrabel
Licensed under the Apache License, Version 2.0
