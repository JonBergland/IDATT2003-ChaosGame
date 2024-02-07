package edu.ntnu.stud.math;

public class Complex extends Vector2D {
  public Complex(double realPart, double imaginaryPart){
    super(realPart, imaginaryPart);
  }

  @Override
  public Vector2D add(Vector2D other) {
    return super.add(other);
  }

  @Override
  public Vector2D subtract(Vector2D other) {
    return super.subtract(other);
  }


  /**
   *
   * @return Complex vector
   */
  public Complex sqrt(){
    return new Complex(0,0);
  }
}
