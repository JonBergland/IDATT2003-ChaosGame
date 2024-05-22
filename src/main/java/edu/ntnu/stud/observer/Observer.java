package edu.ntnu.stud.observer;

import edu.ntnu.stud.utils.ButtonEnum;

/**
 * Interface for the observer role in the observer pattern.
 * Implementing classes provide an update method that is called
 * when the observable changes and notifies the observers.
 * Used in combination with the Subject interface.
 */
public interface Observer {

  /**
   * This method is called when the observable changes and notifies the observers.
   *
   * @param buttonEnum  an enum representing the button pressed
   * @param string      a string representing the details of the button pressed
   */
  void update(ButtonEnum buttonEnum, String string);
}
