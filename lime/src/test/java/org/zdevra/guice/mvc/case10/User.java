package org.zdevra.guice.mvc.case10;

public class User {
	
	private final String username;
	private final String password;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	

	public String getUsername() {		
		return username;
	}
	
	
	public String getPassword() {
		return password;
	}
	
}
