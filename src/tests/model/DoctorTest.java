package tests.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import enums.Specialty;
import model.Doctor;
import model.exception.DomainException;

class DoctorTest {

  @Test
  @DisplayName("Should create a doctor successfully with valid data")
  void shouldCreateDoctorSuccessfully() {
    Doctor doctor = new Doctor(
        "Dr. House",
        "house@example.com",
        LocalDate.of(1980, 1, 10),
        Specialty.CARDIOLOGIST);

    assertNotNull(doctor.getId());
    assertEquals("Dr. House", doctor.getName());
    assertEquals("house@example.com", doctor.getEmail());
    assertEquals(LocalDate.of(1980, 1, 10), doctor.getBirthday());
    assertEquals(Specialty.CARDIOLOGIST, doctor.getSpecialty());
  }

  @Test
  @DisplayName("Should throw error when specialty is null")
  void shouldThrowErrorForNullSpecialty() {
    assertThrows(DomainException.class, () -> {
      new Doctor(
          "Dr. Strange",
          "strange@example.com",
          LocalDate.of(1975, 3, 20),
          null);
    });
  }

  @Test
  @DisplayName("Should throw error when name is invalid")
  void shouldThrowErrorForInvalidName() {
    assertThrows(DomainException.class, () -> {
      new Doctor(
          "",
          "doctor@example.com",
          LocalDate.of(1985, 5, 15),
          Specialty.DERMATOLOGIST);
    });
  }

  @Test
  @DisplayName("Should throw error when email is invalid")
  void shouldThrowErrorForInvalidEmail() {
    assertThrows(DomainException.class, () -> {
      new Doctor(
          "John Doe",
          "invalid-email",
          LocalDate.of(1985, 5, 15),
          Specialty.GENERAL_PRACTITIONER);
    });
  }

  @Test
  @DisplayName("Should throw error for future birthday")
  void shouldThrowErrorForFutureBirthday() {
    assertThrows(DomainException.class, () -> {
      new Doctor(
          "John Doe",
          "john@example.com",
          LocalDate.now().plusDays(1),
          Specialty.CARDIOLOGIST);
    });
  }
}
