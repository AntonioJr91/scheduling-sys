package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import model.Patient;
import services.PatientService;
import utils.Input;

public class PatientController {

  private final PatientService patientService;
  private final Scanner sc = Input.sc;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  public void add() {
    while (true) {
      System.out.println("--- New Patient ----");

      System.out.print("Name: ");
      String name = sc.nextLine();

      System.out.print("Email: ");
      String email = sc.nextLine();

      LocalDate birthday = readBirthday();

      try {
        Patient patient = new Patient(name, email, birthday);
        patientService.save(patient);
        System.out.println("Patient successfully created.");
        return;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
  }

  private LocalDate readBirthday() {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    while (true) {
      System.out.print("Birthday (dd/MM/yyyy): ");
      String birthday = sc.nextLine();

      try {
        return LocalDate.parse(birthday, fmt);
      } catch (DateTimeParseException e) {
        System.out.println("Invalid format. Use dd/MM/yyyy.");
      }
    }
  }
}
