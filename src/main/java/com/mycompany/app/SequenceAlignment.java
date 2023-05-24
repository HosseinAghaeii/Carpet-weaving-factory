package com.mycompany.app;

import java.lang.Math;
public class SequenceAlignment {

  // Scoring scheme for match, mismatch, and indel
  private final int MATCH;
  private final int MISMATCH;
  private final int INDEL;

  // Strands to be analyzed
  private int[] strand1;
  private int[] strand2;

  // Solution matrix, score, and alignedStrings to be calculated
  private int[][] solution;
  private int score;
  private String[] alignedStrands;

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

    // Calculate solution matrix
    this.solution = findSolution();
    // Calculate score
    this.score = solution[solution.length - 1][solution[0].length - 1];
    // Calculate aligned strands
    this.alignedStrands = recursiveFindPath(solution.length - 1, solution[0].length - 1);
    // this.alignedStrands = findPath();
  }

  /**
   * Generates solution matrix given 2 RNA strands.
   * Uses the Needleman-Wunsch algorithm.
   *
   * @return the solution matrix
   */
  public int[][] findSolution() {
    // Generate solution matrix based on lengths of both strands
    // Let strand1 be the side strand
    // Let strand2 be the top strand
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

        // Set the value to the maximum of these three values
        // Position to the left + INDEL
        // Position above + INDEL
        // Position top-left + matchVALUE
        solution[i][j] = max(solution[i][j - 1] + INDEL, solution[i - 1][j] + INDEL,
            solution[i - 1][j - 1] + matchValue);
      }
    }

    // Return solution matrix
    return solution;
  }

  /**
   * Helper method for calculating a maximum of three numbers.
   *
   * @return the maximum of the three given integers
   */
  private int max(int a, int b, int c) {
    return Math.max(Math.max(a, b), c);
  }

  /**
   * Aligns RNA strands based off a solution matrix.
   * Finds one of the 'best' paths in the solution matrix.
   * Uses the 'best' path to generate aligned RNA strands.
   * This method does so non-recursively.
   * 
   * @return the two aligned RNA strands
   */
  public String[] findPath() {
    // Let strand1 be the side strand
    // Let strand2 be the top strand
    String alignedStrand1 = "";
    String alignedStrand2 = "";

    // Start from the bottom right of the solution matrix
    int i = solution.length - 1;
    int j = solution[0].length - 1;

    boolean matchAllowed;
    int matchValue;

    // While you are not at the top/left side of the matrix
    // This prevents an OOB exception
    while (i != 0 && j != 0) {
      // Reset matchAllowed value
      matchAllowed = true;
      // If the characters are different, and mismatching is not allowed
      // Diagonal moves are not legal
      // Calculate whether the diagonal move value
      if (strand1[i - 1] == strand2[j - 1])
        matchValue = MATCH;
      else
        matchValue = MISMATCH;
      // Calculate the best path to the current position
      // If the top-left position is a valid traceback
      if (solution[i - 1][j - 1] == solution[i][j] - matchValue && matchAllowed) {
        // Add the character corresponding to that position to both strands
        // This is the case for either a match or mismatch
        alignedStrand1 = strand1[i-1] + alignedStrand1;
        alignedStrand2 = strand2[j-1] + alignedStrand2;
        // Move to the new position
        i -= 1;
        j -= 1;
        // If the left position is a valid traceback
      } else if (solution[i][j - 1] == solution[i][j] - INDEL) {
        // Add '-' to strand1
        // Add the character correponding to that position to strand2
        // This represents a gap in the side strand
        alignedStrand1 = "-" + alignedStrand1;
        alignedStrand2 = strand2[j-1] + alignedStrand2;
        // Move to the new position
        j -= 1;
        // If the above position is a valid trackback
      } else {
        // Add '-' to strand2
        // Add the character corresponding to that position to strand1
        // This represents a gap in the top strand
        alignedStrand1 = strand1[i-1] + alignedStrand1;
        alignedStrand2 = "-" + alignedStrand2;
        // Move to the new position
        i -= 1;
        // If the top-left position is the best
      }
    }

    // If you are at the top of the matrix
    if (i == 0) {
      // Append characters corresponding to those positions to strand1
      // Append "-" for every space you are away from 0,0 to strand2
      // EX: If you are at 0,3 (j = 3), add "---" to strand2
      for (int k = 0; k < j; k++) {
        alignedStrand1 = "-" + alignedStrand1;
        alignedStrand2 = strand2[(j - k)] + alignedStrand2;
      }
      // If you are at the left most side of the matrix
    } else {
      // Append "-" for every space you are away from 0,0 to strand1
      // Append characters corresponding to those positions to strand2
      // EX: If you are at 3,0 (i = 3), add "---" to strand1
      for (int k = 0; k < i; k++) {
        alignedStrand1 = strand1[i - k] + alignedStrand1;
        alignedStrand2 = "-" + alignedStrand2;
      }
    }

    return new String[] { alignedStrand1, alignedStrand2 };
  }

  /**
   * Aligns RNA strands based off a solution matrix.
   * Finds one of the 'best' paths in the solution matrix.
   * Uses the 'best' path to generate aligned RNA strands.
   * This method does so recursively.
   * 
   * @return the two aligned RNA strands
   */
  public String[] recursiveFindPath(int i, int j) {
    String alignedStrand1 = "";
    String alignedStrand2 = "";

    // If you are at the top of the matrix
    if (i == 0) {
      // Append characters corresponding to those positions to strand1
      // Append "-" for every space you are away from 0,0 to strand2
      // EX: If you are at 0,3 (j = 3), add "---" to strand2
      for (int k = 0; k < j; k++) {
        alignedStrand1 = "-" + alignedStrand1;
        alignedStrand2 = strand2[j - k] + alignedStrand2;
      }

      return new String[] { alignedStrand1, alignedStrand2 };
      // If you are at the left most side of the matrix
    } else if (j == 0) {
      // Append "-" for every space you are away from 0,0 to strand1
      // Append characters corresponding to those positions to strand2
      // EX: If you are at 3,0 (i = 3), add "---" to strand1
      for (int k = 0; k < i; k++) {
        alignedStrand1 = strand1[i - k] + alignedStrand1;
        alignedStrand2 = "-" + alignedStrand2;
      }

      return new String[] { alignedStrand1, alignedStrand2 };
    }

    // Calculate the best path to the current position
    // Check position to the left, above, and top-left
    boolean matchAllowed = true;
    int matchValue;

    // If the characters are different, and mismatching is not allowed
    // Diagonal moves are not legal
    // Calculate whether the diagonal move value
    if (strand1[i-1] == strand2[j-1])
      matchValue = MATCH;
    else
      matchValue = MISMATCH;

    // If the top-left position is a valid traceback
    if (solution[i - 1][j - 1] == solution[i][j] - matchValue && matchAllowed) {
      // Add the character corresponding to that position to both strands
      // This is the case for either a match or mismatch
      alignedStrand1 = "" + strand1[i-1];
      alignedStrand2 = "" + strand2[j-1];
      // Move to the new position
      i -= 1;
      j -= 1;
      // If the left position is a valid traceback
    } else if (solution[i][j - 1] == solution[i][j] - INDEL) {
      // Add '-' to strand1
      // Add the character correponding to that position to strand2
      // This represents a gap in the side strand
      alignedStrand1 = "-";
      alignedStrand2 = "" + strand2[j-1];
      // Move to the new position
      j -= 1;
      // If the above position is a valid traceback
    } else {
      // Add '-' to strand2
      // Add the character corresponding to that position to strand1
      // This represents a gap in the top strand
      alignedStrand1 = "" + strand1[i-1];
      alignedStrand2 = "-";
      // Move to the new position
      i -= 1;
    }

    // Recursively find the characters for strand1/strand2 in the next position
    String[] alignedStrands = recursiveFindPath(i, j);
    // Attach the found characters to current alignedStrands
    alignedStrand1 = alignedStrands[0] + alignedStrand1;
    alignedStrand2 = alignedStrands[1] + alignedStrand2;

    return new String[] { alignedStrand1, alignedStrand2 };
  }

  /**
   * Method that prints out the alignment information.
   * Prints out the aligned strands and alignment score.
   */
  public void printStrandInfo() {
    // Print out side strand
    System.out.println(alignedStrands[0]);
    // Print out top strand
    System.out.println(alignedStrands[1]);
    // Print out strand score
    System.out.println("The score for this alignment is: " + score);
  }

  /**
   * Method to get the solution matrix.
   * 
   * @return the solution matrix
   */
  public int[][] getSolution() {
    return solution;
  }

  /**
   * Method to get the final score.
   * 
   * @return the final score
   */
  public int getScore() {
    return score;
  }

  /**
   * Method to return the aligned Strands.
   * 
   * @return the aligned strands
   */
  public String[] getAlignedStrands() {
    return alignedStrands;
  }
}
