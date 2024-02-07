package edu.ntnu.stud.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {
  double x0;
  double x1;
  Vector2D testVector2D;

  @BeforeEach
  void setUp() {
    x0 = 1;
    x1 = 3;
    testVector2D = new Vector2D(x0, x1);
  }

  @Test
  void TestGetX0() {
    assertEquals(x0, testVector2D.getX0());
  }

  @Test
  void TestGetX1() {
    assertEquals(x1, testVector2D.getX1());
  }

  @Test
  void TestAdd() {
  }

  @Test
  void TestSubtract() {
  }
}