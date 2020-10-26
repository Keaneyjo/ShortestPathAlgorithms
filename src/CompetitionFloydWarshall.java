import java.io.File;
import java.io.FileNotFoundException;
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
 * This class implements the competition using Floyd-Warshall algorithm
 */

/**
 *  Test class for SortComparison.java
 *
 *  @author: John Keaney (keaneyjo@tcd.ie)
 *  Student ID: 18328855
 *  @version HT 2020
 */

public class CompetitionFloydWarshall {

    private int speedA;
    private int speedB;
    private int speedC;
    double[][] dist;

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     */
    CompetitionFloydWarshall (String filename, int sA, int sB, int sC)
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
            dist = new double[v][v];
            //set the array values to as large as possible
            for (int i = 0; i < dist.length; i++)
            {
                for (int j = 0; j < dist[i].length; j++)
                {
                    dist[i][j] = Double.MAX_VALUE;
                }
            }

            //for each edge (u, v) dist[u][v] = length of u -> v
            while(sc.hasNextLine() && sc.hasNextInt())
            {
                dist[sc.nextInt()][sc.nextInt()] = sc.nextDouble();
            }
            //for each vertex i dist[i][i] = 0
            for (int i = 0; i < v; i++)
            {
                dist[i][i] = 0;
            }
            for(int k = 0; k < v; k++)
            {
                for(int i = 0; i < v; i++)
                {
                    for(int j = 0; j < v; j++)
                    {
                        if(dist[i][j] > dist[i][k] + dist[k][j] || dist[i][j] < 0)
                            dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
            for (double d : da)
            {
                if(d == Double.MAX_VALUE)
                    return -1;
                if(d > longestPath)
                {
                    longestPath = d;
                }
            }
        }
        int minSpeed = (Math.min(speedA, Math.min(speedB, speedC)));
        if(minSpeed < 0)
            return -1;
        return (int) Math.ceil((longestPath*1000)/minSpeed);

    }
}