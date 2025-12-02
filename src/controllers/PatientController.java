package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import model.Patient;
import services.BaseService;

public final class PatientController extends BaseController<Patient> {

  public PatientController(BaseService<Patient> service) {
    super(service, Patient.class);
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
      service.save(patient);
      System.out.println("Patient successfully created.");
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
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
}