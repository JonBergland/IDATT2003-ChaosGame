package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.observer.Observer;
import edu.ntnu.stud.observer.Subject;
import edu.ntnu.stud.transform.JuliaTransform;
import edu.ntnu.stud.utils.ButtonEnum;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

/**
 * This class represents a chaos game.
 * It contains a canvas and a description of the chaos game.
 * Goal: act as a model for a chaos game.
 * Implements the {@link Subject} interface to allow for observers to be notified of button events.
 */
public class ChaosGame implements Subject {

  /** EnumMap to store observers for each ButtonEnum. */
  private final EnumMap<ButtonEnum, List<Observer>> observersEnumMap
      = new EnumMap<>(ButtonEnum.class);

  /** The maximum number of iterations for fractal calculations.*/
  private static final int MAX_ITERATIONS = 1000;

  /** The logarithm of 2, used for fractal calculations.*/
  private static final double LOG_2 = Math.log(2);

  /** The escape radius for fractal calculations.*/
  private static final int ESCAPE_RADIUS = 2;

  /** The canvas on which the chaos game is played.*/
  private ChaosCanvas canvas;

  /** The description of the chaos game.*/
  private final ChaosGameDescription description;

  /** The current point in the chaos game.*/
  private Vector2D currentPoint;

  /** Random number generator for selecting transformations.*/
  private final Random random;

  /** The width of the canvas.*/
  private int width;

  /** The height of the canvas.*/
  private int height;

  /** The name of the chaos game. */
  private String chaosGameName;

  /**
   * Constructor for the ChaosGame class.
   *
   * @param description  the description of the chaos game.
   * @param width        the width of the canvas.
   * @param height       the height of the canvas.
   */
  public ChaosGame(ChaosGameDescription description, int width, int height) {
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null");
    }

    this.width = (width <= 0) ? 500 : width;
    this.height = (height <= 0) ? 500 : height;
    this.canvas = new ChaosCanvas(
        this.width, this.height, description.getMinCoords(), description.getMaxCoords());
    this.description = description;
    this.currentPoint = new Vector2D(0, 0);
    this.random = new Random();
    this.chaosGameName = "Not set";
  }

  /**
   * Get the canvas of the chaos game.
   *
   * @return the canvas of the chaos game.
   */
  public ChaosCanvas getCanvas() {
    return this.canvas;
  }

  /**
   * Get the description of the chaos game.
   *
   * @return the description of the chaos game.
   */
  public ChaosGameDescription getDescription() {
    return this.description;
  }

  /**
   * Get the name of the chaos game.
   *
   * @return the name of the chaos game.
   */
  public String getChaosGameName() {
    return this.chaosGameName;
  }

  /**
   * Get the width of the canvas.
   *
   * @return the width of the canvas
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Get the height of the canvas.
   *
   * @return the height of the canvas
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Sets the width of the canvas.
   *
   * @param width the width of the canvas
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Sets the height of the canvas.
   *
   * @param height the height of the canvas
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Update the minimum and maximum coordinates of the chaos game.
   *
   * @param minX0  the minimum x-coordinate of the chaos game.
   * @param minX1  the minimum y-coordinate of the chaos game.
   * @param maxX0  the maximum x-coordinate of the chaos game.
   * @param maxX1  the maximum y-coordinate of the chaos game.
   */
  public void updateMinAndMaxCoords(double minX0, double minX1, double maxX0, double maxX1) {
    if (minX0 >= maxX0 || minX1 >= maxX1) {
      throw new IllegalArgumentException("Bounds are invalid. "
          + "The minimum coordinate must be less than the maximum coordinate.");
    }

    if (this.description.getMinCoords().getX0() == minX0
        && this.description.getMinCoords().getX1() == minX1
        && this.description.getMaxCoords().getX0() == maxX0
        && this.description.getMaxCoords().getX1() == maxX1) {
      return;
    }

    this.description.setMinCoords(minX0, minX1);
    this.description.setMaxCoords(maxX0, maxX1);
    this.canvas = new ChaosCanvas(
        this.width, this.height, description.getMinCoords(), description.getMaxCoords());

    notifyObservers(ButtonEnum.COORDS, "Updated coordinates");
  }

  /**
   * Set the description of the chaos game.
   *
   * @param description the new description of the chaos game.
   */
  public void setDescription(ChaosGameDescription description) {
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null");
    }

    if (this.description.equals(description)) {
      this.canvas = new ChaosCanvas(
          this.width, this.height, description.getMinCoords(), description.getMaxCoords());

      notifyObservers(ButtonEnum.CANVAS_CHANGE, "Updated canvas");
      return;
    }

    this.description.setTransforms(description.getTransforms());
    this.description.setMinCoords(description.getMinCoords().getX0(),
        description.getMinCoords().getX1());
    this.description.setMaxCoords(description.getMaxCoords().getX0(),
        description.getMaxCoords().getX1());
    this.canvas = new ChaosCanvas(
        this.width, this.height, description.getMinCoords(), description.getMaxCoords());

    notifyObservers(ButtonEnum.TRANSFORM, "Updated description");
    notifyObservers(ButtonEnum.CANVAS_CHANGE, "Updated canvas");
  }

  /**
   * Set the name of the chaos game.
   *
   * @param chaosGameName the name of the chaos game.
   */
  public void setChaosGameName(String chaosGameName) {
    this.chaosGameName = chaosGameName;
    notifyObservers(ButtonEnum.GAME_NAME, "Updated name");
  }

  /**
   * Run the chaos game on the canvas.
   * This method will get a random transformation from the description
   * to transform the current point. The transformed point will be
   * put on the canvas. This process will be repeated for the amount of steps.
   *
   * @param steps  the number of steps to run the chaos game.
   */
  public void runSteps(int steps) {
    canvas.clear();
    currentPoint = new Vector2D(0, 0);
    if (steps < 0) {
      throw new IllegalArgumentException("Steps cannot be negative");
    }
    try {
      canvas.putPixel(currentPoint);
      for (int i = 0; i < steps; i++) {
        int randomIndex = random.nextInt(description.getTransforms().size());
        currentPoint = description.getTransforms().get(randomIndex).transform(currentPoint);
        canvas.putPixel(currentPoint);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Could not run steps: " + e.getMessage());
    }
  }

  /**
   * Run the iterative Julia method on the canvas.
   * The method will iterate over all pixels in the canvas and
   * apply the iterative Julia method to each pixel.
   *
   * @throws IllegalArgumentException if the transformation type is not Julia.
   */
  public void runIterativeJuliaMethod() throws IllegalArgumentException {
    canvas.clear();
    if (description.getTransformationType() != JuliaTransform.class) {
      throw new IllegalArgumentException("Transformation type is not Julia");
    }

    JuliaTransform juliaTransform = (JuliaTransform) description.getTransforms().getFirst();
    double cx = juliaTransform.getPoint().getX0();
    double cy = juliaTransform.getPoint().getX1();

    for (int i = 0; i < canvas.getWidth(); i++) {
      for (int j = 0; j < canvas.getHeight(); j++) {
        iterativeJuliaMethod(i, j, cx, cy);
      }
    }
  }

  /**
   * Apply the iterative Julia method to a pixel on the canvas.
   *
   * @param i   the row of the pixel
   * @param j   the column of the pixel
   * @param cx  the real part of the Julia set
   * @param cy  the imaginary of the Julia set
   */
  private void iterativeJuliaMethod(int i, int j, double cx, double cy) {
    double zx = (double) (i * (2 * ESCAPE_RADIUS)) / width - ESCAPE_RADIUS;
    double zy = (double) (j * (2 * ESCAPE_RADIUS)) / height - ESCAPE_RADIUS;

    int iteration = 0;

    while (Math.pow(zx, 2) + Math.pow(zy, 2) < ESCAPE_RADIUS * ESCAPE_RADIUS
        && iteration < MAX_ITERATIONS) {
      double tempX = Math.pow(zx, 2) - Math.pow(zy, 2);
      zy = 2.0 * zx * zy + cy;
      zx = tempX + cx;
      iteration++;
    }

    placePixel(i, j, zx, zy, iteration);
  }

  /**
   * Run the Mandelbrot method on the canvas.
   * The method will iterate over all pixels in the canvas and
   * generate a Mandelbrot set.
   */
  public void runMandelbrotMethod() {
    canvas.clear();
    for (int i = 0; i < canvas.getWidth(); i++) {
      for (int j = 0; j < canvas.getHeight(); j++) {
        mandelbrotMethod(i, j);
      }
    }
  }

  /**
   * Run the Mandelbrot method on a specific pixel on the canvas.
   * The method will apply the Mandelbrot method to the pixel at the specified
   * row and column.
   *
   * @param i the row of the pixel
   * @param j the column of the pixel
   */
  private void mandelbrotMethod(int i, int j) {
    double scaleX = 2.00 + 0.47;
    double scaleY = 1.12 + 1.12;

    double x0 = i * scaleX / width - 2.00;
    double y0 = j * scaleY / height - 1.12;

    double x = 0;
    double y = 0;

    int iteration = 0;

    while (Math.pow(x, 2) + Math.pow(y, 2) <= 4 && iteration < MAX_ITERATIONS) {
      double tempX = Math.pow(x, 2) - Math.pow(y, 2) + x0;
      y = 2 * x * y + y0;
      x = tempX;
      iteration++;
    }

    placePixel(i, j, x0, y0, iteration);
  }

  /**
   * Place a pixel on the canvas based on the given
   * coordinates and iteration count.
   *
   * @param i         the row of the pixel
   * @param j         the column of the pixel
   * @param x0        the initial x-coordinate
   * @param y0        the initial y-coordinate
   * @param iteration the number of iterations
   */
  private void placePixel(int i, int j, double x0, double y0, int iteration) {
    if (iteration == MAX_ITERATIONS) {
      canvas.putPixel(i, j, MAX_ITERATIONS);

    } else {
      double absZ = Math.pow(x0, 2) + Math.pow(y0, 2);
      iteration += (int) (1 - Math.log(Math.log(absZ)) / LOG_2);
      canvas.putPixel(i, j, iteration);
    }
  }

  /**
   * Print the canvas to the console.
   */
  public void printCanvas() {
    int[][] canvasArray = canvas.getCanvasArray();
    for (int[] row : canvasArray) {
      for (int col : row) {
        if (col >= 1) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }

  /**
   * Add an observer to the list of observers for a specific button.
   *
   * @param buttonEnum the button enumeration for which to add the observer.
   * @param observer   the observer to add.
   */

  @Override
  public void addObserver(ButtonEnum buttonEnum, Observer observer) {
    if (observersEnumMap.containsKey(buttonEnum)) {
      observersEnumMap.get(buttonEnum).add(observer);
    } else {
      List<Observer> observerList = new ArrayList<>();
      observerList.add(observer);
      observersEnumMap.put(buttonEnum, observerList);
    }
  }

  /**
   * Remove an observer from the list of observers for a specific button.
   *
   * @param buttonEnum the button enumeration for which to remove the observer.
   * @param observer   the observer to remove.
   * @throws IllegalArgumentException if the observer is not found for the specified button.
   */
  @Override
  public void removeObserver(ButtonEnum buttonEnum, Observer observer) {
    if (observersEnumMap.containsKey(buttonEnum)) {
      observersEnumMap.get(buttonEnum).remove(observer);
    } else {
      throw new IllegalArgumentException("Observer not found");
    }
  }

  /**
   * Notify all observers associated with a specific button enumeration.
   *
   * @param buttonEnum the button enumeration to notify the observers for.
   * @param buttonText the text to pass to the observers.
   * @throws IllegalArgumentException if the specified button enumeration
   *                                  is not found in the observers map.
   */
  protected void notifyObservers(ButtonEnum buttonEnum, String buttonText) {
    if (observersEnumMap.containsKey(buttonEnum)) {
      observersEnumMap.get(buttonEnum).forEach(observer -> observer.update(buttonEnum, buttonText));
    }
  }
}
