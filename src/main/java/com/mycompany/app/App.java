package com.mycompany.app;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import com.mycompany.app.Models.Carpet;

public class App {

  static ArrayList<Carpet> carpets = new ArrayList<>();

  /**
   * calling all function(all parts of code)
   */
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

  /**
   * this method get all carpets stored in carpets.txt in
   * ./../../../../resources/carpets.txt
   * inter you input like this
   * NAME PRICE ROW COL
   * [MATRIX]
   */
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

  /**
   * part 2 impemation
   * for changeing the input you can change findPath.txt in
   * ./../../../../resources/findPath.txt
   * the input must incude size of all intersection and the the length of each
   * street of it like below
   * SIZE
   * [MATRIX]
   *
   * inter INF for not having a road to that intersection
   * change the function params for changeing the path that you want to find
   * 
   * @param startVertex the start point
   * @param endVertex the end point
   */
  public static void getPath(int startVertex, int endVertex) {
    Scanner input = new Scanner(App.class.getClassLoader().getResourceAsStream("findPath.txt"));
    Integer size = input.nextInt();

    int[][] graph = new int[size][4];

    for (int i = 0; i < size; i++) {
      String[] in = input.nextLine().split(" ");
      if (in.length < 4) {
        continue;
      }
      for (int j = 0; j < 4; j++) {
        graph[i][j] = in[j].equals("INF") ? FindPath.INF : Integer.parseInt(in[j]);
      }
    }

    FindPath findPath = new FindPath(size, graph);
    findPath.printPath(3, 1);

    input.close();
  }

  /**
   * part 2 impemation
   * for changeing the input you can change similerCarpet.txt in
   * ./../../../../resources/similerCarpet.txt
   * the input must incude carpet name and price and map like below
   * NAME PRICE ROW COL
   * [MATRIX]
   * this input will be comapre to the rest of carptes that added in getCarpets
   * method
   *
   */

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
