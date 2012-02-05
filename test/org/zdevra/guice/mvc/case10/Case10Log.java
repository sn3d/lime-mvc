package org.zdevra.guice.mvc.case10;

import java.util.LinkedList;
import java.util.List;

public class Case10Log {
	
	private static Case10Log instance;	
	private List<String> messages;
		
	public static Case10Log getInstance() {
		if (instance == null) {
			instance = new Case10Log();
			instance.reset();
		}
		return instance;
	}
	
	
	public void reset() {
		this.messages = new LinkedList<String>();
	}
	
	
	public void log(String msg)
	{
		this.messages.add(msg);
	}
	
	
	public List<String> getMessages() 
	{ 
		return messages;
	}
	
	
	public int contains(String text) 
	{
		int occurence = 0;
		for (String m : messages) {
			if (m.contains(text)) {
				occurence++;
			}
		}		
		return occurence;
	}

}
