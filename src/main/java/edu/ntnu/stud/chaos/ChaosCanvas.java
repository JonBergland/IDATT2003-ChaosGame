package edu.ntnu.stud.chaos;
import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.AffineTransform2D;

public class ChaosCanvas {

  private final int [][] canvas;
  private final int width;
  private final int height;
  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final AffineTransform2D transformCoordsToIndices;

  public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords) {
    this.width = width;
    this.height = height;
    this.canvas = new int[height][width]; //height = M, width = N
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;

    this.transformCoordsToIndices = initializeTransform();

  }

  public int getPixel(Vector2D point) {
    Vector2D canvasCoords = convertToCanvasCoords(point);
    return this.canvas[(int)canvasCoords.getX0()][(int)canvasCoords.getX1()];
  }

  public void putPixel(Vector2D point) {
    Vector2D canvasCoords = convertToCanvasCoords(point);
    this.canvas[(int)canvasCoords.getX0()][(int)canvasCoords.getX1()] = 1;
  }

  public int[][] getCanvasArray() {
    return this.canvas;
  }

  public void clear() {
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        this.canvas[i][j] = 0;
      }
    }
  }

  public Vector2D convertToCanvasCoords(Vector2D point) {
    return this.transformCoordsToIndices.transform(point);
  }

  private AffineTransform2D initializeTransform() {
    // Make matrix
    double a00 = 0;
    double a01 = (height - 1) / (minCoords.getX1() - maxCoords.getX1());
    double a10 = (width - 1) / (maxCoords.getX0() - minCoords.getX0());
    double a11 = 0;

    Matrix2x2 matrix = new Matrix2x2(a00, a01, a10, a11);

    // Make vector
    double x0 = (height-1)* maxCoords.getX1() / (maxCoords.getX1() - minCoords.getX1());
    double x1 = (width-1) * minCoords.getX0() / (minCoords.getX0() - maxCoords.getX0());

    Vector2D vector = new Vector2D(x0, x1);

    return new AffineTransform2D(matrix, vector);
  }
}
