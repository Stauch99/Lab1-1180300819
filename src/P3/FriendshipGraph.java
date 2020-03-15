package P3;

import java.util.*;

public class FriendshipGraph {
    private Set<Person> space = new HashSet<>();
    private int distance;
    private Set<Person> visited = new HashSet<>();

    /*
    Well..this is simply an addVertex function,
    it adds **person** into the graph space
    (I preferred space than graph caz it sounds cool)
     */

    void addVertex(Person person) {
        for (Person p : space) {
            if (p.getName().equals(person.getName())) {
                System.out.println("Same name already exist! Program will quit...\n");
                System.exit(0);
            }
        }
        space.add(person);
    }

    /*
    Well..this is simply an addEdge function,
    it adds **directed edges** between the two person
     */

    void addEdge(Person person1, Person person2) {
        person1.addOutEdge(person2);
        person2.addInEdge(person1);
    }

    /*
    This is the mother method of the DFS function to
    do some initializing work and ending stuff
     */

    int getDistance(Person person1, Person person2) {
        visited.clear();
        visited.add(person1);
        distance = 0;
        BFS(person1,person2);
        return distance;
    }

    /*
    using broad first search method to
    find the shortest path between two people
     */

    void BFS(Person source, Person target) {
        if(source.equals(target)){
            return;
        }
        distance+=1;
        for (Person person : source.outEdge) {
            if (person.equals(target)){
                return;
            }
            if ((visited.contains(person))||(!source.inEdge.contains(person))){
                continue;
            }
            visited.add(person);
            BFS(person,target);
            return;
        }
        distance = -1;
    }
}
