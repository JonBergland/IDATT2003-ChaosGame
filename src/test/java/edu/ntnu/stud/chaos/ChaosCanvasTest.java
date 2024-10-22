package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ChaosCanvas}.
 */
class ChaosCanvasTest {
  /** The width of the canvas. */
  int width;
  /** The height of the canvas. */
  int height;
  /** The minimum coordinates of the canvas. */
  Vector2D minCoords;
  /** The maximum coordinates of the canvas. */
  Vector2D maxCoords;
  /** The ChaosCanvas object. */
  ChaosCanvas chaosCanvas;

  /**
   * Sets up the test environment.
   */
  @BeforeEach
  void setUp() {
    width = 100;
    height = 100;
    minCoords = new Vector2D(0, 0);
    maxCoords = new Vector2D(1, 1);
    chaosCanvas = new ChaosCanvas(width, height, minCoords, maxCoords);
  }

  /**
   * Test of the constructor with negative width and height.
   */
  @Test
  @DisplayName("Test constructor with negative width and height")
  void constructorWithNegativeWidthAndHeight() {
    width = -1;
    height = -1;
    ChaosCanvas chaosCanvas = new ChaosCanvas(width, height, minCoords, maxCoords);
    int[][] canvas = chaosCanvas.getCanvasArray();
    assertAll("Constructor with negative width and height",
        () -> assertEquals(500, canvas.length, "Incorrect canvas height"),
        () -> assertEquals(500, canvas[0].length, "Incorrect canvas width")
    );
  }

  /**
   * Test of the getPixel and put pixel methods.
   */
  @Test
  @DisplayName("Test getPixel and putPixel")
  void getAndPutPixel() {
    Vector2D point = new Vector2D(1, 1);
    chaosCanvas.putPixel(point);

    Vector2D point2 = new Vector2D(0, 1);

    assertAll("Get pixel",
        () -> assertEquals(1, chaosCanvas.getPixel(point), "Incorrect pixel value"),
        () -> assertEquals(0, chaosCanvas.getPixel(point2), "Incorrect pixel value")
    );
  }

  /**
   * Test of the getCanvasArray method.
   */
  @Test
  @DisplayName("Test getCanvasArray")
  void getCanvasArray() {
    int[][] canvas = chaosCanvas.getCanvasArray();
    assertAll("Get canvas array",
        () -> assertEquals(height, canvas.length, "Incorrect canvas height"),
        () -> assertEquals(width, canvas[0].length, "Incorrect canvas width")
    );
  }

  /**
   * Tests the {@code getWidth} method.
   * Ensures that the method returns the correct width of the canvas.
   */
  @Test
  @DisplayName("Test getWidth")
  void getWidth() {
    int expectedWidth = 100;
    int result = chaosCanvas.getWidth();
    assertNotNull(result, "Width should not be null");
    assertEquals(expectedWidth, result, "Width should match the expected value");
  }

  /**
   * Tests the {@code getHeight} method.
   * Ensures that the method returns the correct height of the canvas.
   */
  @Test
  @DisplayName("Test getHeight")
  void getHeight() {
    int expectedHeight = 100;
    int result = chaosCanvas.getHeight();
    assertNotNull(result, "Height should not be null");
    assertEquals(expectedHeight, result, "Height should match the expected value");
  }

  /**
   * Test of the clear method.
   */
  @Test
  @DisplayName("Test clear method")
  void clear() {
    chaosCanvas.putPixel(new Vector2D(1, 1));
    chaosCanvas.clear();
    int[][] canvas = chaosCanvas.getCanvasArray();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        assertEquals(0, canvas[i][j], "Incorrect pixel value");
      }
    }
  }

  /**
   * Tests the {@code putPixel} method.
   * Ensures that the method correctly adds the amount
   * to the specified pixel on the canvas.
   */
  @Test
  @DisplayName("Test putPixel")
  void testPutPixel() {
    int i = 1;
    int j = 1;
    int amount = 5;

    int initialValue = chaosCanvas.getCanvasArray()[i][j];

    chaosCanvas.putPixel(i, j, amount);
    int expectedValue = initialValue + amount;
    assertEquals(expectedValue, chaosCanvas.getCanvasArray()[i][j],
        "Pixel value should match the expected value after addition");
  }

  /**
   * Tests the {@code putPixel} method with invalid indices.
   * Ensures that the method throws an
   * ArrayIndexOutOfBoundsException for out-of-bounds indices.
   */
  @Test
  @DisplayName("Test putPixel with invalid indices")
  void testPutPixelInvalidIndices() {
    int i = -1; // Invalid row index
    int j = -1; // Invalid column index
    int amount = 5;

    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      chaosCanvas.putPixel(i, j, amount);
    }, "Expected an ArrayIndexOutOfBoundsException for out-of-bounds indices");
  }

  /**
   * Test of the convertToCanvasCoords method.
   */
  @Test
  @DisplayName("Test convertToCanvasCoords")
  void convertToCanvasCoords() {
    Vector2D point = new Vector2D(1, 0);
    Vector2D expectPoint = new Vector2D(99, 99);

    Vector2D result = chaosCanvas.convertToCanvasCoords(point);

    assertAll("Convert to canvas coords",
        () -> assertEquals(expectPoint.getX0(), result.getX0(), "Incorrect X0 value"),
        () -> assertEquals(expectPoint.getX1(), result.getX1(), "Incorrect X1 value")
    );
  }

  /**
   * Test of the convertToCanvasCoords method with
   * the point in the bottom left corner of the canvas.
   */
  @Test
  @DisplayName("Test convertToCanvasCoords bottom left corner")
  void convertToCanvasCoordsBottomLeftCorner() {
    Vector2D point = new Vector2D(0, 0);
    Vector2D expectPoint = new Vector2D(99, 0);

    Vector2D result = chaosCanvas.convertToCanvasCoords(point);

    assertAll("Convert to canvas coords",
        () -> assertEquals(expectPoint.getX0(), result.getX0(), "Incorrect X0 value"),
        () -> assertEquals(expectPoint.getX1(), result.getX1(), "Incorrect X1 value")
    );
  }

  /**
   * Test of the convertToCanvasCoords method with Julia
   * values and the point in the bottom left corner of
   * the canvas.
   */
  @Test
  @DisplayName("Test convertToCanvasCoords with Julia values bottom left corner")
  void convertToCanvasCoordsWithJuliaValuesBottomLeftCorner() {
    minCoords = new Complex(-1.6, -1);
    maxCoords = new Complex(1.6, 1);
    chaosCanvas = new ChaosCanvas(width, height, minCoords, maxCoords);

    Vector2D point = new Vector2D(-1.6, -1);
    Vector2D expectPoint = new Vector2D(99, 0);

    Vector2D result = chaosCanvas.convertToCanvasCoords(point);

    assertAll("Convert to canvas coords",
        () -> assertEquals(expectPoint.getX0(), result.getX0(), "Incorrect X0 value"),
        () -> assertEquals(expectPoint.getX1(), result.getX1(), "Incorrect X1 value")
    );
  }

  /**
   * Test of the convertToCanvasCoords method with Julia
   * values and the point in the top right corner of
   * the canvas.
   */
  @Test
  @DisplayName("Test convertToCanvasCoords with Julia values top right corner")
  void convertToCanvasCoordsWithJuliaValuesTopRightCorner() {
    minCoords = new Complex(-1.6, -1);
    maxCoords = new Complex(1.6, 1);
    chaosCanvas = new ChaosCanvas(width, height, minCoords, maxCoords);

    Vector2D point = new Vector2D(1.6, 1);
    Vector2D expectPoint = new Vector2D(0, 99);

    Vector2D result = chaosCanvas.convertToCanvasCoords(point);

    assertAll("Convert to canvas coords",
        () -> assertEquals(expectPoint.getX0(), result.getX0(), "Incorrect X0 value"),
        () -> assertEquals(expectPoint.getX1(), result.getX1(), "Incorrect X1 value")
    );
  }
}