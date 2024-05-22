package edu.ntnu.stud;

import static javafx.stage.Screen.getPrimary;

import edu.ntnu.stud.chaos.ChaosGame;
import edu.ntnu.stud.component.CanvasView;
import edu.ntnu.stud.component.ParameterInputView;
import edu.ntnu.stud.controller.ChaosGameController;
import edu.ntnu.stud.controller.HomePageController;
import edu.ntnu.stud.controller.ViewController;
import edu.ntnu.stud.factory.ChaosGameDescriptionFactory;
import edu.ntnu.stud.utils.ButtonEnum;
import edu.ntnu.stud.utils.FractalType;
import edu.ntnu.stud.view.ChaosGameView;
import edu.ntnu.stud.view.HomePageView;
import java.util.Objects;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main class of the Chaos Game application.
 * Responsible for initializing the JavaFX application and setting up the primary stage.
 */
public class App extends Application {

  /** The primary stage of the application. */
  protected static Stage primaryStage;

  /** The width of the screen. */
  private static final double SCREEN_WIDTH = getPrimary().getVisualBounds().getWidth();

  /**
   * Starts the JavaFX application.
   *
   * @param primaryStage the primary stage of the application.
   */
  @Override
  public void start(Stage primaryStage) {
    App.primaryStage = primaryStage;
    primaryStage.setTitle("Chaos Game");

    // Setting application icon
    Image image = new Image(Objects.requireNonNull(getClass()
        .getResourceAsStream("/image/chaosGame_logo.jpg")));
    primaryStage.getIcons().add(image);

    // Creating controllers and setting up views
    HomePageView homePageView = new HomePageView();
    HomePageController homePageController = new HomePageController(homePageView);
    homePageView.addObserver(ButtonEnum.GAME_TYPE, homePageController);

    ChaosGame chaosGame = new ChaosGame(ChaosGameDescriptionFactory
        .getDescription(FractalType.SIERPINSKI), 0, 0);
    ParameterInputView parameterInputView = new ParameterInputView(chaosGame);
    CanvasView canvasView = new CanvasView(chaosGame);

    chaosGame.addObserver(ButtonEnum.TRANSFORM, parameterInputView);
    chaosGame.addObserver(ButtonEnum.COORDS, parameterInputView);
    chaosGame.addObserver(ButtonEnum.GAME_NAME, parameterInputView);
    chaosGame.addObserver(ButtonEnum.CANVAS_CHANGE, canvasView);

    ChaosGameView chaosGameView = new ChaosGameView(parameterInputView, canvasView);
    ChaosGameController chaosGameController = new
        ChaosGameController(chaosGameView, canvasView, chaosGame);
    chaosGameView.addObserver(ButtonEnum.SCREEN_CHANGE, chaosGameController);

    canvasView.addObserver(ButtonEnum.CANVAS_CHANGE, chaosGameController);

    parameterInputView.addObserver(ButtonEnum.PARAMETER_CHANGE, chaosGameController);
    parameterInputView.addObserver(ButtonEnum.FRACTAL, chaosGameController);
    parameterInputView.addObserver(ButtonEnum.COORDS, chaosGameController);
    parameterInputView.addObserver(ButtonEnum.STEPS, chaosGameController);
    parameterInputView.addObserver(ButtonEnum.TRANSFORM, chaosGameController);
    parameterInputView.addObserver(ButtonEnum.SAVEFILE, chaosGameController);
    parameterInputView.addObserver(ButtonEnum.READFILE, chaosGameController);
    parameterInputView.addObserver(ButtonEnum.COLORPICKER, chaosGameController);

    ViewController viewController = new ViewController(primaryStage);

    viewController.addView(ViewController.PageEnum.HOMEPAGE, homePageView);
    viewController.addView(ViewController.PageEnum.CHAOSGAMEPAGE, chaosGameView);
    homePageView.addObserver(ButtonEnum.START_GAME, viewController);

    homePageView.addObserver(ButtonEnum.START_GAME, chaosGameController);

    // Setting initial view
    viewController.setView(ViewController.PageEnum.HOMEPAGE);

    // Handling screen changes
    primaryStage.fullScreenProperty().addListener((observable, oldValue, newValue) ->
        chaosGameController.update(ButtonEnum.SCREEN_CHANGE, String.valueOf(SCREEN_WIDTH)));

    // Handling close event
    primaryStage.setOnCloseRequest(e -> {
      e.consume();
      viewController.closeProgram();
    });

    primaryStage.show();
  }

  /**
   * The main method. Launches the JavaFX application.
   *
   * @param args the command-line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
