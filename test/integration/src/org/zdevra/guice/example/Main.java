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
package org.zdevra.guice.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


public class Main {

// ------------------------------------------------------------------------
	
	private Server server;	
			
// ------------------------------------------------------------------------	
		
	@BeforeTest()
	public void startServer() throws Exception {
		
		//start jetty server
		try {
			server = new Server(7374);
			WebAppContext wac = new WebAppContext();
			wac.setContextPath("/test");
			wac.setWar("./test/webapp");
			server.setHandler(wac);
			server.start();
		} catch (Exception e) {
			System.err.println("ERROR:" + e.getMessage() );
		}
	}
		
	@AfterTest
	public void endServlet() throws Exception {		
		server.stop();		
	}

// ------------------------------------------------------------------------


	public static void main(String[] args) throws Exception {
		
		try {
			Main main = new Main();
			main.startServer();
			Thread.sleep(100000);
			main.endServlet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
