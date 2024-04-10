package edu.ntnu.stud.observer;

/**
 * Interface for the observer role in the observer pattern.
 * Implementing classes provide an update method that is called
 * when the observable changes and notifies the observers.
 * Used in combination with the Observable interface.
 */
public interface Observer {
  /**
   * This method is called when the observable changes and notifies the observers.
   */
  void update();
}
