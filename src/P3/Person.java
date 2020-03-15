package P3;

import java.util.HashSet;
import java.util.Set;

public class Person {
    String name;
    Set<Person> inEdge= new HashSet<>();
    Set<Person> outEdge= new HashSet<>();

    public Person(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addInEdge(Person person){
        inEdge.add(person);
    }
    public void addOutEdge(Person person){
        outEdge.add(person);
    }
}
