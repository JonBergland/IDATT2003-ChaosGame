package edu.ntnu.stud.component;

import edu.ntnu.stud.observer.Observer;
import edu.ntnu.stud.observer.Subject;
import edu.ntnu.stud.utils.ButtonEnum;
import edu.ntnu.stud.utils.FillerPane;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

/**
 * A class that extends ToolBar and creates a navigation bar for the chaos-game application.
 * This class is used to create a home button, a logo, and a title for the application.
 * Implements the {@link Subject} interface to allow for observers to be notified of button events.
 */
public class NavBar extends ToolBar implements Subject {

  /** EnumMap to store observers corresponding to button enums. */
  private final EnumMap<ButtonEnum, List<Observer>> observersEnumMap =
      new EnumMap<>(ButtonEnum.class);

  /**
   * Constructor for NavBar.
   * It creates buttons for navigation, including home, info,
   * and exit buttons, and adds them to the navigation bar.
   */
  public NavBar() {
    // Create home button
    SVGPath homeIcon = createHomeIcon();
    double iconSize = 1.3;
    homeIcon.setScaleX(iconSize);
    homeIcon.setScaleY(iconSize);
    Hyperlink homeButton = createButton(homeIcon,
        () -> notifyObservers(ButtonEnum.SWITCH_PAGE, "Home page"));
    double homeButtonPadding = 10;
    homeButton.setPadding(new Insets(
        homeButtonPadding, homeButtonPadding, homeButtonPadding, homeButtonPadding));

    // Create info button
    SVGPath infoIcon = createInfoIcon();
    infoIcon.setScaleX(iconSize * 0.55);
    infoIcon.setScaleY(iconSize * 0.55);
    Hyperlink infoButton = createButton(infoIcon,
        () -> notifyObservers(ButtonEnum.SWITCH_PAGE, "Info page"));
    infoButton.setPadding(new Insets(
        homeButtonPadding / 4, homeButtonPadding / 2,
        homeButtonPadding / 4, homeButtonPadding / 2));

    // Create exit button
    SVGPath exitIcon = createExitIcon();
    exitIcon.setScaleX(iconSize * 0.5);
    exitIcon.setScaleY(iconSize * 0.5);
    Hyperlink exitButton = createButton(exitIcon,
        () -> notifyObservers(ButtonEnum.SWITCH_PAGE, "Exit page"));
    exitButton.setPadding(new Insets(
        homeButtonPadding / 4, homeButtonPadding / 2,
        homeButtonPadding / 4, homeButtonPadding / 2));

    Pane iconFillerPane = new Pane();
    iconFillerPane.setPrefWidth(homeIcon.getBoundsInLocal().getWidth() + homeButtonPadding * 6);

    HBox banner = createBanner();

    this.getItems().addAll(homeButton, iconFillerPane,
        new FillerPane(), banner, new FillerPane(),
        infoButton, exitButton);
    this.getStyleClass().add("navbar-background");
  }

  /**
   * Create a button with a specified icon and action.
   *
   * @param icon      The icon for the button.
   * @param action    The action to be performed when the button is clicked.
   * @return          The created button.
   */
  private Hyperlink createButton(SVGPath icon, Runnable action) {
    Hyperlink button = new Hyperlink();
    button.setGraphic(icon);
    button.setOnAction(event -> action.run());
    return button;
  }

  /**
   * Create a home icon for the home button.
   * This method creates a SVGPath object with a rectangle and a triangle to represent a home icon.
   *
   * @return The created home icon.
   */
  private SVGPath createHomeIcon() {
    SVGPath icon = new SVGPath();
    String rectangle = "M 5 10 L 5 20 L 8 20 L 8 14 L 17 14 L 17 20 L 20 20 L 20 10 Z";
    String triangle = "M 2 8.5 L 23 8.5 L 12.5 2 Z";
    icon.setContent(rectangle + " " + triangle);
    icon.setStroke(Color.BLACK);
    icon.setStrokeWidth(2.0);
    icon.setStrokeLineJoin(StrokeLineJoin.ROUND);
    icon.setStrokeType(StrokeType.OUTSIDE);
    return icon;
  }

  /**
   * Create an info icon for the home button.
   * This method creates a SVGPath object with a circle and a line to represent an info icon.
   * The SVG path data is taken from
   * <a href="https://www.svgrepo.com/svg/2428/info-button">SVGRepo</a>
   *
   * @return The created info icon.
   */
  private SVGPath createInfoIcon() {
    SVGPath icon = new SVGPath();
    icon.setContent("M39.264,6.736c-8.982-8.981-23.545-8.982-32.528,0c-8.982,8.982-8.981,23.545,"
        + "0,32.528c8.982,8.98,23.545,8.981,32.528,0"
        + "C48.245,30.281,48.244,15.719,39.264,6.736z M25.999,33c0,1.657-1.343,3-3,"
        + "3s-3-1.343-3-3V21c0-1.657,1.343-3,3-3s3,1.343,3,3V33z"
        + "M22.946,15.872c-1.728,0-2.88-1.224-2.844-2.735c-0.036-1.584,1.116-2.771,"
        + "2.879-2.771c1.764,0,2.88,1.188,2.917,2.771"
        + "C25.897,14.648,24.746,15.872,22.946,15.872z");
    icon.setStroke(Color.BLACK);
    icon.setStrokeWidth(2.0);
    icon.setStrokeLineJoin(StrokeLineJoin.ROUND);
    return icon;
  }

  /**
   * Create an exit icon for the home button.
   * This method creates a SVGPath object with a cross to represent an exit icon.
   * The SVG path data is taken from
   * <a href="https://www.svgrepo.com/svg/20605/close-cross">SVGRepo</a>
   *
   * @return The created exit icon.
   */
  private SVGPath createExitIcon() {
    SVGPath icon = new SVGPath();
    icon.setContent("M27.948,20.878L40.291,8.536c1.953-1.953,1.953-5.119,"
        + "0-7.071c-1.951-1.952-5.119-1.952-7.07,0L20.878,13.809L8.535,1.465"
        + "c-1.951-1.952-5.119-1.952-7.07,0c-1.953,1.953-1.953,5.119,0,7.071l12.342,"
        + "12.342L1.465,33.22c-1.953,1.953-1.953,5.119,0,7.071"
        + "C2.44,41.268,3.721,41.755,5,41.755c1.278,0,2.56-0.487,3.535-1.464l12.343-12.342l12.343,"
        + "12.343c0.976,0.977,2.256,1.464,3.535,1.464s2.56-0.487,3.535-1.464c1.953-1.953,"
        + "1.953-5.119,0-7.071L27.948,20.878z");
    icon.setStroke(Color.BLACK);
    icon.setStrokeWidth(2.0);
    icon.setStrokeLineJoin(StrokeLineJoin.ROUND);
    icon.setStrokeType(StrokeType.OUTSIDE);
    return icon;
  }

  /**
   * Create a logo for the application.
   * This method creates a logo with the text "Chaos Game" and an image of the logo.
   *
   * @return The created logo as an HBox containing the text "Chaos Game" and an image.
   */
  private HBox createBanner() {
    Image image = new Image(Objects.requireNonNull(
        getClass().getResourceAsStream("/image/chaosGame_logo.jpg")));
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(50);
    imageView.setFitWidth(50);

    StackPane stackPane = new StackPane(imageView);
    stackPane.setPadding(new Insets(10, 0, 0, 0));

    Text text = new Text("Chaos Game");
    text.getStyleClass().add("title");
    StackPane titlePane = new StackPane();
    titlePane.getChildren().addAll(text);
    titlePane.setPadding(new Insets(10));

    return new HBox(titlePane, imageView);
  }

  /**
   * Adds an observer to the observable.
   *
   * @param buttonEnum the button enum corresponding to the observer
   * @param observer the observer to be added
   */
  @Override
  public void addObserver(ButtonEnum buttonEnum, Observer observer) {
    if (observersEnumMap.containsKey(buttonEnum)) {
      observersEnumMap.get(buttonEnum).add(observer);
    } else {
      List<Observer> observerList = new ArrayList<>();
      observerList.add(observer);
      observersEnumMap.put(buttonEnum, observerList);
    }
  }

  /**
   * Removes an observer from the observable.
   *
   * @param buttonEnum the button enum corresponding to the observer
   * @param observer   the observer to be removed
   * @throws IllegalArgumentException if the observer is not found
   */
  @Override
  public void removeObserver(ButtonEnum buttonEnum, Observer observer) {
    if (observersEnumMap.containsKey(buttonEnum)) {
      observersEnumMap.get(buttonEnum).remove(observer);
    } else {
      throw new IllegalArgumentException("Observer not found");
    }
  }

  /**
   * Notifies all observers listening to this observable.
   *
   * @param buttonEnum the button enum corresponding to the observers
   * @param string     the message to be sent to the observers
   * @throws IllegalArgumentException if the button enum is not found
   */
  public void notifyObservers(ButtonEnum buttonEnum, String string) {
    if (observersEnumMap.containsKey(buttonEnum)) {
      observersEnumMap.get(buttonEnum).forEach(observer -> observer.update(buttonEnum, string));
    } else {
      throw new IllegalArgumentException("Button enum not found");
    }
  }
}
