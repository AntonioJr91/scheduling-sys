package tests.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import enums.AppointmentStatus;
import enums.Specialty;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.exception.DomainException;

class AppointmentTest {

    private Patient patientHelper() {
        return new Patient("John Doe", "john@example.com", LocalDate.of(1990, 5, 10));
    }

    private Doctor doctorHelper() {
        return new Doctor("Dr. House", "house@example.com", LocalDate.of(1980, 1, 10), Specialty.CARDIOLOGIST);
    }

    @Test
    @DisplayName("Should create appointment successfully")
    void shouldCreateSuccessfully() {
        Appointment appointment = new Appointment(
                patientHelper(),
                doctorHelper(),
                "Routine check",
                LocalDateTime.now().minusHours(2),
                LocalDateTime.now().minusHours(1),
                150.00
        );

        assertNotNull(appointment.getId());
        assertEquals("Routine check", appointment.getReason());
        assertEquals(AppointmentStatus.PENDING, appointment.getAppointmentStatus());
        assertEquals(150.0, appointment.getConsultationFee());
    }

    @Test
    @DisplayName("Should throw error when patient is null")
    void shouldThrowWhenPatientNull() {
        assertThrows(DomainException.class, () -> {
            new Appointment(
                null,
                doctorHelper(),
                "Check",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                150.00
            );
        });
    }

    @Test
    @DisplayName("Should throw error when doctor is null")
    void shouldThrowWhenDoctorNull() {
        assertThrows(DomainException.class, () -> {
            new Appointment(
                patientHelper(),
                null,
                "Check",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                150.00
            );
        });
    }

    @Test
    @DisplayName("Should throw error when end time is before start")
    void shouldThrowWhenEndBeforeStart() {
        assertThrows(DomainException.class, () -> {
            new Appointment(
                patientHelper(),
                doctorHelper(),
                "Check",
                LocalDateTime.now(),
                LocalDateTime.now().minusHours(1),
                150.00
            );
        });
    }

    @Test
    @DisplayName("Should throw error when fee is below 100")
    void shouldThrowWhenInvalidFee() {
        assertThrows(DomainException.class, () -> {
            new Appointment(
                patientHelper(),
                doctorHelper(),
                "Check",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                50.00
            );
        });
    }

    @Test
    @DisplayName("Should cancel appointment successfully if pending")
    void shouldCancelSuccessfully() {
        Appointment ap = new Appointment(
                patientHelper(),
                doctorHelper(),
                "Check",
                LocalDateTime.now().minusHours(2),
                LocalDateTime.now().minusHours(1),
                150.00
        );

        ap.cancel();

        assertEquals(AppointmentStatus.CANCELED, ap.getAppointmentStatus());
    }

    @Test
    @DisplayName("Should not cancel a completed appointment")
    void shouldNotCancelCompleted() {
        Appointment ap = new Appointment(
                patientHelper(),
                doctorHelper(),
                "Check",
                LocalDateTime.now().minusHours(3),
                LocalDateTime.now().minusHours(2),
                150.00
        );

        ap.completed(); // already completed

        assertThrows(DomainException.class, ap::cancel);
    }

    @Test
    @DisplayName("Should complete appointment successfully after it ends")
    void shouldCompleteSuccessfully() {
        Appointment ap = new Appointment(
                patientHelper(),
                doctorHelper(),
                "Check",
                LocalDateTime.now().minusHours(2),
                LocalDateTime.now().minusHours(1),
                150.00
        );

        ap.completed();

        assertEquals(AppointmentStatus.COMPLETED, ap.getAppointmentStatus());
    }

    @Test
    @DisplayName("Should not complete before end time")
    void shouldNotCompleteBeforeEnd() {
        Appointment ap = new Appointment(
                patientHelper(),
                doctorHelper(),
                "Check",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                150.00
        );

        assertThrows(DomainException.class, ap::completed);
    }
}
