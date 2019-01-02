package javaordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PersonRunner {
    public static void main(String[] args) {
        List<Person> persons =  new ArrayList<>();
        Person p1 = new Person(18,"anubhav");
        Person p2 = new Person(16,"person");
        persons.add(p1);
        persons.add(p2);

        // comprator should be used in case we have third party library
        Comparator<Person> personComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if(o1.age==o2.age){
                    return 0;
                }
                else  if(o1.age>o2.age){
                    int i = 1;
                    return i;
                }
                return -1;
            }
        };
        Collections.sort(persons,personComparator);

        for(Person person:persons){
            System.out.println(person);
        }
    }
}

class Person{
    int age;
    String name;

    Person(int age,String name){
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "Name: '" + this.name +  "', Age: '" + this.age + " ";
    }

   /* @Override
    public int compareTo(Person o) {
        if(this.age==o.age){
            return 0;
        }
        else if(this.age>o.age){
            return 1;
        }
        else{
            return -1;
        }
    }*/

}