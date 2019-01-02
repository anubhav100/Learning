package javaordering;

import java.util.Objects;

public class JavaEqualsRunner {
    public static void main(String[] args) {

        /**
         * Equals method in java check for the values and == check for object references
         */
        /*System.out.println(5==5);
        Integer i1 =5;
        Integer i2 =5;
        System.out.println(i1.equals(i2));
        System.out.println(i1==(i2));
        Integer i3 =15;
        Integer i4 =1;
        System.out.println(i3.equals(i4));
        System.out.println(i3==(i4));
        Integer i5 = i4;
        System.out.println(i5==(i4));
        System.out.println(i5.equals(i4))*/

        User user1 = new User(18, "Anubhav");
        User user2 = new User(18, "Anubhav");
        System.out.println(user1.equals(user2));
    }
}

/**
 * default implementation of equals method is == for UDT
 */
class User {
    int age;
    String name;

    User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;
        return this.age == user.age && this.name == user.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }
}