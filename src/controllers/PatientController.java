package controllers;

import java.time.LocalDate;

import model.Patient;
import services.BaseService;

public final class PatientController extends BaseController<Patient> {

  public PatientController(BaseService<Patient> service) {
    super(service, Patient.class);
  }

  public void add() {
    System.out.printf("----- New %s ------\n", entityName);

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
      System.err.println(ex.getMessage());
    }
    pause();
  }
}