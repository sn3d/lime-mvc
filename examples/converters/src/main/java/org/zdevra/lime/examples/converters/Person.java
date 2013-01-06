package org.zdevra.lime.examples.converters;

/**
 * Created by IntelliJ IDEA.
 * User: sn3d
 * Date: 9/8/11
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Person {

    private final String name;
    private final String surname;
    private final String email;
    private final int age;

    public Person(String name, String surname, String email, int age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
