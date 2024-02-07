package edu.ntnu.stud.math;

public class Matrix2x2 {
  private double a00;
  private double a01;
  private double a10;
  private double a11;

  public Matrix2x2(double a00, double a01, double a10, double a11) {
    this.a00 = a00;
    this.a01 = a01;
    this.a10 = a10;
    this.a11 = a11;
  }

  public Vector2D multiply(Vector2D vector) {

    return vector;
  }


  public double getA00() {
    return a00;
  }

  public double getA01() {
    return a01;
  }

  public double getA10() {
    return a10;
  }

  public double getA11() {
    return a11;
  }

  public void setA00(double a00) {
    this.a00 = a00;
  }

  public void setA01(double a01) {
    this.a01 = a01;
  }

  public void setA10(double a10) {
    this.a10 = a10;
  }

  public void setA11(double a11) {
    this.a11 = a11;
  }
}
