package com.mycompany.app.Models;

import com.mycompany.app.SequenceAlignment;

public class Carpet  {
  public String name;
  int price;
  public int[] map;

  public Carpet(String name, int price, int[][] map) {
    this.name = name;
    this.price = price;
    this.map = to1DArray(map);
  }

  private int[] to1DArray(int[][] map) {
    int size = 0;
    for (int i = 0; i < map.length; i++) {
      size += map[i].length;
    }
    int[] result = new int[size];
    int z = 0;
    for (int row = 0; row < map.length; row++) {
      for (int col = 0; col < map[row].length; col++) {
        result[z] = map[row][col];
        z++;
      }
    }
    return result;

  }


}
