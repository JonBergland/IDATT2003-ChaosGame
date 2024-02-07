package edu.ntnu.stud.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix2x2Test {
  Matrix2x2 matrix2x2;
  double a00;
  double a01;
  double a10;
  double a11;

  @BeforeEach
  void setUp() {
    a00 = 2;
    a01 = 1;
    a10 = 1;
    a11 = 2;
    matrix2x2 = new Matrix2x2(a00, a01, a10, a11);
  }

  @Test
  void multiply() {
  }

  @Test
  void getA00() {
    assertEquals(a00, matrix2x2.getA00());
  }

  @Test
  void getA01() {
    assertEquals(a01, matrix2x2.getA01());
  }

  @Test
  void getA10() {
    assertEquals(a10, matrix2x2.getA10());
  }

  @Test
  void getA11() {
    assertEquals(a11, matrix2x2.getA11());
  }

  @Test
  void setA00() {
    double newA00 = 0;
    matrix2x2.setA00(newA00);
    assertEquals(newA00, matrix2x2.getA00());
  }

  @Test
  void setA01() {
    double newA01 = 0;
    matrix2x2.setA01(newA01);
    assertEquals(newA01, matrix2x2.getA01());
  }

  @Test
  void setA10() {
    double newA10 = 0;
    matrix2x2.setA10(newA10);
    assertEquals(newA10, matrix2x2.getA10());
  }

  @Test
  void setA11() {
    double newA11 = 0;
    matrix2x2.setA11(newA11);
    assertEquals(newA11, matrix2x2.getA11());
  }
}