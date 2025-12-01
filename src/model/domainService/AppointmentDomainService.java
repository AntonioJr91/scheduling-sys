package model.domainService;

import java.util.List;

import model.Appointment;
import model.exception.DomainException;

public class AppointmentDomainService {

  public static void validateNoTimeConflict(Appointment newAppointment, List<Appointment> appointments) {
    boolean hasConflict = appointments.stream()
        .filter(a -> a.getDoctor().getId().equals(newAppointment.getDoctor().getId()))
        .anyMatch(existing -> existing.getStartDateTime().isBefore(newAppointment.getEndDateTime()) &&
            existing.getEndDateTime().isAfter(newAppointment.getStartDateTime()));

    DomainException.when(hasConflict,
        "Doctor already has an appointment in this time range.");
  }
}
