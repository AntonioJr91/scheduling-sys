package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import model.Appointment;
import model.Doctor;
import model.Patient;
import services.AppointmentService;
import services.DoctorService;
import services.PatientService;
import utils.Input;
import utils.PauseUI;

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

  public void getAll() {
    System.out.printf("----- Appointment List -----\n");

    if (appointmentService.getAll().isEmpty()) {
      System.err.println("Empty list.");
      PauseUI.pause();
      return;
    }

    appointmentService.getAll().forEach(System.out::println);
    PauseUI.pause();
  }

  public void getByPatient() {
    System.out.println("----- Patient consultation -----\n");

    System.out.println("Patient name: ");
    String name = sc.nextLine();

    List<Patient> patients = patientService.getAll().stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList();

    if (patients.isEmpty()) {
      System.err.println("No appointment available for this patient.");
      PauseUI.pause();
      return;
    }

    patients.forEach(System.out::println);
    PauseUI.pause();
  }

  public void add() {
    System.out.println("---- New Appointment -----\n");

    System.out.print("Patient Name: ");
    String patientName = sc.nextLine();

    System.out.print("Doctor Name: ");
    String doctorName = sc.nextLine();

    System.out.print("Reason: ");
    String reason = sc.nextLine();

    LocalDateTime startDateTime = readLocalDateTime("Start DateTime");
    LocalDateTime endDateTime = readLocalDateTime("End DateTime");

    System.out.print("Consultation Fee: ");
    String price = sc.nextLine();
    Double consultationFee;

    Patient patient = findPatient(patientName);
    Doctor doctor = findDoctor(doctorName);
    try {
      consultationFee = Double.parseDouble(price);
    } catch (NumberFormatException e) {
      System.err.println("Invalid fee. Provide a valid number.");
      PauseUI.pause();
      return;
    }

    try {
      Appointment appointment = new Appointment(patient, doctor, reason, startDateTime, endDateTime, consultationFee);
      appointmentService.save(appointment);
      System.out.println("Appointment successfully created.");
      return;
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    PauseUI.pause();
  }

  public void delete() {
    System.out.println("----- Delete Appointment -----\n");

    System.out.print("Appointment ID: ");
    int id = Integer.parseInt(sc.nextLine());

    Appointment appointment = appointmentService.getAll().stream().filter(a -> a.getId() == id).findFirst()
        .orElse(null);

    if (appointment == null) {
      System.err.println("Appointment not found.");
      PauseUI.pause();
      return;
    }

    System.out.println(appointment);

    System.out.print("Delete this item? (y)yes (n)no: ");
    String inputChoose = sc.nextLine();

    if (inputChoose.isEmpty()) {
      System.out.println("Invalid input. Action canceled.");
      PauseUI.pause();
      return;
    }

    char choose = inputChoose.toLowerCase().charAt(0);

    if (choose != 'y') {
      System.out.println("Action canceled.");
      PauseUI.pause();
      return;
    }

    try {
      appointmentService.delete(id);
      System.out.println("Appointment has been deleted.");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    PauseUI.pause();
  }

  private Patient findPatient(String name) {
    Patient patient = patientService.getAll()
        .stream()
        .filter(p -> p.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);

    if (patient == null) {
      throw new RuntimeException("Patient not found.");
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
      throw new RuntimeException("Doctor not found.");
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
