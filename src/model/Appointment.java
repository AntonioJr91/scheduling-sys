package model;

import java.time.LocalDateTime;

import enums.AppointmentStatus;
import interfaces.HasId;
import model.exception.DomainException;

public class Appointment implements HasId {
  private static int count = 0;
  private Integer id;
  private final Patient patient;
  private final Doctor doctor;
  private String reason;
  private final LocalDateTime startDateTime;
  private final LocalDateTime endDateTime;
  private AppointmentStatus appointmentStatus;
  private final Double consultationFee;

  public Appointment(Patient patient, Doctor doctor, String reason, LocalDateTime startDateTime,
      LocalDateTime endDateTime, Double consultationFee) {
    isValid(patient, doctor, startDateTime, endDateTime, consultationFee);
    this.id = ++count;
    this.patient = patient;
    this.doctor = doctor;
    this.reason = reason;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.appointmentStatus = AppointmentStatus.PENDING;
    this.consultationFee = consultationFee;
  }

  public Integer getId() {
    return id;
  }

  public Patient getPatient() {
    return patient;
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public String getReason() {
    return reason;
  }

  public LocalDateTime getStartDateTime() {
    return startDateTime;
  }

  public LocalDateTime getEndDateTime() {
    return endDateTime;
  }

  public AppointmentStatus getAppointmentStatus() {
    return appointmentStatus;
  }

  public Double getConsultationFee() {
    return consultationFee;
  }

  public void cancel() {
    DomainException.when(appointmentStatus == AppointmentStatus.CANCELED, "Appointment already canceled.");
    DomainException.when(appointmentStatus == AppointmentStatus.COMPLETED, "Completed appointment cannot be canceled.");
    this.appointmentStatus = AppointmentStatus.CANCELED;
  }

  public void completed() {
    DomainException.when(appointmentStatus == AppointmentStatus.COMPLETED, "Appointment already completed.");
    DomainException.when(appointmentStatus == AppointmentStatus.CANCELED, "Canceled appointment cannot be completed.");
    DomainException.when(LocalDateTime.now().isBefore(endDateTime), "Cannot complete appointment before it ends.");
    this.appointmentStatus = AppointmentStatus.COMPLETED;
  }

  private void isValid(Patient patient, Doctor doctor, LocalDateTime startDateTime, LocalDateTime endDateTime,
      Double consultationFee) {
    DomainException.when(patient == null, "Patient is required.");
    DomainException.when(doctor == null, "Doctor is required.");
    DomainException.when(startDateTime == null || endDateTime == null, "Dates must be required.");
    DomainException.when(!endDateTime.isAfter(startDateTime), "The end date/time must be after the start date/time.");
    DomainException.when(consultationFee == null, "Consultation fee is required.");
    DomainException.when(consultationFee < 100.00, "The consultation fee must be greater or equal than 100.00.");
  }

  @Override
  public String toString() {
    return "Appointment [id=" + id + ", patient=" + patient + ", doctor=" + doctor + ", reason=" + reason
        + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", appointmentStatus="
        + appointmentStatus + ", consultationFee=" + consultationFee + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Appointment other = (Appointment) obj;
    if (id != other.id)
      return false;
    return true;
  }

}
