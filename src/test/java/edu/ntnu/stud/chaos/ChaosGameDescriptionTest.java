/**
 * Test class for {@link ChaosGameDescription}.
 */

package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.AffineTransform2D;
import edu.ntnu.stud.transform.Transform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ChaosGameDescription}.
 */
class ChaosGameDescriptionTest {

  /** Represents the minimum coordinates (x, y) of a 2D vector. */
  Vector2D minCoords;

  /** Represents the maximum coordinates (x, y) of a 2D vector. */
  Vector2D maxCoords;

  /** Represents the first transformation in the Chaos Game. */
  Transform2D transform1;

  /** Represents the second transformation in the Chaos Game. */
  Transform2D transform2;

  /** Represents the third transformation in the Chaos Game. */
  Transform2D transform3;

  /** Represents a list of transformations in the Chaos Game. */
  List<Transform2D> transform = new ArrayList<>();

  /** Represents the description of the Chaos Game,
   * including transformations and coordinate bounds. */
  ChaosGameDescription description;


  /**
   * Sets up the test environment.
   */
  @BeforeEach
  void setUp() {
    minCoords = new Vector2D(0, 0);
    maxCoords = new Vector2D(1, 1);

    transform1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
    transform2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5));
    transform3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0));

    transform.add(transform1);
    transform.add(transform2);
    transform.add(transform3);

    description = new ChaosGameDescription(transform, minCoords, maxCoords);
  }

  /**
   * Nested tests for ChaosGameDescription constructor.
   */
  @Nested
  @DisplayName("Tests for ChaosGameDescription constructor")
  class ConstructorTests {
    /**
     * Test constructor with valid input.
     */
    @Test
    @DisplayName("Test constructor with valid input")
    void getTransforms() {
      assertDoesNotThrow(() -> new ChaosGameDescription(transform, minCoords, maxCoords));
    }

    /**
     * Test constructor with null input.
     */
    @Test
    @DisplayName("Test constructor with null input")
    void constructorWithNullInput() {
      assertAll("Null input",
          () -> assertThrows(IllegalArgumentException.class, () -> new ChaosGameDescription(null, minCoords, maxCoords)),
          () -> assertThrows(IllegalArgumentException.class, () -> new ChaosGameDescription(transform, null, maxCoords)),
          () -> assertThrows(IllegalArgumentException.class, () -> new ChaosGameDescription(transform, minCoords, null))
      );
    }
  }

  /**
   * Nested tests for ChaosGameDescription get-methods.
   */
  @Nested
  @DisplayName("Tests for ChaosGameDescription get-methods")
  class GetMethodsTests {

    /**
     * Test getTransforms method.
     */
    @Test
    @DisplayName("Test getTransforms method")
    void getTransforms() {
      description.getTransforms().forEach(t -> assertTrue(transform.contains(t)));
    }

    /**
     * Test getTransforms method with not same object.
     */
    @Test
    @DisplayName("Test getTransforms method not same object")
    void getTransformsNotSameObject() {
      assertNotSame(transform, description.getTransforms());
    }

    /**
     * Test getMaxCoords method.
     */
    @Test
    @DisplayName("Test getMaxCoords method")
    void getMaxCoords() {
      assertAll("MaxCoords",
          () -> assertEquals(1, description.getMaxCoords().getX0()),
          () -> assertEquals(1, description.getMaxCoords().getX1()),
          () -> assertNotEquals(maxCoords, description.getMaxCoords())
      );
    }

    /**
     * Test getMinCoords method.
     */
    @Test
    @DisplayName("Test getMinCoords method")
    void getMinCoords() {
      assertAll("MinCoords",
          () -> assertEquals(0, description.getMinCoords().getX0()),
          () -> assertEquals(0, description.getMinCoords().getX1()),
          () -> assertNotEquals(minCoords, description.getMinCoords())
      );
    }

    /**
     * Tests the {@code setMinCoords} method.
     * Ensures that the minimum coordinates are set correctly.
     */
    @Test
    @DisplayName("Test setMinCoords")
    void testSetMinCoords() {
      double x0 = -2.0;
      double x1 = -1.5;

      description.setMinCoords(x0, x1);

      assertEquals(x0, description.getMinCoords().getX0(),
          "Minimum x0 coordinate should match the expected value");
      assertEquals(x1, description.getMinCoords().getX1(),
          "Minimum x1 coordinate should match the expected value");
    }

    /**
     * Tests the {@code setMaxCoords} method.
     * Ensures that the maximum coordinates are set correctly.
     */
    @Test
    @DisplayName("Test setMaxCoords")
    void testSetMaxCoords() {
      double x0 = 2.0;
      double x1 = 1.5;

      description.setMaxCoords(x0, x1);

      assertEquals(x0, description.getMaxCoords().getX0(),
          "Maximum x0 coordinate should match the expected value");
      assertEquals(x1, description.getMaxCoords().getX1(),
          "Maximum x1 coordinate should match the expected value");
    }
  }

  /**
   * Nested tests for ChaosGameDescription set-methods.
   */
  @Nested
  @DisplayName("Tests for ChaosGameDescription set-methods")
  class SetMethodsTests {

    /**
     * Test setTransforms method.
     */
    @Test
    @DisplayName("Test setTransforms method")
    void setTransforms() {
      List<Transform2D> newTransforms = new ArrayList<>();
      Transform2D newTransform1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
      Transform2D newTransform2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5));
      Transform2D newTransform3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0));

      newTransforms.add(newTransform1);
      newTransforms.add(newTransform2);
      newTransforms.add(newTransform3);

      description.setTransforms(newTransforms);

      description.getTransforms().forEach(t -> assertTrue(newTransforms.contains(t)));
    }

    /**
     * Test setTransforms method with not same object.
     */
    @Test
    @DisplayName("Test setTransforms method not same object")
    void setTransformsNotSameObject() {
      List<Transform2D> newTransforms = new ArrayList<>();
      Transform2D newTransform1 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0));
      Transform2D newTransform2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5));
      Transform2D newTransform3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0));

      newTransforms.add(newTransform1);
      newTransforms.add(newTransform2);
      newTransforms.add(newTransform3);

      description.setTransforms(newTransforms);

      assertNotSame(newTransforms, description.getTransforms());
    }

    /**
     * Test setTransforms method with null input.
     */
    @Test
    @DisplayName("Test setTransforms method with null input")
    void setTransformsWithNullInput() {
      assertAll(
          () -> assertThrows(IllegalArgumentException.class, () -> description.setTransforms(null)),
          () -> assertThrows(IllegalArgumentException.class, () -> description.setTransforms(new ArrayList<>()))
      );
    }

    /**
     * Test setTransforms method with same object.
     */
    @Test
    @DisplayName("Test setTransforms method with same object")
    void setTransformsWithSameObject() {

      description.setTransforms(transform);

      assertAll("Same object",
          () -> assertSame(transform.size(), description.getTransforms().size()),
          () -> assertSame(transform.getFirst(), description.getTransforms().getFirst()),
          () -> assertSame(transform.getLast(), description.getTransforms().getLast())
      );
    }
  }
}