package com.mycompany.app;

import java.lang.Math;
public class SequenceAlignment {

  private final int MATCH;
  private final int MISMATCH;
  private final int INDEL;

  private int[] strand1;
  private int[] strand2;

  private int[][] solution;
  private int score;

  /**
   * Constructor taking in two strands.
   * Default values:
   * MATCH = 1
   * MISMATCH = -1
   * INDEL = -1
   * allowMismatch = true
   *
   * @param strand1 the first strand
   * @param strand2 the second strand
   */
  public SequenceAlignment(int[] strand1, int[] strand2) {
    this.strand1 = strand1;
    this.strand2 = strand2;

    this.MATCH = 1;
    this.MISMATCH = -1;
    this.INDEL = -1;

    this.solution = findSolution();
    this.score = solution[solution.length - 1][solution[0].length - 1];
  }

  /**
   * Generates solution matrix given 2 RNA strands.
   * Uses the Needleman-Wunsch algorithm.
   *
   * @return the solution matrix
   */
  public int[][] findSolution() {
    int[][] solution = new int[strand1.length + 1][strand2.length + 1];
    // Set the starting point to value of 0
    solution[0][0] = 0;

    // Fill in the top row. Moving to the right always adds the value of INDEL.
    for (int i = 1; i < strand2.length + 1; i++) {
      solution[0][i] = solution[0][i - 1] + INDEL;
    }

    // Fill in the left column. Moving down always adds the value of INDEL.
    for (int i = 1; i < strand1.length + 1; i++) {
      solution[i][0] = solution[i - 1][0] + INDEL;
    }

    // Fill in the rest of the matrix based on a few rules.
    for (int i = 1; i < strand1.length + 1; i++) {
      for (int j = 1; j < strand2.length + 1; j++) {

        int matchValue;

        // If the characters that correspond to the grid position are equal for both
        // strands
        // Set the matchValue to MATCH, else set the matchValue to MISMATCH
        if (strand1[i - 1] == strand2[j - 1])
          matchValue = MATCH;
        else
          matchValue = MISMATCH;

        solution[i][j] = max(solution[i][j - 1] + INDEL, solution[i - 1][j] + INDEL,
            solution[i - 1][j - 1] + matchValue);
      }
    }

    // Return solution matrix
    return solution;
  }

  private int max(int a, int b, int c) {
    return Math.max(Math.max(a, b), c);
  }

  public int getScore() {
    return score;
  }

}
