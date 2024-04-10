package edu.ntnu.stud.chaos;

import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.Transform2D;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a chaos game description.
 * It contains the transforms, minCoords and maxCoords of the chaos game.
 * Goal: act as a model for a chaos game description.
 */
public class ChaosGameDescription {
  /**
   * A list of the transforms used to generate the chaos game.
   */
  private final List<Transform2D> transforms;
  /**
   * The minimum coordinates of the chaos game.
   * Represents the lower left corner of the canvas.
   */
  private final Vector2D minCoords;
  /**
   * The maximum coordinates of the chaos game.
   * Represents the upper right corner of the canvas.
   */
  private final Vector2D maxCoords;

  /**
   * Constructor for the ChaosGameDescription class.
   *
   * @param transforms  the list of transforms used to generate the chaos game.
   * @param minCoords   the minimum coordinates of the chaos game.
   * @param maxCoords   the maximum coordinates of the chaos game.
   */
  public ChaosGameDescription(List<Transform2D> transforms, Vector2D minCoords, Vector2D maxCoords) {
    try {
      if (transforms == null || minCoords == null || maxCoords == null) {
        throw new IllegalArgumentException("The input cannot be null");
      }
      this.minCoords = new Vector2D(minCoords);
      this.maxCoords = new Vector2D(maxCoords);
      this.transforms = new ArrayList<>();
      this.transforms.addAll(transforms);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  /**
   * Get the list of transforms used to generate the chaos game.
   *
   * @return the list of transforms used to generate the chaos game.
   */
  public List<Transform2D> getTransforms() {
    return transforms;
  }

  /**
   * Get the minimum coordinates of the chaos game.
   *
   * @return the minimum coordinates of the chaos game.
   */
  public Vector2D getMinCoords() {
    return new Vector2D(minCoords);
  }

  /**
   * Get the maximum coordinates of the chaos game.
   *
   * @return the maximum coordinates of the chaos game.
   */
  public Vector2D getMaxCoords() {
    return new Vector2D(maxCoords);
  }

  public Class<? extends Transform2D> getTransformationType() {
    return transforms.get(0).getClass();
  }
}
