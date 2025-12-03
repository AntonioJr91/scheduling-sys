package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    return "Doctor {\n" +
        "  id=" + getId() + ",\n" +
        "  name='" + getName() + "',\n" +
        "  email='" + getEmail() + "',\n" +
        "  birthday=" + (getBirthday() != null ? getBirthday().format(formatter) : null) + ",\n" +
        "  specialty=" + specialty + "\n" +
        "}";
  }

  private void validateSpecialty(Specialty specialty) {
    DomainException.when(specialty == null, "Specialty is required");
  }
}
