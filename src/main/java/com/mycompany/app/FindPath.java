package com.mycompany.app;

public class FindPath {

  static final int MAXN = 100;

  static int INF = (int) 1e7;

  static int[][] dis = new int[MAXN][MAXN];
  static int[][] Next = new int[MAXN][MAXN];

  FindPath(int V,
      int[][] graph) {
    for (int i = 0; i < V; i++) {
      for (int j = 0; j < V; j++) {
        dis[i][j] = graph[i][j];

        if (graph[i][j] == INF)
          Next[i][j] = -1;
        else
          Next[i][j] = j;
      }
    }
    floyd(V);
  }

  private void floyd(int V) {
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

  void printPath(int u,
      int v) {
    if (Next[u][v] == -1)
      return;

    System.out.print(u + " -> ");

    while (u != v) {
      u = Next[u][v];
      System.out.print(u + " -> ");
    }
    System.out.println("END");
  }

}
