package edu.ntnu.stud.controller;

import static javafx.stage.Screen.getPrimary;

import edu.ntnu.stud.chaos.ChaosGame;
import edu.ntnu.stud.chaos.ChaosGameDescription;
import edu.ntnu.stud.chaos.ChaosGameFileHandler;
import edu.ntnu.stud.component.CanvasView;
import edu.ntnu.stud.component.ParameterInputView;
import edu.ntnu.stud.factory.ChaosGameDescriptionFactory;
import edu.ntnu.stud.math.Complex;
import edu.ntnu.stud.math.Matrix2x2;
import edu.ntnu.stud.math.Vector2D;
import edu.ntnu.stud.observer.Observer;
import edu.ntnu.stud.transform.AffineTransform2D;
import edu.ntnu.stud.transform.JuliaTransform;
import edu.ntnu.stud.transform.Transform2D;
import edu.ntnu.stud.utils.ButtonEnum;
import edu.ntnu.stud.utils.FractalType;
import edu.ntnu.stud.view.ChaosGameView;
import edu.ntnu.stud.view.View;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 * The controller for the chaos game.
 * This class is responsible for handling the logic behind the chaos game.
 * It controls the {@link ChaosGameView}, {@link CanvasView} and {@link ParameterInputView}.
 */
public class ChaosGameController implements Observer {

  /** The view for the chaos game. */
  private final View view;

  /** The canvas view where the fractal is drawn. */
  private final CanvasView canvasView;

  /** The current instance of the chaos game. */
  private final ChaosGame currentGame;

  /** The size of the chaos game. */
  private int size;

  /** The number of steps for the chaos game. */
  private int steps;

  /** The type of fractal being generated. */
  private String fractalType;

  /** The original width of the screen. */
  private final double originalScreenSizeWidth;

  /** The file handler for saving and loading chaos game configurations. */
  private final ChaosGameFileHandler chaosGameFileHandler;

  /** The file chooser for selecting files to save or load. */
  private final FileChooser fileChooser;

  /** The error message for format exceptions. */
  private static final String FORMAT_EXCEPTION = "String cannot be converted to double: ";

  /**
   * Constructor for the ChaosGameController.
   */
  public ChaosGameController(View chaosGameView,
                             CanvasView canvasView, ChaosGame chaosGame) {
    if (chaosGameView == null || canvasView == null) {
      throw new IllegalArgumentException("View cannot be null");
    } else if (chaosGame == null) {
      throw new IllegalArgumentException("ChaosGame cannot be null");
    }

    this.currentGame = chaosGame;

    this.canvasView = canvasView;

    chaosGameFileHandler = new ChaosGameFileHandler();
    fileChooser = new FileChooser();

    originalScreenSizeWidth = getPrimary().getVisualBounds().getWidth();

    this.view = chaosGameView;

    view.setup();
  }

  /**
   * Updates the view based on the specified button action and additional string parameter.
   * This method is invoked when an observable notifies its observers of a change.
   *
   * @param buttonEnum the type of button action that triggered the update
   * @param string     additional string parameter associated with the button action
   */
  @Override
  public void update(ButtonEnum buttonEnum, String string) {
    switch (buttonEnum) {
      case START_GAME:
        startFirstGame(string);
        startChaosGame(ChaosGameDescriptionFactory.getDescription(fractalType));
        break;

      case PARAMETER_CHANGE:
        startChaosGame(currentGame.getDescription());
        break;

      case COORDS:
        try {
          List<Double> coordsValues = divide2DMatrixString(string);
          currentGame.updateMinAndMaxCoords(coordsValues.get(0), coordsValues.get(1),
              coordsValues.get(2), coordsValues.get(3));

          view.resetPane();
          view.render();
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(
              FORMAT_EXCEPTION + e.getMessage());
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Invalid coordinates: " + e.getMessage());
        }
        break;

      case STEPS:
        try {
          this.steps = (int) Double.parseDouble(string);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(
              FORMAT_EXCEPTION + e.getMessage());
        }
        break;

      case FRACTAL:
        this.fractalType = string.toLowerCase();
        currentGame.setChaosGameName(this.fractalType);
        currentGame.setDescription(ChaosGameDescriptionFactory.getDescription(this.fractalType));

        view.resetPane();
        view.render();
        break;

      case SCREEN_CHANGE:
        updateWindowSize(string);
        break;

      case TRANSFORM:
        interpretTransformString(string);
        break;

      case SAVEFILE:
        saveToFile();
        break;

      case READFILE:
        readFromFile();
        break;

      case COLORPICKER:
        updateFractalColor(string);
        break;

      default:
        throw new UnsupportedOperationException("ButtonEnum not supported: " + buttonEnum);
    }
  }

  /**
   * Start the first game with the given fractal type.
   * It will set the steps to 10000 and the width and height to 40% of the screen width.
   *
   * @param fractalType the type of fractal to start
   */
  private void startFirstGame(String fractalType) {
    this.steps = 10000;
    this.size = (int) (originalScreenSizeWidth * 0.4);

    if (FractalType.getFractalSet().contains(fractalType)) {
      currentGame.setChaosGameName(fractalType.substring(0, 1).toUpperCase()
                                   + fractalType.substring(1));
      this.fractalType = fractalType;
    } else {
      throw new IllegalArgumentException("Invalid game type");
    }
  }

  /**
   * Start the chaos game with the given description.
   *
   * @param chaosGameDescription the description of the chaos game to start
   */
  private void startChaosGame(ChaosGameDescription chaosGameDescription) {
    currentGame.setWidth(size);
    currentGame.setHeight(size);
    currentGame.setDescription(chaosGameDescription);

    if (fractalType.equalsIgnoreCase(FractalType.MANDELBROT)) {
      currentGame.runMandelbrotMethod();
    } else if (Objects.equals(chaosGameDescription.getTransformationType(), JuliaTransform.class)) {
      currentGame.runIterativeJuliaMethod();
    } else {
      currentGame.runSteps(steps);
    }
    //Update Canvas
    canvasView.resetPane();
    canvasView.render();

    //Update view
    view.resetPane();
    view.render();
  }

  /**
   * Update the window size based on the given string.
   *
   * @param numberString the string representing the new size
   */
  private void updateWindowSize(String numberString) {
    try {
      int newSize = (int) (Double.parseDouble(numberString) * 0.4);

      if (newSize == size) {
        return;
      }

      if (originalScreenSizeWidth * 0.2 <= newSize && newSize <= originalScreenSizeWidth * 0.4) {
        size = newSize;

        canvasView.resetPane();
        currentGame.setWidth(size);
        currentGame.setHeight(size);
        canvasView.updateCanvasSize(size, size);
        canvasView.render();

        view.resetPane();
        view.render();
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          FORMAT_EXCEPTION + e.getMessage());
    }
  }

  /**
   * Divide the string with coordinates into a list of double values.
   *
   * @param divideString               the string to divide
   * @param expectedLength             the expected length of the list
   * @return a list of double values extracted from the string
   * @throws IllegalArgumentException  if the string is not a valid coordinate string
   */
  private List<Double> divideString(String divideString, int expectedLength) {
    String[] coords = divideString.split(", ");
    if (coords.length != expectedLength) {
      throw new IllegalArgumentException(
          "Invalid number of values. Must be " + expectedLength + ".");
    }
    try {
      return Arrays.stream(coords)
        .map(Double::parseDouble)
        .toList();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          FORMAT_EXCEPTION + e.getMessage());
    }

  }

  /**
   * Divide the string with a 2x2 Matrix into 4 double values.
   *
   * @param matrixString              the string to divide
   * @return a list of double values representing the matrix
   * @throws IllegalArgumentException if the string is not a 2x2 matrix
   */
  private List<Double> divide2DMatrixString(String matrixString) throws IllegalArgumentException {
    String[] coords = matrixString.split("\\R");
    if (coords.length != 2) {
      throw new IllegalArgumentException("Invalid number of vectors. Must be 2.");
    }

    List<Double> firstLine = divideString(coords[0], 2);
    List<Double> secondLine = divideString(coords[1], 2);
    return List.of(firstLine.get(0), firstLine.get(1), secondLine.get(0), secondLine.get(1));
  }

  /**
   * Interpret the transform string and set the transforms in the current game.
   * The string should contain either a single complex number or a list of 2x2 matrices
   * with corresponding vectors. The values should be separated by a comma and a space.
   * The matrices should be separated by a newline.
   * Updates the current game with the new transforms.
   *
   * @param transformString            the string to interpret
   * @throws IllegalArgumentException  if the string is not a valid transform string
   */
  private void interpretTransformString(String transformString) throws IllegalArgumentException {
    try {
      List<String> transformStrings = List.of(transformString.split("\\R"));

      if (transformStrings.size() == 1) {
        if (Objects.equals(transformStrings.getFirst(), FractalType.MANDELBROT)) {
          JuliaTransform juliaTransform = new JuliaTransform(new Complex(0, 0), 1);
          currentGame.getDescription().setTransforms(List.of(juliaTransform));
        } else {
          List<Double> transformValues = divideString(transformStrings.getFirst(), 2);
          JuliaTransform juliaTransform = new JuliaTransform(
              new Complex(transformValues.get(0), transformValues.get(1)), 1);
          JuliaTransform juliaTransform2 = new JuliaTransform(
              new Complex(transformValues.get(0), transformValues.get(1)), -1);
          currentGame.getDescription().setTransforms(List.of(juliaTransform, juliaTransform2));
        }
      } else {
        List<Transform2D> transforms = new ArrayList<>();
        transformStrings.forEach(string -> {
          if (string.isEmpty()) {
            return;
          }
          List<Double> transformValues = divideString(string, 6);
          transforms.add(new AffineTransform2D(
              new Matrix2x2(transformValues.get(0), transformValues.get(1),
                  transformValues.get(2), transformValues.get(3)),
              new Vector2D(transformValues.get(4), transformValues.get(5))));
        });
        currentGame.getDescription().setTransforms(transforms);
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          FORMAT_EXCEPTION + e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid coordinates: " + e.getMessage());
    }
  }

  /**
   * Read the chaos game description from a file.
   */
  private void readFromFile() {

    File choosenFile = fileChooser.showOpenDialog(null);

    if (choosenFile != null) {
      try {
        ChaosGameDescription readDescription =
            chaosGameFileHandler.readFromFile(choosenFile.getAbsolutePath());
        ChaosGameDescriptionFactory.addNewFileDescription(choosenFile.getName(), readDescription);
        FractalType.addFractal(choosenFile.getName().toLowerCase());

        startChaosGame(readDescription);
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not read file: " + e.getMessage());
      }
    }
  }

  /**
   * Save the current chaos game description to a file.
   */
  private void saveToFile() {
    File choosenFile = fileChooser.showSaveDialog(null);

    if (choosenFile != null) {
      try {
        chaosGameFileHandler.writeToFile(choosenFile.getAbsolutePath(),
            currentGame.getDescription());
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not save file: " + e.getMessage());
      }
    }
  }

  /**
   * Update the color of the fractal based on the given string.
   *
   * @param colorString the string representing the new color
   */
  private void updateFractalColor(String colorString) {
    Color newColor = Color.web(colorString);
    canvasView.updateColor(newColor);
    canvasView.render();
  }
}
