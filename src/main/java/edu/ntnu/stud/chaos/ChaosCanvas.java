package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.AffineTransform2D;

/**
 * This class represents a canvas for a chaos game.
 * It contains a 2D array of integers representing the canvas.
 * Goal: act as a model for a canvas.
 */
public class ChaosCanvas {

  /** A 2D array representing the canvas.*/
  private final int[][] canvas;

  /**
   * The width of the canvas.
   * This represents the number of columns in the canvas array.
   */
  private final int width;

  /**
   * The height of the canvas.
   * This represents the number of rows in the canvas array.
   */
  private final int height;

  /**
   * The minimum coordinates in the coordinate system.
   * This represents the lower bound of the coordinate
   * space mapped to the canvas.
   */
  private final Vector2D minCoords;

  /**
   * The maximum coordinates in the coordinate system.
   * This represents the upper bound of the coordinate
   * space mapped to the canvas.
   */
  private final Vector2D maxCoords;

  /**
   * The transformation applied to convert coordinates
   * to indices in the canvas array.
   * This affine transformation is used to map the
   * coordinate space (defined by minCoords and maxCoords)
   * to the array indices of the canvas.
   */
  private final AffineTransform2D transformCoordsToIndices;

  /**
   * Constructor for the ChaosCanvas class.
   *
   * @param width     the width of the canvas.
   * @param height    the height of the canvas.
   * @param minCoords the minimum coordinates of the canvas.
   * @param maxCoords the maximum coordinates of the canvas.
   */
  public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords) {
    this.width = (width <= 0) ? 500 : width;
    this.height = (height <= 0) ? 500 : height;
    this.canvas = new int[this.height][this.width];
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;

    this.transformCoordsToIndices = initializeTransform();

  }

  /**
   * Get the width of the canvas.
   *
   * @return the width of the canvas.
   *
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Get the height of the canvas.
   *
   * @return the height of the canvas.
   *
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Get the pixel value at a given point.
   *
   * @param point the point to get the pixel value from.
   * @return the pixel value at the given point.
   */
  public int getPixel(Vector2D point) {
    Vector2D canvasCoords = convertToCanvasCoords(point);
    return this.canvas[(int) canvasCoords.getX0()][(int) canvasCoords.getX1()];
  }

  /**
   * Put a pixel at a given point indicated by a Vector.
   * The point will be converted to canvas coordinates before
   * being placed on the canvas.
   *
   * @param point the point to put the pixel at.
   */
  public void putPixel(Vector2D point) {
    Vector2D canvasCoords = convertToCanvasCoords(point);
    this.canvas[(int) canvasCoords.getX0()][(int) canvasCoords.getX1()]
        += 1 / (this.canvas[(int) canvasCoords.getX0()][(int) canvasCoords.getX1()] + 1);
  }

  /**
   * Put a pixel at a given list placement.
   *
   * @param i       the row to place the pixel.
   * @param j       the column to place the pixel.
   * @param amount  the amount to add to pixel on the canvas.
   */
  public void putPixel(int i, int j, int amount) {
    this.canvas[i][j] += amount;
  }

  /**
   * Get the canvas array.
   *
   * @return the canvas array.
   */
  public int[][] getCanvasArray() {
    return this.canvas;
  }

  /**
   * Clear the canvas and set all pixels to 0.
   */
  public void clear() {
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        this.canvas[i][j] = 0;
      }
    }
  }

  /**
   * Convert a Vector to canvas coordinates.
   *
   * @param point the point to convert.
   * @return the point in canvas coordinates.
   */
  public Vector2D convertToCanvasCoords(Vector2D point) {
    return this.transformCoordsToIndices.transform(point);
  }

  /**
   * Initialize the transformation used to turn coordinates into indices.
   *
   * @return the transformation from coordinates to indices.
   */
  private AffineTransform2D initializeTransform() {
    // Make matrix
    double a00 = 0;
    double a01 = (height - 1) / (minCoords.getX1() - maxCoords.getX1());
    double a10 = (width - 1) / (maxCoords.getX0() - minCoords.getX0());
    double a11 = 0;

    Matrix2x2 matrix = new Matrix2x2(a00, a01, a10, a11);

    // Make vector
    double x0 = (height - 1) * maxCoords.getX1() / (maxCoords.getX1() - minCoords.getX1());
    double x1 = (width - 1) * minCoords.getX0() / (minCoords.getX0() - maxCoords.getX0());

    Vector2D vector = new Vector2D(x0, x1);

    return new AffineTransform2D(matrix, vector);
  }
}
