package edu.ntnu.stud.math;

/**
 * The Complex class represents a complex number, consisting of a real part and an imaginary part.
 * It extends the Vector2D class, inheriting its properties and methods for 2D vectors.
 */
public class Complex extends Vector2D {

  /**
   * Constructs a new Complex number with the specified real and imaginary parts.
   *
   * @param realPart The real part of the complex number.
   * @param imaginaryPart The imaginary part of the complex number.
   */
  public Complex(double realPart, double imaginaryPart){
    super(realPart, imaginaryPart);
  }

  /**
   * Adds another Vector2D (considered as a complex number) to this complex number.
   *
   * @param other The other Vector2D to be added.
   * @return A new Complex object representing the sum of this and the other complex number.
   */
  @Override
  public Complex add(Vector2D other) {
    double sumX0 = this.getX0() + other.getX0();
    double sumX1 = this.getX1() + other.getX1();
    return new Complex(sumX0, sumX1);
  }

  /**
   * Subtracts another Vector2D (considered as a complex number) from this complex number.
   *
   * @param other The other Vector2D to be subtracted.
   * @return A new Complex object representing the result of subtracting the other complex number from this.
   */
  @Override
  public Complex subtract(Vector2D other) {
    double sumX0 = this.getX0() - other.getX0();
    double sumX1 = this.getX1() - other.getX1();
    return new Complex(sumX0, sumX1);
  }

  /**
   * Computes the principal square root of this complex number.
   *
   * @return A new Complex object representing the principal square root of this complex number.
   */
  public Complex sqrt() {
    double x = getX0();
    double y = getX1();

    double newX = partSqrt(x, y, 1);
    double newY = Math.signum(y) * partSqrt(x, y, -1);

    return new Complex(newX, newY);
  }

  /**
   * Helper method to compute the square root of a part of the complex number.
   *
   * @param x The real part of the complex number.
   * @param y The imaginary part of the complex number.
   * @param sign The sign multiplier.
   * @return The computed part of the square root.
   */
  private double partSqrt(double x, double y, int sign) {
    return Math.sqrt((double) 1 /2 * (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) + sign*x));
  }
}
