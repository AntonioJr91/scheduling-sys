package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
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

  public void getAll() {
    List<Patient> patients = patientService.getAll();

    System.out.println("----- Patient List -----");

    if (patients.isEmpty()) {
      System.out.println("Empty list");
      pause();
      return;
    }

    patients.forEach(System.out::println);
    pause();
    return;
  }

  public void getByName() {
    System.out.println("----- Patient List By Name -----");

    System.out.print("Name: ");
    String name = sc.nextLine();

    List<Patient> patients = patientService.getAll().stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList();

    if (patients.isEmpty()) {
      System.out.println("Empty list");
      pause();
      return;
    }

    patients.forEach(System.out::println);
    pause();
    return;
  }

  public void add() {
    System.out.println("----- New Patient ------");

    System.out.print("Name: ");
    String name = sc.nextLine();

    System.out.print("Email: ");
    String email = sc.nextLine();

    LocalDate birthday = readBirthday();

    try {
      Patient patient = new Patient(name, email, birthday);
      patientService.save(patient);
      System.out.println("Patient successfully created.");
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    pause();
  }

  public void update() {
  }

  public void delete() {
    System.out.println("----- Patient Delete -----");

    System.out.print("Enter patient id: ");
    int id = sc.nextInt();
    sc.nextLine();

    Patient patient;

    try {
      patient = patientService.findById(id);
    } catch (Exception e) {
      System.out.println("Patient does not exist.");
      pause();
      return;
    }

    System.out.println(patient);

    System.out.print("Delete this item? (y)yes (n)no: ");
    char choose = sc.nextLine().toLowerCase().charAt(0);
    if (choose != 'y') {
      System.out.println("Action canceled.");
      pause();
      return;
    }
    try {
      patientService.delete(patient.getId());
      System.out.println("Patient successfully deleted.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    pause();
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

  private void pause() {
    System.out.print("Press any key to continue...");
    sc.nextLine();
  }
}
