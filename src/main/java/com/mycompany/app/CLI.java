package com.mycompany.app;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

import com.mycompany.app.Models.Carpet;

public class CLI {
  public static void getGraph() {

    System.out.println("enter n graph size:");
    Scanner input = new Scanner(System.in);
    Integer size = input.nextInt();
    Graph g1 = new Graph(size);

    input.nextLine();
    do {
      System.out.println("enter edge: X Y (enter space between numbers. X & Y are the vertex index," +
          "enter calc for calculation)");
      if (input.hasNextInt()) {
        g1.addEdge(input.nextInt(), input.nextInt());
      } else
        break;
    } while (input.hasNextLine());

    g1.greedyColoring();

  }

  public static void getPath() {
    findPath findPath = new findPath();
    System.out.println("enter street size:");
    Scanner input = new Scanner(System.in);
    Integer size = input.nextInt();

    int[][] graph = new int[size][4];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < 4; j++) {
        graph[i][j] = input.nextInt();
      }
    }

    findPath.initialise(size, graph);

    findPath.floydWarshall(size);
    Vector<Integer> path;

    // Path from node 1 to 3
    System.out.print("Shortest path from 1 to 3: ");
    path = findPath.constructPath(1, 3);
    findPath.printPath(path);

    // Path from node 0 to 2
    System.out.print("Shortest path from 0 to 2: ");
    path = findPath.constructPath(0, 2);
    findPath.printPath(path);

    input.nextLine();
    input.close();
  }

  public static void getSimilerCarpets() {
    ArrayList<Carpet> carpets = new ArrayList<>();
    int[][] map1 = { { 1, 3, 5, 6 }, { 1, 3, 5, 7 } };
    int[][] map2 = { { 1, 5, 5, 10 }, { 1, 4, 5, 7 } };
    int[][] map3 = { { 6, 5, 2, 100 }, { 9, 2, 9, 1 } };
    int[][] map4 = { { 15, 1, 4, 180 }, { 15, 24, 58, 79 } };

    carpets.add(new Carpet("map 1", 100, map1));
    carpets.add(new Carpet("map 2", 100, map2));
    carpets.add(new Carpet("map 3", 100, map3));
    carpets.add(new Carpet("map 4", 100, map4));

    final Carpet input = new Carpet("input", 1000, map1);

    Collections.sort(carpets, new Comparator<Carpet>() {
      @Override
      public int compare(final Carpet lhs, Carpet rhs) {
        final SequenceAlignment alignment1 = new SequenceAlignment(lhs.map, input.map);
        final SequenceAlignment alignment2 = new SequenceAlignment(rhs.map, input.map);
        return alignment1.getScore() < alignment2.getScore() ? 1 : -1;
      }
    });

    for (Carpet c : carpets) {
      System.out.println(c.name);
    }

  }

public static void Buy() throws FileNotFoundException {
        BuyCarpet buyCarpet = new BuyCarpet();
        System.out.println("How much money do you have?");
        Scanner input = new Scanner(System.in);
        long money = input.nextLong();
        ArrayList<String> chosenCarpets = buyCarpet.showLargestNumOfCarpet(money);
        System.out.println("These are the carpets that we offer you to buy:");
        System.out.println(chosenCarpets);
    }

}
