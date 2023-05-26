package com.mycompany.app;

import java.util.*;
import java.util.LinkedList;

public class Graph {
        private int V; // No. of vertices
        private LinkedList<Integer> adj[]; //Adjacency List

 
        Graph(int v) {
            V = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i)
                adj[i] = new LinkedList();
        }


        void addEdge(int v, int w) {
            adj[v].add(w);
            adj[w].add(v); 
        }


        void coloring() {
            int result[] = new int[V];


            Arrays.fill(result, -1);

            result[0] = 0;


            boolean available[] = new boolean[V];

           
            Arrays.fill(available, true);

        
            for (int u = 1; u < V; u++) {
               
                Iterator<Integer> it = adj[u].iterator();
                while (it.hasNext()) {
                    int i = it.next();
                    if (result[i] != -1)
                        available[result[i]] = false;
                }

               
                int cr;
                for (cr = 0; cr < V; cr++) {
                    if (available[cr])
                        break;
                }

                result[u] = cr; // Assign the found color

                
                Arrays.fill(available, true);
            }

            int min = -1 ;
          
            for (int u = 0; u < V; u++){
                if (min < result[u])
                    min = result[u];
                System.out.println("Vertex " + u + " ---> Color "
                        + result[u]);
            }

            System.out.println(min);
        }

        
}
