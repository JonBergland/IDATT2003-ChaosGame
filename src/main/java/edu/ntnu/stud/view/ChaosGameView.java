package edu.ntnu.stud.view;

import static javafx.stage.Screen.getPrimary;

import edu.ntnu.stud.component.CanvasView;
import edu.ntnu.stud.component.ParameterInputView;
import edu.ntnu.stud.utils.ButtonEnum;
import edu.ntnu.stud.utils.FillerPane;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * The ChaosGameView class represents the view for the chaos game.
 * It contains the layout setup for the chaos game view, including parameter inputs and canvas.
 */
public class ChaosGameView extends View {

  /** The main layout for the chaos game view. */
  protected BorderPane borderPane;

  /** The parameter input view for the chaos game. */
  private final ParameterInputView parameterInputView;

  /** The canvas view for the chaos game. */
  private final CanvasView canvasView;

  /** The canvas ratio for the chaos game view. */
  private static final double CANVAS_RATIO =
      getPrimary().getVisualBounds().getWidth() / getPrimary().getVisualBounds().getHeight();

  /**
   * Constructs a ChaosGameView with the specified parameter input view and canvas view.
   *
   * @param parameterInputView the parameter input view
   * @param canvasView the canvas view
   */
  public ChaosGameView(ParameterInputView parameterInputView, CanvasView canvasView) {
    if (parameterInputView == null || canvasView == null) {
      throw new IllegalArgumentException("ParameterInputView and CanvasView cannot be null");
    }
    borderPane = new BorderPane();

    this.parameterInputView = parameterInputView;
    this.canvasView = canvasView;
    setup();
  }

  /**
   * Retrieves the main pane of the chaos game view.
   *
   * @return the main pane of the chaos game view
   */
  @Override
  public Pane getPane() {
    return this.borderPane;
  }

  /**
   * Sets up the layout of the chaos game view, including parameter inputs and canvas.
   */
  @Override
  public void setup() {
    borderPane.setLeft(parameterBox());
    borderPane.setCenter(canvasView.getPane());
    borderPane.getStyleClass().add("background");

    // Add listeners for width and height changes to adjust canvas size
    borderPane.widthProperty().addListener((observable, oldValue, newValue) ->
        notifyObservers(ButtonEnum.SCREEN_CHANGE, newValue.toString()));

    borderPane.heightProperty().addListener((observable, oldValue, newValue) -> {
      double ratioValue = Math.floor((newValue.doubleValue() + 77) * CANVAS_RATIO);
      notifyObservers(ButtonEnum.SCREEN_CHANGE, String.valueOf(ratioValue));
    });
  }

  /**
   * Clears the main pane of the chaos game view.
   */
  @Override
  public void resetPane() {
    borderPane.getChildren().clear();
  }

  /**
   * Renders the chaos game view by setting up the layout again.
   */
  @Override
  public void render() {
    parameterInputView.resetPane();
    parameterInputView.render();

    borderPane.setLeft(parameterBox());
    borderPane.setCenter(canvasView.getPane());
  }

  /**
   * Creates a horizontal box for parameter inputs.
   *
   * @return a horizontal box containing parameter inputs
   */
  private HBox parameterBox() {
    HBox parameterBox = new HBox();
    parameterBox.setPadding(new Insets(10, 50, 10, 50));
    parameterBox.getChildren().addAll(new FillerPane(), parameterInputView.getPane());
    parameterBox.setMaxHeight(getPrimary().getVisualBounds().getHeight() * 0.4);
    return parameterBox;
  }
}

