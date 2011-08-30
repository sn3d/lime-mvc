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
import org.zdevra.guice.mvc.case7.TestCase7;

/**
 * This class exists for debugging and testing
 * purpose.
 *
 */
public class Main {

	public static void main(String[] args) {
		try {			
			TestCase7 t = new TestCase7();			
			t.testBasic();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
