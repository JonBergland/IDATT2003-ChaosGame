package edu.ntnu.stud.view;

import edu.ntnu.stud.observer.Observer;
import edu.ntnu.stud.observer.Subject;
import edu.ntnu.stud.utils.ButtonEnum;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 * The base class for all views in the application.
 * Views are responsible for displaying the user interface and handling user interactions.
 * Implements the {@link Subject} interface to allow for observers to be notified of button events.
 */
public abstract class View implements Subject {

  /** A mapping of button enums to lists of observers. */
  private final EnumMap<ButtonEnum, List<Observer>> observersEnumMap =
      new EnumMap<>(ButtonEnum.class);

  /**
   * Gets the pane associated with the view.
   *
   * @return the JavaFX pane associated with the view.
   */
  public abstract Pane getPane();

  /**
   * Sets up the initial layout and components of the view.
   */
  public abstract void setup();

  /**
   * Resets the view's pane.
   */
  public abstract void resetPane();

  /**
   * Renders the view, updating its display.
   */
  public abstract void render();

  /**
   * Adds an observer for a specific button event.
   *
   * @param buttonEnum the enum representing the button event.
   * @param observer   the observer to add.
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
   * Removes an observer for a specific button event.
   *
   * @param buttonEnum the enum representing the button event.
   * @param observer   the observer to remove.
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
   * Notifies observers of a button event.
   *
   * @param buttonEnum the button enum that was clicked.
   * @param buttonText the text of the button that was clicked.
   */
  protected void notifyObservers(ButtonEnum buttonEnum, String buttonText) {
    if (observersEnumMap.containsKey(buttonEnum)) {
      observersEnumMap.get(buttonEnum).forEach(observer -> observer.update(buttonEnum, buttonText));
    } else {
      throw new IllegalArgumentException("Button enum is not in observersEnumMap");
    }
  }
}