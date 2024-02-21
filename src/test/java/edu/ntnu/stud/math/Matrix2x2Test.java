package edu.ntnu.stud.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Matrix2x2 class.
 */
@DisplayName("Tests for Matrix2x2 class")
class Matrix2x2Test {

  // Arrange
  Matrix2x2 matrix2x2;
  double a00;
  double a01;
  double a10;
  double a11;

  /**
   * Set up before each test.
   */
  @BeforeEach
  void setUp() {

    // Arrange
    a00 = 2;
    a01 = 1;
    a10 = 1;
    a11 = 2;
    matrix2x2 = new Matrix2x2(a00, a01, a10, a11);
  }

  /**
   * Nested test class for the constructor.
   */
  @Nested
  @DisplayName("Constructor")
  class constructorMatrix2x2 {

    /**
     * Test case for the constructor.
     */
    @Test
    @DisplayName("Test constructor")
    void constructor() {

      // Assert
      assertAll("Constructor",
          () -> assertEquals(2, matrix2x2.getA00(), "Expected value of A00 should be correct."),
          () -> assertEquals(1, matrix2x2.getA01(), "Expected value of A01 should be correct."),
          () -> assertEquals(1, matrix2x2.getA10(), "Expected value of A10 should be correct."),
          () -> assertEquals(2, matrix2x2.getA11(), "Expected value of A11 should be correct.")
      );
    }
  }

  /**
   * Nested test class for the multiply method.
   */
  @Nested
  @DisplayName("multiplyMethod")
  class multiplyMethod {
    /**
     * Test case for the multiply method.
     */
    @Test
    @DisplayName("Test multiply method")
    void multiply() {

      // Arrange
      Vector2D multiplyVector = new Vector2D(2, 1);

      // Act
      Vector2D resultVector = matrix2x2.multiply(multiplyVector);

      // Assert
      assertAll("Multiplication",
          () -> assertEquals(5.0, resultVector.getX0(), "Expected x0 component of the result vector to be correct."),
          () -> assertEquals(4.0, resultVector.getX1(), "Expected x1 component of the result vector to be correct.")
      );
    }
  }

  /**
   * Nested test class for the get methods.
   */
  @Nested
  @DisplayName("getMethods")
  class getMethods{

    /**
     * Test case for the getA00 method.
     */
    @Test
    @DisplayName("Test getA00 method")
    void getA00() {

      // Assert
      assertEquals(a00, matrix2x2.getA00(), "Expected value of A00 to be correct.");
    }

    /**
     * Test case for the getA01 method.
     */
    @Test
    @DisplayName("Test getA01 method")
    void getA01() {

      // Assert
      assertEquals(a01, matrix2x2.getA01(), "Expected value of A01 to be correct.");
    }

    /**
     * Test case for the getA10 method.
     */
    @Test
    @DisplayName("Test getA10 method")
    void getA10() {

      // Assert
      assertEquals(a10, matrix2x2.getA10(), "Expected value of A10 to be correct.");
    }

    /**
     * Test case for the getA11 method.
     */
    @Test
    @DisplayName("Test getA11 method")
    void getA11() {

      // Assert
      assertEquals(a11, matrix2x2.getA11(), "Expected value of A11 to be correct.");
    }
  }

  /**
   * Nested test class for the set methods.
   */
  @Nested
  @DisplayName("setMethods")
  class setMethods{

    /**
     * Test case for the setA00 method.
     */
    @Test
    @DisplayName("Test setA00 method")
    void setA00() {

      // Arrange
      double newA00 = 0;

      // Act
      matrix2x2.setA00(newA00);

      // Assert
      assertEquals(newA00, matrix2x2.getA00(), "setA00 should set value correctly.");
    }

    /**
     * Test case for the setA01 method.
     */
    @Test
    @DisplayName("Test setA01 method")
    void setA01() {

      // Arrange
      double newA01 = 0;

      // Act
      matrix2x2.setA01(newA01);

      // Assert
      assertEquals(newA01, matrix2x2.getA01(), "setA01 should set value correctly.");
    }

    /**
     * Test case for the setA10 method.
     */
    @Test
    @DisplayName("Test setA10 method")
    void setA10() {

      // Arrange
      double newA10 = 0;

      // Act
      matrix2x2.setA10(newA10);

      // Assert
      assertEquals(newA10, matrix2x2.getA10(), "setA10 should set value correctly.");
    }

    /**
     * Test case for the setA11 method.
     */
    @Test
    @DisplayName("Test setA11 method")
    void setA11() {

      // Arrange
      double newA11 = 0;

      // Act
      matrix2x2.setA11(newA11);

      // Assert
      assertEquals(newA11, matrix2x2.getA11(), "setA11 should set value correctly.");
    }
  }

  /**
   * Nested test class for validating the {@link Matrix2x2#toString()} method.
   */
  @Nested
  @DisplayName("toString method")
  class toStringMethod {

    /**
     * Test method for the {@link Matrix2x2#toString()} method.
     * Verifies that the toString method correctly generates the string representation
     * of the matrix in the expected format.
     */
    @Test
    @DisplayName("Test toString method")
    void testToString() {
      // Arrange
      String expectedString = "[2.0 1.0\n 1.0 2.0]";

      // Act
      String actualString = matrix2x2.toString();

      // Assert
      assertEquals(expectedString, actualString, "Expected toString output doesn't match actual toString output.");
    }
  }

}