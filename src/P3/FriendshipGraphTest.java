package P3;

import org.junit.Test;

import static org.junit.Assert.*;

public class FriendshipGraphTest {

    public static void main(String[] args) {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));
        System.out.println(graph.getDistance(rachel, ben));
        System.out.println(graph.getDistance(rachel, rachel));
        System.out.println(graph.getDistance(rachel, kramer));
    }

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void graphTest() {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);

//      Tests below are same for these sout uses
//    System.out.println(graph.getDistance(rachel, ross));    should print 1
//    System.out.println(graph.getDistance(rachel, ben));     should print 2
//    System.out.println(graph.getDistance(rachel, rachel));  should print 0
//    System.out.println(graph.getDistance(rachel, kramer));  should print -1

        assertEquals(1, graph.getDistance(rachel, ross));
        assertEquals(2, graph.getDistance(rachel, ben));
        assertEquals(0, graph.getDistance(rachel, rachel));
        assertEquals(-1, graph.getDistance(rachel, kramer));

        //now I'll write more samples to test my work
        Person stauch = new Person("Stauch"); //this is my english name
        Person snow = new Person("Snow");
        graph.addVertex(stauch);
        graph.addVertex(snow);
        graph.addEdge(stauch, snow);
        graph.addEdge(snow, stauch);
        graph.addEdge(ross, stauch);
        graph.addEdge(stauch, ross);
        assertEquals(3, graph.getDistance(rachel, snow)); //pretty good!

        //now let's test what will happen if we have a one-direction relationship
        //it should be unable to access (-1 distance)
        graph.addEdge(kramer, ross);
        assertEquals(-1, graph.getDistance(rachel, kramer)); //pass!
    }
}
