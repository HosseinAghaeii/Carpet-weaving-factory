package com.mycompany.app;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

import com.mycompany.app.Models.Carpet;

public class App {

  static ArrayList<Carpet> carpets = new ArrayList<>();

  public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
    getCarpets();
    System.out.println("geting color grath");
    getGraphColor();
    System.out.println("find path");
    getPath(1, 2);
    System.out.println("find similer carpets");
    getSimilerCarpets();
    System.out.println("buy carpets");
    buyCarpet();
  }

  static void getCarpets() {
    Scanner scr = new Scanner(App.class.getClassLoader().getResourceAsStream("carpets.txt"));
    while (scr.hasNextLine()) {
      String str = scr.nextLine();
      if (str.isEmpty()) {
        continue;
      }
      String[] str2 = str.split(" ");
      int[][] map = new int[Integer.parseInt(str2[2])][Integer.parseInt(str2[3])];

      for (int i = 0; i < Integer.parseInt(str2[2]); i++)
        for (int j = 0; j < Integer.parseInt(str2[3]); j++)
          map[i][j] = scr.nextInt();
      carpets.add(new Carpet(str2[0], Integer.parseInt(str2[1]), map));
    }
    scr.close();

  }

  public static void getGraphColor() {
    Scanner input = new Scanner(App.class.getClassLoader().getResourceAsStream("graphColor.txt"));
    Integer size = input.nextInt();
    Graph g1 = new Graph(size);

    input.nextLine();
    do {
      if (input.hasNextInt()) {
        g1.addEdge(input.nextInt(), input.nextInt());
      } else
        break;
    } while (input.hasNextLine());

    g1.greedyColoring();
    input.close();
  }

  public static void getPath(int startVertex, int endVertex) {
    Scanner input = new Scanner(App.class.getClassLoader().getResourceAsStream("findPath.txt"));
    findPath findPath = new findPath();
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

    path = findPath.constructPath(startVertex, endVertex);
    findPath.printPath(path);

    input.close();
  }

  public static void getSimilerCarpets() {
    Scanner scr = new Scanner(App.class.getClassLoader().getResourceAsStream("similerCarpet.txt"));
    String str = scr.nextLine();
    String[] str2 = str.split(" ");
    int[][] map = new int[Integer.parseInt(str2[2])][Integer.parseInt(str2[3])];

    for (int i = 0; i < Integer.parseInt(str2[2]); i++)
      for (int j = 0; j < Integer.parseInt(str2[3]); j++)
        map[i][j] = scr.nextInt();
    Carpet input = new Carpet(str2[0], Integer.parseInt(str2[1]), map);
    scr.close();

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

  public static void buyCarpet()  {
    BuyCarpet buyCarpet = new BuyCarpet();
    System.out.println("How much money do you have?");
    Scanner input = new Scanner(System.in);
    long money = input.nextLong();
    ArrayList<String> chosenCarpets = buyCarpet.showLargestNumOfCarpet(money,carpets);
    System.out.println("These are the carpets that we offer you to buy:");
    System.out.println(chosenCarpets);
    input.close();
  }

}
