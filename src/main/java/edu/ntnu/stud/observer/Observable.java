package edu.ntnu.stud.observer;

/**
 * Interface for the observable role in the observer pattern.
 * Implementing classes provide methods to add and remove Observers
 * to the class and to notify them.
 * Used in combination with the Observer interface.
 */
public interface Observable {
  /**
   * Adds an observer to the observable.
   *
   * @param observer the observer to be added
   */
  void addObserver(Observer observer);

  /**
   * Removes an observer from the observable.
   *
   * @param observer the observer to be removed
   */
  void removeObserver(Observer observer);

  /**
   * Notifies all observers listening to this observable.
   */
  void notifyObservers();
}
