package model;

import java.time.LocalDate;

import enums.Specialty;
import model.exception.DomainException;

public final class Doctor extends Person {

  private final Specialty specialty;

  public Doctor(String name, String email, LocalDate birthday, Specialty specialty) {
    super(name, email, birthday);
    validateSpecialty(specialty);
    this.specialty = specialty;
  }

  public Specialty getSpecialty() {
    return specialty;
  }

  @Override
  public String toString() {
    return "Doctor [id=" + getId()
        + ", name=" + getName()
        + ", email=" + getEmail()
        + ", birthday=" + getBirthday()
        + ", specialty=" + specialty
        + "]";
  }

  private void validateSpecialty(Specialty specialty) {
    DomainException.when(specialty == null, "Specialty is required");
  }
}
