package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import enums.Specialty;
import model.Doctor;
import services.DoctorService;
import utils.Input;

public class DoctorController {
  private DoctorService doctorService;
  private static Scanner sc = Input.sc;

  public DoctorController(DoctorService doctorService) {
    this.doctorService = doctorService;
  }

  public void add() {
    while (true) {
      System.out.println("--- New Doctor ----");

      System.out.print("Name: ");
      String name = sc.nextLine();

      System.out.print("Email: ");
      String email = sc.nextLine();

      System.out.print("Birthday: ");
      LocalDate birthday = readBirthday();

      System.out.println("Specialty:");

      for (int i = 0; i < Specialty.values().length; i++) {
        System.out.printf("%d - %s \n", i + 1, Specialty.values()[i]);
      }

      System.out.print("Choose: ");
      int choose = sc.nextInt();
      sc.nextLine();

      Specialty specialty = null;
      if (choose > 0 && choose <= Specialty.values().length) {
        specialty = Specialty.values()[choose - 1];
      }

      try {
        Doctor doctor = new Doctor(name, email, birthday, specialty);
        doctorService.save(doctor);
        System.out.println("Doctor successfully created.");
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
