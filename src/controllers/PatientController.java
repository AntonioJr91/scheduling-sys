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

    System.out.println("Patients");

    if (patients.isEmpty()) {
      System.out.println("Vazio");
    }
    System.out.println(patients);
  }

  public void getByName() {
    System.out.println("patitent");

    System.out.println("Name: ");
    String name = sc.nextLine();

    List<Patient> patients = patientService.getAll().stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList();

    System.out.println(patients);
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

  public void update() {
    System.out.println("Update");

    System.out.print("Informe o ID:");
    int id = sc.nextInt();
    sc.nextLine();

    Patient patient = patientService.findById(id);
    if (patient == null)
      throw new RuntimeException("id invalido");

    System.out.println("dados do  elemento para confirmação");

    System.out.println("Novo email: ");
    String email = "email";

    List<Patient> lista = patientService.getAll();
    boolean isValid = lista.stream().anyMatch(d -> d.getEmail().equalsIgnoreCase(email));
    if (isValid)
      throw new RuntimeException("email em uso");

    patient.updateEmail(email);
    System.out.println("Email atualizado.");
  }

  public void delete() {
    System.out.println("delete");

    System.out.print("email: ");
    String email = sc.nextLine();

    Patient patient = patientService.getAll().stream().filter(p -> p.getEmail().equalsIgnoreCase(email)).findFirst()
        .orElse(null);

    if (patient == null) {
      throw new RuntimeException("erro");
    }

    System.out.println(patient);

    System.out.println("confirmação");

    patientService.delete(patient.getId());
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
