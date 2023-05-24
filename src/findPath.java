import java.io.*;
import java.lang.*;
import java.util.*;

public class findPath {

    static final int MAXN = 100;

    // Infinite value for array
    static int INF = (int) 1e7;

    static int[][] dis = new int[MAXN][MAXN];
    static int[][] Next = new int[MAXN][MAXN];

    // Initializing the distance and
// Next array
    static void initialise(int V,
                           int[][] graph) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dis[i][j] = graph[i][j];

                // No edge between node
                // i and j
                if (graph[i][j] == INF)
                    Next[i][j] = -1;
                else
                    Next[i][j] = j;
            }
        }
    }

    // Function construct the shortest
// path between u and v
    static Vector<Integer> constructPath(int u,
                                         int v) {

        // If there's no path between
        // node u and v, simply return
        // an empty array
        if (Next[u][v] == -1)
            return null;

        // Storing the path in a vector
        Vector<Integer> path = new Vector<Integer>();
        path.add(u);

        while (u != v) {
            u = Next[u][v];
            path.add(u);
        }
        return path;
    }

    // Standard Floyd Warshall Algorithm
// with little modification Now if we find
// that dis[i][j] > dis[i][k] + dis[k][j]
// then we modify next[i][j] = next[i][k]
    static void floydWarshall(int V) {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {

                    // We cannot travel through
                    // edge that doesn't exist
                    if (dis[i][k] == INF ||
                            dis[k][j] == INF)
                        continue;

                    if (dis[i][j] > dis[i][k] +
                            dis[k][j]) {
                        dis[i][j] = dis[i][k] +
                                dis[k][j];
                        Next[i][j] = Next[i][k];
                    }
                }
            }
        }
    }

    // Print the shortest path
    static void printPath(Vector<Integer> path) {
        int n = path.size();
        for (int i = 0; i < n - 1; i++)
            System.out.print(path.get(i) + " -> ");
        System.out.print(path.get(n - 1) + "\n");
    }

}
