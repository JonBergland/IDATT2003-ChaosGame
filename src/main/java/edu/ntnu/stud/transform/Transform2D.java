package edu.ntnu.stud.transform;

import edu.ntnu.stud.math.Vector2D;

/**
 * Represents a 2D transformation operation.
 * Implementing classes provide methods to transform 2D vectors.
 */
interface Transform2D {
  /**
   * This method takes a vector in as parameter and transforms it.
   *
   * @param point The vector taken in as parameter
   * @return The transformed vector
   */
  Vector2D transform(Vector2D point);

}

