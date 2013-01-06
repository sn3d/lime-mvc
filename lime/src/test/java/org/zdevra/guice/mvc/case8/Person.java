package org.zdevra.guice.mvc.case8;

public class Person {

    private final String name;
    private final String surname;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "Person{"
                + "name='" + name + '\''
                + ", surname='" + surname + '\''
                + '}';
    }
}
