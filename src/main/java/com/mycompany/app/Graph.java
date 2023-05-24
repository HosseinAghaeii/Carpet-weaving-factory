package com.mycompany.app;

import java.util.*;
import java.util.LinkedList;

public class Graph {
        private int V; // No. of vertices
        private LinkedList<Integer> adj[]; //Adjacency List

        //Constructor
        Graph(int v) {
            V = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i)
                adj[i] = new LinkedList();
        }

        //Function to add an edge into the graph
        void addEdge(int v, int w) {
            adj[v].add(w);
            adj[w].add(v); //Graph is undirected
        }

        // Assigns colors (starting from 0) to all vertices and
        // prints the assignment of colors
        void greedyColoring() {
            int result[] = new int[V];

            // Initialize all vertices as unassigned
            Arrays.fill(result, -1);

            // Assign the first color to first vertex
            result[0] = 0;

            // A temporary array to store the available colors. False
            // value of available[cr] would mean that the color cr is
            // assigned to one of its adjacent vertices
            boolean available[] = new boolean[V];

            // Initially, all colors are available
            Arrays.fill(available, true);

            // Assign colors to remaining V-1 vertices
            for (int u = 1; u < V; u++) {
                // Process all adjacent vertices and flag their colors
                // as unavailable
                Iterator<Integer> it = adj[u].iterator();
                while (it.hasNext()) {
                    int i = it.next();
                    if (result[i] != -1)
                        available[result[i]] = false;
                }

                // Find the first available color
                int cr;
                for (cr = 0; cr < V; cr++) {
                    if (available[cr])
                        break;
                }

                result[u] = cr; // Assign the found color

                // Reset the values back to true for the next iteration
                Arrays.fill(available, true);
            }

            int min = -1 ;
            // print the result
            for (int u = 0; u < V; u++){
                if (min < result[u])
                    min = result[u];
                System.out.println("Vertex " + u + " ---> Color "
                        + result[u]);
            }

            System.out.println(min);
        }

        // Driver method
}
