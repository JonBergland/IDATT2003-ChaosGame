package edu.ntnu.stud.component;

import static javafx.stage.Screen.getPrimary;

import edu.ntnu.stud.chaos.ChaosGame;
import edu.ntnu.stud.observer.Observer;
import edu.ntnu.stud.utils.ButtonEnum;
import edu.ntnu.stud.view.View;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The CanvasView class represents a view component
 * for displaying a fractal on a canvas.
 */
public class CanvasView extends View implements Observer {
  /** The main container for the canvas and other UI elements. */
  protected StackPane stackPane;

  /** The canvas where the fractal is drawn. */
  private Canvas fractalCanvas;

  /** The graphics context used for drawing on the canvas. */
  private GraphicsContext gc;

  /** The ChaosGame associated with the canvas view. */
  private final ChaosGame chaosGame;

  /** The color used for drawing the fractal. */
  private Color fractalColor = Color.BLUE;

  /**
   * Constructs a new CanvasView object and initializes
   * the canvas and graphics context.
   */
  public CanvasView(ChaosGame chaosGame) {
    if (chaosGame == null) {
      throw new IllegalArgumentException("ChaosGame cannot be null");
    }
    this.chaosGame = chaosGame;
    setup();
  }

  /**
   * Returns the main pane containing the canvas and other UI elements.
   *
   * @return the main pane
   */
  @Override
  public Pane getPane() {
    return stackPane;
  }

  /**
   * Sets up the initial state of the view.
   */
  @Override
  public void setup() {
    stackPane = new StackPane();
    stackPane.setPadding(new Insets(10, 10, 10, 10));
    fractalCanvas = new Canvas(getPrimary().getVisualBounds().getWidth() * 0.04 * 16,
        getPrimary().getVisualBounds().getHeight() * 0.04 * 16);
    gc = fractalCanvas.getGraphicsContext2D();
  }

  /**
   * Resets the main pane by clearing all its children.
   */
  @Override
  public void resetPane() {
    stackPane.getChildren().clear();
  }

  /**
   * Renders the fractal on the canvas using the current chaos canvas data.
   * It calculates the dimensions of each cell, determines the maximum pixel value,
   * iterates over each cell, calculates the color, and fills the corresponding rectangle
   * on the canvas. Finally, it adds the rendered fractal canvas to the main stack pane for display.
   */
  @Override
  public void render() {
    clearCanvas();

    int[][] canvasArray = chaosGame.getCanvas().getCanvasArray();

    double cellWidth = fractalCanvas.getWidth() / canvasArray.length;
    double cellHeight = fractalCanvas.getHeight() / canvasArray[0].length;

    int maxValue = Arrays.stream(canvasArray)
        .flatMapToInt(Arrays::stream)
        .max()
        .orElse(1);

    Color backgroundColor = Color.hsb(0, 0, 1);
    gc.setFill(backgroundColor);
    gc.fillRect(0, 0, fractalCanvas.getWidth(), fractalCanvas.getHeight());

    for (int i = 0; i < canvasArray.length; i++) {
      for (int j = 0; j < canvasArray[i].length; j++) {
        if (canvasArray[i][j] >= 1) {
          double colorValue = (double) canvasArray[i][j] / maxValue;
          Color color = fractalColor.deriveColor(0, 1, colorValue, 1);

          gc.setFill(color);
          gc.fillRect(j * cellHeight, i * cellWidth, cellWidth, cellHeight);
        }
      }
    }

    stackPane.getChildren().add(centerBox());
  }

  /**
   * Updates the color used for drawing the fractal.
   *
   * @param color the new color to use for the fractal
   */
  public void updateColor(Color color) {
    this.fractalColor = color;
  }

  /**
   * Update the canvas size and create a new canvas with the new size.
   *
   * @param width  The new width of the canvas.
   * @param height The new height of the canvas.
   */
  public void updateCanvasSize(int width, int height) {
    this.fractalCanvas = new Canvas(width, height);
    this.gc = fractalCanvas.getGraphicsContext2D();
  }

  /**
   * Creates a VBox containing the fractal canvas centered within its bounds.
   *
   * @return a VBox containing the fractal canvas
   */
  private VBox centerBox() {
    VBox centerVbox = new VBox();
    centerVbox.setSpacing(50);
    centerVbox.setMaxHeight(fractalCanvas.getHeight());
    centerVbox.setMaxWidth(fractalCanvas.getWidth());

    centerVbox.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, null, new BorderWidths(2))));

    centerVbox.getChildren().add(fractalCanvas);

    return centerVbox;
  }

  /**
   * Clears the canvas by filling it with a transparent color.
   */
  private void clearCanvas() {
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
  }

  /**
   * Update method called when the observer is notified of a change.
   *
   * @param buttonEnum the button enumeration representing the change.
   * @param string     the associated string information.
   */
  @Override
  public void update(ButtonEnum buttonEnum, String string) {
    if (buttonEnum == ButtonEnum.CANVAS_CHANGE) {
      this.fractalCanvas = new Canvas(
          chaosGame.getCanvas().getWidth(), chaosGame.getCanvas().getHeight());
      this.gc = fractalCanvas.getGraphicsContext2D();
    } else {
      throw new IllegalArgumentException("ButtonEnum not found");
    }
  }
}
