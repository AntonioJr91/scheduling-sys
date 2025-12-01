package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import model.Appointment;
import model.Doctor;
import model.Patient;
import services.AppointmentService;
import services.DoctorService;
import services.PatientService;
import utils.Input;

public class AppointmentController {
  private static Scanner sc = Input.sc;
  private AppointmentService appointmentService;
  private PatientService patientService;
  private DoctorService doctorService;

  public AppointmentController(AppointmentService appointmentService,
      PatientService patientService,
      DoctorService doctorService) {
    this.appointmentService = appointmentService;
    this.patientService = patientService;
    this.doctorService = doctorService;
  }

  public void add() {
    while (true) {
      System.out.println("---- New Appointment -----");

      System.out.print("Patient Name: ");
      String patientName = sc.nextLine();

      System.out.print("Doctor Name: ");
      String doctorName = sc.nextLine();

      System.out.print("Reason: ");
      String reason = sc.nextLine();

      LocalDateTime startDateTime = readLocalDateTime("Start DateTime");
      LocalDateTime endDateTime = readLocalDateTime("End DateTime");

      System.out.print("Consultation Fee: ");
      Double consultationFee = sc.nextDouble();

      Patient patient = findPatient(patientName);
      Doctor doctor = findDoctor(doctorName);

      try {
        Appointment appointment = new Appointment(patient, doctor, reason, startDateTime, endDateTime, consultationFee);
        appointmentService.save(appointment);
        System.out.println("Appointment successfully created.");
        return;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
  }

  public void showAll(){
    for(Appointment ap : appointmentService.getAll()){
      System.out.println(ap);
    }
  }

  private Patient findPatient(String name) {
    Patient patient = patientService.getAll()
        .stream()
        .filter(p -> p.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);

    if (patient == null) {
      throw new RuntimeException("nao encontrado");
    }
    return patient;
  }

  private Doctor findDoctor(String name) {
    Doctor doctor = doctorService.getAll()
        .stream()
        .filter(p -> p.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);

    if (doctor == null) {
      throw new RuntimeException("nao encontrado");
    }
    return doctor;
  }

  private LocalDateTime readLocalDateTime(String optionName) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    while (true) {
      System.out.printf("%s: (dd/MM/yyyy HH:mm): ", optionName);
      String dateTime = sc.nextLine();

      try {
        return LocalDateTime.parse(dateTime, fmt);
      } catch (DateTimeParseException e) {
        System.out.println("Invalid format. Use dd/MM/yyyy HH:mm.");
      }
    }
  }
}
