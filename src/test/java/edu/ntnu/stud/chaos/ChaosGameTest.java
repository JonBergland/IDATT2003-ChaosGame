/**
 * Test class for {@link ChaosGame}.
 */

package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.AffineTransform2D;
import edu.ntnu.stud.transform.Transform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ChaosGame}.
 */
class ChaosGameTest {

  /** Represents a Chaos Game instance which generates
   * fractal images based on a given ChaosGameDescription.*/
  ChaosGame chaosGame;

  /** Represents the description of the Chaos Game,
   * including transformations and coordinate bounds.*/
  ChaosGameDescription chaosGameDescription;

  /** Represents the minimum coordinates (x, y)
   * of the image generated by the Chaos Game.*/
  Vector2D minCoords;

  /** Represents the maximum coordinates (x, y)
   * of the image generated by the Chaos Game.*/
  Vector2D maxCoords;

  /** Represents the width of the image generated by the Chaos Game.*/
  int width;

  /** Represents the height of the image generated by the Chaos Game.*/
  int height;


  /**
   * Sets up the test environment.
   */
  @BeforeEach
  void setUp() {
    List<Transform2D> affineTransform2DList = new ArrayList<>();
    Vector2D vector1 = new Vector2D(0, 0);
    Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5);

    AffineTransform2D affineTransform2D = new AffineTransform2D(matrix, vector1);
    affineTransform2DList.add(affineTransform2D);

    minCoords = new Vector2D(0, 0);
    maxCoords = new Vector2D(1, 1);

    width = 100;
    height = 100;
    chaosGameDescription = new ChaosGameDescription(affineTransform2DList, minCoords, maxCoords);
    chaosGame = new ChaosGame(chaosGameDescription, width, height);
  }

  /**
   * Test for getting the canvas.
   */
  @Test
  @DisplayName("Get Canvas")
  void getCanvas() {
    ChaosCanvas canvas = new ChaosCanvas(width, height, minCoords, maxCoords);

    assertAll("Get canvas",
        () -> assertNotNull(chaosGame.getCanvas(), "Canvas is null"),
        () -> assertEquals(canvas.getClass(), chaosGame.getCanvas().getClass(), "Incorrect canvas class"),
        () -> assertEquals(canvas.getCanvasArray().length, chaosGame.getCanvas().getCanvasArray().length, "Incorrect canvas height"),
        () -> assertEquals(canvas.getCanvasArray()[0].length, chaosGame.getCanvas().getCanvasArray()[0].length, "Incorrect canvas width")
        );
  }
}