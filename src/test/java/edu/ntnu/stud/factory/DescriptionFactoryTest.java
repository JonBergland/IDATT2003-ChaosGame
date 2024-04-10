package edu.ntnu.stud.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionFactoryTest {
  DescriptionFactory descriptionFactory;

  @BeforeEach
  void setUp() {
    descriptionFactory = new DescriptionFactory();
  }

  @Test
  void getSierpinskiDescription() {
    assertEquals(SierpinskiDescription.class, descriptionFactory.get("Sierpinski").getClass());
  }

  @Test
  void getBarnsleyDescription() {
    assertEquals(BarnsleyDescription.class, descriptionFactory.get("Barnsley").getClass());
  }

  @Test
  void getJuliaDescription() {
    assertEquals(JuliaDescription.class, descriptionFactory.get("Julia").getClass());
  }

  @Test
  void getInvalidDescription() {
    assertThrows(IllegalArgumentException.class, () -> descriptionFactory.get("Invalid"));
  }
}