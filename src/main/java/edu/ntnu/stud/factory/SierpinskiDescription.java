package edu.ntnu.stud.factory;

import edu.ntnu.stud.chaos.ChaosGameDescription;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.Transform2D;

import java.util.List;

public class SierpinskiDescription extends ChaosGameDescription {
  /**
   * Constructor for the ChaosGameDescription class.
   *
   * @param transforms the list of transforms used to generate the chaos game.
   * @param minCoords  the minimum coordinates of the chaos game.
   * @param maxCoords  the maximum coordinates of the chaos game.
   */
  public SierpinskiDescription(List<Transform2D> transforms, Vector2D minCoords, Vector2D maxCoords) {
    super(transforms, minCoords, maxCoords); // TODO - implement SierpinskiDescription
  }
}
