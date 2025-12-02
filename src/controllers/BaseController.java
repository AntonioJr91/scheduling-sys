package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import interfaces.HasEmail;
import interfaces.HasId;
import interfaces.HasName;
import interfaces.IsUpdatable;
import services.BaseService;
import utils.Input;
import utils.PauseUI;

public abstract class BaseController<T extends HasId & HasName & HasEmail & IsUpdatable> {
  protected Scanner sc = Input.sc;
  protected BaseService<T> service;
  protected Class<T> entityClass;
  protected String entityName;

  public BaseController(BaseService<T> service, Class<T> entityClass) {
    this.service = service;
    this.entityClass = entityClass;
    this.entityName = entityClass.getSimpleName();
  }

  protected abstract void add();

  public void getAll() {
    List<T> entities = service.getAll();

    System.out.printf("----- %s List -----\n", entityClass.getSimpleName());

    if (entities.isEmpty()) {
      System.out.println("Empty list");
      pause();
      return;
    }

    entities.forEach(System.out::println);
    pause();
  }

  public void getByName() {
    System.out.printf("----- %s List By Name -----", entityName);

    System.out.print("Name: ");
    String name = sc.nextLine();

    List<T> entities = service.getAll().stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList();

    if (entities.isEmpty()) {
      System.out.println("Empty list");
      pause();
      return;
    }

    entities.forEach(System.out::println);
    pause();
  }

  public void update() {
    System.out.printf("----- %s Update -----\n", entityName);

    System.out.print("Name: ");
    String name = sc.nextLine();

    T entity = service.getAll().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst()
        .orElse(null);

    if (entity == null) {
      System.out.printf("Invalid %s.\n", entityName);
      pause();
      return;
    }

    System.out.println(entity);

    System.out.print("New Email Address: ");
    String email = sc.nextLine();

    boolean isValid = service.getAll().stream().anyMatch(p -> p.getEmail().equalsIgnoreCase(email));

    if (isValid) {
      System.out.println("This email is already in use. Please try another one.");
      pause();
      return;
    }

    try {
      entity.updateEmail(email);
      System.out.println("Your email address has been successfully updated.");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    pause();
  }

  public void delete() {
    System.out.printf("----- %s Delete -----\n", entityName);

    System.out.printf("Enter %s id: ", entityName);
    int id = sc.nextInt();
    sc.nextLine();

    T entity;

    try {
      entity = service.findById(id);
    } catch (Exception e) {
      System.out.printf("%s does not exist.\n", entityName);
      pause();
      return;
    }

    System.out.println(entity);

    System.out.print("Delete this item? (y)yes (n)no: ");
    char choose = sc.nextLine().toLowerCase().charAt(0);
    if (choose != 'y') {
      System.out.println("Action canceled.");
      pause();
      return;
    }
    try {
      service.delete(entity.getId());
      System.out.printf("%s successfully deleted.", entityName);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    pause();
  }

  protected LocalDate readBirthday() {
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

  protected void pause() {
    PauseUI.pause();
  }

}
