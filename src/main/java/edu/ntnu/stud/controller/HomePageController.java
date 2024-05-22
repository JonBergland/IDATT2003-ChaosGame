package edu.ntnu.stud.controller;

import edu.ntnu.stud.observer.Observer;
import edu.ntnu.stud.utils.ButtonEnum;
import edu.ntnu.stud.utils.FractalType;
import edu.ntnu.stud.view.HomePageView;
import java.util.Objects;


/**
 * The controller for the home page.
 * This class handles the logic for the home page, managing interactions and updating the view.
 */
public class HomePageController implements Observer {

  /** The view for the home page. */
  private final HomePageView view;

  /**
   * The constructor for the HomePageController.
   * Initializes the home page view and sets up the observer.
   */
  public HomePageController(HomePageView view) {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.view = view;
    this.view.setup();
  }


  /**
   * This method is called when the observable changes and notifies the observers.
   * It handles updates based on the button action and provided string.
   *
   * @param buttonEnum the type of button action that triggered the update
   * @param string     additional string parameter associated with the button action
   */
  @Override
  public void update(ButtonEnum buttonEnum, String string) {
    if (Objects.requireNonNull(buttonEnum) == ButtonEnum.GAME_TYPE) {
      switch (string) {
        case "Sierpinski" -> setGameType(FractalType.SIERPINSKI);
        case "Barnsley" -> setGameType(FractalType.BARNSLEY);
        case "Julia" -> setGameType(FractalType.JULIA);
        case "Mandelbrot" -> setGameType(FractalType.MANDELBROT);
        default -> throw new IllegalArgumentException("Invalid game type");
      }
    }
  }

  /**
   * Sets the game type and updates the view with the button that is checked.
   *
   * @param gameType the type of chaos game
   */
  private void setGameType(String gameType) {
    view.setChosenGameType(gameType);
    view.render();
  }
}
