import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 *  Test class for SortComparison.java
 *
 *  @author: John Keaney (keaneyjo@tcd.ie)
 *  Student ID: 18328855
 *  @version HT 2020
 */



/*
            There wasn't really an exact question on what to discuss here so I'm just going to compare Dijkstra against Floyd-Warshall
            for this problem.

            With time complexity it's clear that in general Dijkstra is the winner as there respective time complexities are:
            -> Time Complexity of Dijkstra’s Algorithm: O(E log V)
            -> Time Complexity of Floyd Warshall: O(V3)

            So then why use Floyd-Warshall over Dijkstra's. Because Floyd-Warhsall's algorithm is used when any of the
            nodes can be the source so you need to find the shortest path from all nodes to every other node.
            Floyd-Warshall's shortest path algorithm is quite similar to Dijkstra's however it allows for negative
            weighted edges whereas Dijkstra's does not. With Dijstra's on the face it seems as if the time complexity would
            be 0(V^2) however we can use different data-structures to greatly shorten that.

            For this problem Dijkstra's shortest path algorithm is the less ideal of the
            two algorithms to use in theory as it is designed for finding the shortest path from one source node.

            Whereas by using Floyd-Warshall, you compare all possible paths through the graph between each
            pair of vertices. This is much more ideal for the current problem at hand where we need to know
            the longest path from any two vertices in order to compute the minimum time needed for the competition
            to run. However, the Floyd-Warshall algorithm runs in O(V^3).
 */


@RunWith(JUnit4.class)
public class CompetitionTests {

    @Test
    public void testDijkstraConstructor() {
        //String name = "8\n" +
//                "15\n" +
//                "4 5 0.35\n" +
//                "5 4 0.35\n" +
//                "4 7 0.37\n" +
//                "5 7 0.28\n" +
//                "7 5 0.28\n" +
//                "5 1 0.32\n" +
//                "0 4 0.38\n" +
//                "0 2 0.26\n" +
//                "7 3 0.39\n" +
//                "1 3 0.29\n" +
//                "2 7 0.34\n" +
//                "6 2 0.40\n" +
//                "3 6 0.52\n" +
//                "6 0 0.58\n" +
//                "6 4 0.93";

        //Assert.assertEquals("Number of Streets should be 28", graph.noOfStreets, 8);
        //Assert.assertEquals("Number of Points should be 9", graph.noOfVectors, 15);
        //If code works correctly then my graph should be the same no matter the order of string;
        //String result = "[{0={0=0.0, 1=4.0, 7=8.0}, 1={0=4.0, 1=0.0, 2=8.0, 7=11.0}, 2={1=8.0, 2=0.0, 3=7.0, 5=4.0, 8=2.0}, 3={2=7.0, 3=0.0, 4=9.0, 5=14.0}, 4={3=9.0, 4=0.0, 5=10.0}, 5={2=4.0, 3=14.0, 4=10.0, 5=0.0, 6=2.0}, 6={5=2.0, 6=0.0, 7=1.0, 8=6.0}, 7={0=8.0, 1=11.0, 6=1.0, 7=0.0, 8=7.0}, 8={2=2.0, 6=6.0, 7=7.0, 8=0.0}}]";
        //Assert.assertEquals("Check whether graph mapped correctly", graph.graphString, result);
        CompetitionDijkstra graph = new CompetitionDijkstra("tinyEWD.txt", 50, 75, 100);
        Assert.assertEquals("Check whether graph mapped correctly", 38, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("tinyEWD.txt", 55, 75, 100);
        //System.out.println(graph.timeRequiredforCompetition());
        Assert.assertEquals("Check whether graph mapped correctly", 34, graph.timeRequiredforCompetition());

        //[{0={2=0.26, 4=0.38}, 1={3=0.29}, 2={7=0.34}, 3={6=0.52}, 4={5=0.35, 7=0.37}, 5={1=0.32, 4=0.35, 7=0.28}, 6={0=0.58, 2=0.4, 4=0.93}, 7={3=0.39, 5=0.28}}]
        //System.out.println(Collections.singletonList(graph));
    }



    @Test
    public void testFWConstructor() {
        CompetitionFloydWarshall graph2 = new CompetitionFloydWarshall("1000EWD.txt", 50, 75, 100);
        Assert.assertEquals("Check whether graph mapped correctly", 28, graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall("./tinyEWD.txt", -55, -75, -100);
        Assert.assertEquals("should return -1 if speed are -ve", -1, graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall("./tinyEWD.txt", 0, 0, 0);
        Assert.assertEquals("should return -1 if speed are -ve", -1, graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall("./tinyEWD.txt", 55, 75, 101);
        Assert.assertEquals("should return -1 if speed is over 100", -1, graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall("./input-I.txt", 72,70,65);
        Assert.assertEquals("should return 185 if speed is [60, 50, 75]", 185, graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall("./input-I.txt", 60,70,84);
        Assert.assertEquals("should return 200 if speed is [60, 50, 75]", 200, graph2.timeRequiredforCompetition());
    }

    @Test
    public void testRunningTimes() {
        CompetitionFloydWarshall graph2 = new CompetitionFloydWarshall("./1000EWD.txt", 50, 75, 100);
        //System.out.println(graph2.timeRequiredforCompetition(graph2.graph));
        Assert.assertEquals("Check whether graph mapped correctly", 28, graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall(null, 55, 75, 100);
        Assert.assertEquals("should return -1 if file is null", -1,  graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall(null, -55, 75, 100);
        Assert.assertEquals("should return -1 if file is null", -1,  graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall(null, 55, -75, 100);
        Assert.assertEquals("should return -1 if file is null", -1,  graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall(null, 55, -75, 100);
        Assert.assertEquals("should return -1 if file is null", -1,  graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall(null, 155, 75, 100);
        Assert.assertEquals("should return -1 if file is null", -1,  graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall(null, 55, 175, 100);
        Assert.assertEquals("should return -1 if file is null", -1,  graph2.timeRequiredforCompetition());
        graph2 = new CompetitionFloydWarshall(null, 55, 75, 1100);
        Assert.assertEquals("should return -1 if file is null", -1,  graph2.timeRequiredforCompetition());



        CompetitionDijkstra graph = new CompetitionDijkstra("./tinyEWD.txt", 50, 75, 100);
        //System.out.println(graph.timeRequiredforCompetition());
        Assert.assertEquals("Check whether graph mapped correctly", 38, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("./tinyEWD.txt", 55, 75, 100);
        //System.out.println(graph.timeRequiredforCompetition());
        Assert.assertEquals("Check whether graph mapped correctly", 34, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra(null, 55, 75, 100);
        Assert.assertEquals("should return -1 if file is null", -1, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("./tinyEWD.txt", -55, -75, -100);
        Assert.assertEquals("should return -1 if speed are -ve", -1, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("./tinyEWD.txt", 55, -75, -100);
        Assert.assertEquals("should return -1 if speed are -ve", -1, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("./tinyEWD.txt", 55, 75, -100);
        Assert.assertEquals("should return -1 if speed are -ve", -1, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("./tinyEWD.txt", 155, 75, 100);
        Assert.assertEquals("should return -1 if speed are -ve", -1, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("./tinyEWD.txt", 55, 175, 100);
        Assert.assertEquals("should return -1 if speed are -ve", -1, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("./tinyEWD.txt", 55, 75, 101);
        Assert.assertEquals("should return -1 if speed are -ve", -1, graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("./tinyEWD.txt", 0, 0, 0);
        Assert.assertEquals("should return -1 if speed are -ve", -1,  graph.timeRequiredforCompetition());
        graph = new CompetitionDijkstra("./tinyEWD.txt", 55, 75, 101);
        Assert.assertEquals("should return -1 if speed is over 100", -1, graph.timeRequiredforCompetition());
    }

//    @Test
//    public void testFWNumberOfVertices() {
//        CompetitionFloydWarshall graph2 = new CompetitionFloydWarshall("./1000EWD.txt", 50, 75, 100);
//        Assert.assertEquals(graph2.numberOfVertices(null), 0);
//    }

}