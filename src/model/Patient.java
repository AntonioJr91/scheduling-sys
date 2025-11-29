package model;

import java.time.LocalDate;

public final class Patient extends Person {

  public Patient(String name, String email, LocalDate birthday) {
    super(name, email, birthday);
  }
}
