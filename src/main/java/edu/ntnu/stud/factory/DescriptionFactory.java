package edu.ntnu.stud.factory;

import edu.ntnu.stud.chaos.ChaosGameDescription;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.transform.Transform2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DescriptionFactory {
  public static ChaosGameDescription get(String description) {
    List<Transform2D> transformsPlaceHolder = new ArrayList<>();
    Vector2D placeHolderVector = new Vector2D(0, 0);

    return switch (description) { //TODO Add Description types for the fractals
      case "Sierpinski" -> new SierpinskiDescription(transformsPlaceHolder, placeHolderVector, placeHolderVector);
      case "Barnsley" -> new BarnsleyDescription(transformsPlaceHolder, placeHolderVector, placeHolderVector);
      case "Julia" -> new JuliaDescription(transformsPlaceHolder, placeHolderVector, placeHolderVector);
      default -> throw new IllegalArgumentException("Invalid description type");
    };
  }
}
