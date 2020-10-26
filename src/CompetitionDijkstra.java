import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestants’
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *     Each contestant walks at a given estimated speed.
 *     The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Dijkstra's algorithm
 */


/**
 *  Test class for SortComparison.java
 *
 *  @author: John Keaney (keaneyjo@tcd.ie)
 *  Student ID: 18328855
 *  @version HT 2020
 */

public class CompetitionDijkstra {

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     */


    int speedA, speedB, speedC;
    private double[][] graph;
    private double[][] dist;
    private int[][] prev;

    CompetitionDijkstra (String filename, int sA, int sB, int sC)
    {
        if(filename == null)
            return;
        this.speedA = sA;
        this.speedB = sB;
        this.speedC = sC;
        File testFile= new File(filename);
        Scanner sc;
        try {
            sc = new Scanner(testFile);



            int v = sc.nextInt();
            int numEdges = sc.nextInt();
            graph = new double [v][v];
            for (int i = 0; i < graph.length; i++)
            {
                for (int j = 0; j < graph[i].length; j++)
                {
                    graph[i][j] = -1;
                }
            }

            while(sc.hasNextLine() && sc.hasNextInt())
            {
                graph[sc.nextInt()][sc.nextInt()] = sc.nextDouble();
            }
            sc.close();
            dist = new double [v][v];
            prev = new int [v][v];



            for (int source = 0; source < v ; source++)
            {
                if(Dijkstra(source, graph, dist[source], prev[source])<0)
                    return;

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private int Dijkstra(int source, double[][] graph, double[] dist, int[] prev)
    {
        ArrayList<Integer> queue = new ArrayList<Integer>();

        for (int j = 0; j < dist.length; j++)
        {
            dist[j] = Double.MAX_VALUE;
            prev[j] = -1;
            queue.add(j);
        }
        dist[source] = 0;
        while(!queue.isEmpty())
        {
            int u = minOf(queue, dist);
            if(u < 0)
            {
                for(int i = 0; i < dist.length; i++)
                    dist[i] = -1;
                return -1;
            }
            int pos = queue.indexOf(u);
            if(pos >=0 && pos < queue.size())
                queue.remove(pos);

            for (int x = 0; x < dist.length ; x++)
            {
                double lengthUX = graph[u][x];
                if(lengthUX >= 0) // if u is a neighbour of x
                {
                    double alt = dist[u] + lengthUX;
                    if(alt < dist[x])
                    {
                        dist[x] = alt;
                        prev[x] = u;
                    }
                }
            }
        }
        return 0;
    }


    private int minOf(ArrayList<Integer> queue, double[] lengthTo) {
        double minDist = Double.MAX_VALUE;
        int nearest = -1;
        for(int i : queue)
        {
            double distanceTo = lengthTo[i];
            if(distanceTo >=0 && distanceTo < minDist)
            {
                nearest = i;
                minDist = distanceTo;
            }
        }
        return nearest;
    }


    /**
     * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforCompetition()
    {
        if((this.speedA < 50||this.speedB < 50||this.speedC < 50) || (this.speedA > 100||this.speedB > 100||this.speedC > 100) )
        {
            return -1;
        }
        double longestPath = Double.MIN_VALUE;
        if (dist == null || dist.length == 0)
        {
            return -1;
        }
        for(double[] da: dist)
        {
            if (da == null)
            {
                return -1;
            }
            for (double d : da)
            {
                if(d < 0)
                    return -1;
                if(d > longestPath)
                {
                    longestPath = d;
                }
            }
        }
        int minSpeed = (Math.min(speedA, Math.min(speedB, speedC)));
        if(minSpeed < 0 || longestPath == Double.MIN_VALUE)
            return -1;
        return (int) Math.ceil((longestPath*1000)/minSpeed);

    }


}