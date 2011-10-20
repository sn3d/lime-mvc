package org.zdevra.lime.examples.async;

public class Customer {

    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name  = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
