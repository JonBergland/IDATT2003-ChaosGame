package edu.ntnu.stud.math;

public class Vector2D {
  private final double x0;
  private final double x1;

  /**
   * Constructor for Vector2D
   * @param x0 - The first element in the vector.
   * @param x1 - The second element in the vector.
   */
  public Vector2D(double x0, double x1) {
    this.x0 = x0;
    this.x1 = x1;
  }

  /**
   * Getter for x0.
   * @return x0.
   */
  public double getX0() {
    return x0;
  }

  /**
   * Getter for x1.
   * @return x1.
   */
  public double getX1() {
    return x1;
  }

  public Vector2D add(Vector2D other) {
    double first_element = this.getX0() + other.getX0();
    double second_element = this.getX1() + other.getX1();
    return new Vector2D(first_element, second_element);
  }

  public Vector2D subtract(Vector2D other) {
    double first_element = this.getX0() - other.getX0();
    double second_element = this.getX1() - other.getX1();
    return new Vector2D(first_element, second_element);
  }
}
