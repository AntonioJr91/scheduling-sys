package controllers;

import java.time.LocalDate;

import enums.Specialty;
import model.Doctor;
import services.BaseService;

public class DoctorController extends BaseController<Doctor> {

  public DoctorController(BaseService<Doctor> service) {
    super(service, Doctor.class);
  }

  public void add() {
    System.out.printf("----- New %s ------\n", entityName);

    System.out.print("Name: ");
    String name = sc.nextLine();

    System.out.print("Email: ");
    String email = sc.nextLine();

    System.out.println("Specialty: ");

    for (int i = 0; i < Specialty.values().length; i++) {
      System.out.printf("%d - %s\n", i + 1, Specialty.values()[i]);
    }
    System.out.print("Choose: ");
    int specialtyIndex = sc.nextInt();
    sc.nextLine();

    Specialty specialty;

    if (specialtyIndex < 1 || specialtyIndex > Specialty.values().length) {
      System.out.println("Invalid specialty.");
      pause();
      return;
    }

    specialty = Specialty.values()[specialtyIndex -1];

    LocalDate birthday = readBirthday();

    try {
      Doctor doctor = new Doctor(name, email, birthday, specialty);
      service.save(doctor);
      System.out.println("Doctor successfully created.");
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    pause();
  }

}
