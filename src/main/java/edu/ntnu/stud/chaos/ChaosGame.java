package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Vector2D;

import java.util.Random;

public class ChaosGame {
  ChaosCanvas canvas;
  ChaosGameDescription description;
  Vector2D currentPoint;
  Random random;

  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
    this.description = description;
    this.currentPoint = new Vector2D(0, 0);
    this.random = new Random();
  }

  public ChaosCanvas getCanvas() {
    return this.canvas;
  }

  public void runSteps(int steps) {
    canvas.clear();
    canvas.putPixel(currentPoint);
    for (int i = 0; i < steps; i++) {
      int randomIndex = random.nextInt(description.getTransforms().size());
      currentPoint = description.getTransforms().get(randomIndex).transform(currentPoint);
      canvas.putPixel(currentPoint);
    }
    printCanvas();
  }

  public void printCanvas() {
    int[][] canvasArray = canvas.getCanvasArray();
    for (int[] row : canvasArray) {
      for (int col : row) {
        if (col == 1) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }

  }
}
