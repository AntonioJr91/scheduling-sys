package tests.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Patient;
import model.exception.DomainException;

class PatientTest {

    @Test
    @DisplayName("Should create a patient successfully with valid data")
    void shouldCreatePatientSuccessfully() {
        Patient patient = new Patient("John Doe", "john@example.com", LocalDate.of(1990, 5, 10));

        assertNotNull(patient.getId());
        assertEquals("John Doe", patient.getName());
        assertEquals("john@example.com", patient.getEmail());
        assertEquals(LocalDate.of(1990, 5, 10), patient.getBirthday());
    }

    @Test
    @DisplayName("Should throw error when name is blank")
    void shouldThrowErrorForInvalidName() {
        assertThrows(DomainException.class, () -> {
            new Patient(" ", "john@example.com", LocalDate.of(1990, 5, 10));
        });
    }

    @Test
    @DisplayName("Should throw error when name is too short")
    void shouldThrowErrorForShortName() {
        assertThrows(DomainException.class, () -> {
            new Patient("Jo", "john@example.com", LocalDate.of(1990, 5, 10));
        });
    }

    @Test
    @DisplayName("Should throw error when email is invalid")
    void shouldThrowErrorForInvalidEmail() {
        assertThrows(DomainException.class, () -> {
            new Patient("John Doe", "johnexample.com", LocalDate.of(1990, 5, 10));
        });
    }

    @Test
    @DisplayName("Should throw error when birthday is in the future")
    void shouldThrowErrorForFutureBirthday() {
        assertThrows(DomainException.class, () -> {
            new Patient("John Doe", "john@example.com", LocalDate.now().plusDays(1));
        });
    }

    @Test
    @DisplayName("Should throw error for a birthday too old")
    void shouldThrowErrorForOldBirthday() {
        assertThrows(DomainException.class, () -> {
            new Patient("John Doe", "john@example.com", LocalDate.of(1800, 1, 1));
        });
    }

    @Test
    @DisplayName("Should update name successfully")
    void shouldUpdateNameSuccessfully() {
        Patient patient = new Patient("John Doe", "john@example.com", LocalDate.of(1990, 5, 10));

        patient.updateName("Jane Doe");

        assertEquals("Jane Doe", patient.getName());
    }

    @Test
    @DisplayName("Should not update name with invalid value")
    void shouldThrowErrorWhenUpdatingNameToInvalid() {
        Patient patient = new Patient("John Doe", "john@example.com", LocalDate.of(1990, 5, 10));

        assertThrows(DomainException.class, () -> {
            patient.updateName("");
        });
    }

    @Test
    @DisplayName("Should update email successfully")
    void shouldUpdateEmailSuccessfully() {
        Patient patient = new Patient("John Doe", "john@example.com", LocalDate.of(1990, 5, 10));

        patient.updateEmail("newmail@example.com");

        assertEquals("newmail@example.com", patient.getEmail());
    }

    @Test
    @DisplayName("Should not update with invalid email")
    void shouldThrowErrorWhenUpdatingEmailToInvalid() {
        Patient patient = new Patient("John Doe", "john@example.com", LocalDate.of(1990, 5, 10));

        assertThrows(DomainException.class, () -> {
            patient.updateEmail("invalid-mail");
        });
    }
}
