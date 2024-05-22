package edu.ntnu.stud.observer;

import edu.ntnu.stud.utils.ButtonEnum;

/**
 * Interface for the subject role in the observer pattern.
 * Implementing classes provide methods to add and remove Observers
 * to the class and to notify them.
 * Used in combination with the Observer interface.
 */
public interface Subject {
  /**
   * Adds an observer to the observable.
   *
   * @param observer the observer to be added
   */
  void addObserver(ButtonEnum buttonEnum, Observer observer);

  /**
   * Removes an observer from the observable.
   *
   * @param observer the observer to be removed
   */
  void removeObserver(ButtonEnum buttonEnum, Observer observer);
}
