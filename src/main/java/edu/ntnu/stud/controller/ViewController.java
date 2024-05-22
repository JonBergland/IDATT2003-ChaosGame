package edu.ntnu.stud.controller;

import static javafx.stage.Screen.getPrimary;

import edu.ntnu.stud.component.CloseProgramBox;
import edu.ntnu.stud.component.InfoBox;
import edu.ntnu.stud.component.NavBar;
import edu.ntnu.stud.observer.Observer;
import edu.ntnu.stud.utils.ButtonEnum;
import edu.ntnu.stud.view.View;
import java.util.EnumMap;
import java.util.Objects;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is responsible for managing and setting the views in the application.
 * It is responsible for the root pane of the application and setting of the navigation bar.
 * It also listens for changes in the views and changes the view accordingly.
 */
public class ViewController implements Observer {

  /**
   * The primary stage for this application.
   * Represents the main window of the application.
   */
  private final Stage window = new Stage();

  /**
   * Enum representing the different pages in the application.
   */
  public enum PageEnum {
    HOMEPAGE,
    CHAOSGAMEPAGE
  }

  /** The EnumMap containing the views in the application. */
  private final EnumMap<PageEnum, View> views;

  /** The root pane of the application. Used to create a Scene */
  private final BorderPane root;

  /**
   * Constructs a new ViewController with a given stage.
   *
   * @param stage The stage for the application.
   */
  public ViewController(Stage stage) {
    views = new EnumMap<>(PageEnum.class);
    root = new BorderPane();
    setupNavBar();
    Scene scene = new Scene(root, getPrimary().getVisualBounds().getWidth(),
        getPrimary().getVisualBounds().getHeight() - 10);
    scene.getStylesheets().add(Objects.requireNonNull(getClass()
        .getResource("/style/components.css")).toExternalForm());
    stage.setScene(scene);
  }

  /**
   * Sets up the root of the application.
   * Creates a NavBar instance, adds it as an observer, and sets it at the top of the root pane.
   */
  private void setupNavBar() {
    NavBar navBar = new NavBar();
    navBar.addObserver(ButtonEnum.SWITCH_PAGE, this);
    VBox top = new VBox();
    top.getChildren().add(navBar);
    root.setTop(top);
  }

  /**
   * Gets a view from the view manager.
   *
   * @param nameEnum The enum representing which view to get.
   * @return The view.
   */
  public View getView(PageEnum nameEnum) {
    return views.get(nameEnum);
  }

  /**
   * Adds a view to the view manager.
   *
   * @param name The name of the view to add.
   * @param view The view to add.
   */
  public void addView(PageEnum name, View view) {
    if (name == null || view == null) {
      throw new IllegalArgumentException("Name or view cannot be null");
    } else if (views.containsKey(name)) {
      throw new IllegalArgumentException("View already exists");
    }
    views.put(name, view);
  }

  /**
   * Removes a view from the view manager.
   *
   * @param name The name of the view to remove.
   */
  public void removeView(PageEnum name) {
    views.remove(name);
  }

  /**
   * Sets the view of the application to a given page.
   *
   * @param name The name of the page to set.
   */
  public void setView(PageEnum name) {
    root.setCenter(views.get(name).getPane());
  }

  /**
   * Initiates the process to close the program. This method creates an instance of
   * the CloseProgramBox class and displays a dialog box to confirm if the user
   * wants to close the program. If the user confirms, the program's window is
   * closed and the application exits. If "No" or closing the alert box without
   * making a choice, the program continues running.
   */
  public void closeProgram() {
    CloseProgramBox closeProgramBox = new CloseProgramBox();
    boolean answer = closeProgramBox.disPlay("Close Program");
    if (answer) {
      window.close();
      System.exit(0);
    }
  }

  /**
   * Displays an information dialog box to the user. This method creates an
   * instance of the InfoBox class and displays a dialog box. If the user
   * press the "OK" button (returns true), the program's window is closed.
   */
  public void infoBox() {
    InfoBox infoBox = new InfoBox();
    boolean answer = infoBox.disPlay("Information Box");
    if (answer) {
      window.close();
    }
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
    switch (buttonEnum) {
      case START_GAME:
        setView(PageEnum.CHAOSGAMEPAGE);
        break;
      case SWITCH_PAGE:
        switch (string) {
          case "Home page" -> setView(PageEnum.HOMEPAGE);
          case "Exit page" -> closeProgram();
          case "Info page" -> infoBox();
          default -> throw new IllegalArgumentException("Invalid page");
        }
        break;
      default:
        break;
    }
  }
}
