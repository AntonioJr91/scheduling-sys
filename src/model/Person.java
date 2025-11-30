package model;

import java.time.LocalDate;

import interfaces.HasEmail;
import model.exception.DomainException;

public abstract class Person implements HasEmail{

  private static int count = 0;

  private final Integer id;
  private String name;
  private String email;
  private LocalDate birthday;

  public Person(String name, String email, LocalDate birthday) {
    validateName(name);
    validateEmail(email);
    validateBirthday(birthday);

    id = ++count;
    this.name = name;
    this.email = email;
    this.birthday = birthday;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  private void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", name=" + name + ", email=" + email + ", birthday=" + birthday + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Person other = (Person) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  private void validateName(String name) {
    DomainException.when(name == null || name.isBlank(), "Name is required");
    DomainException.when(name.length() < 3 || name.length() > 50,
        "Name must be between 3 and 50 characters");
  }

  private void validateEmail(String email) {
    DomainException.when(email == null || email.isBlank(), "Email is required");
    DomainException.when(!email.contains("@") || !email.contains("."),
        "Invalid email format");
  }

  private void validateBirthday(LocalDate birthday) {
    DomainException.when(birthday == null, "Birthday is required");
    DomainException.when(birthday.isAfter(LocalDate.now()),
        "Birthday cannot be in the future");
    DomainException.when(birthday.isBefore(LocalDate.of(1900, 1, 1)),
        "Birthday is too old to be valid");
  }

  public void updateName(String name) {
    validateName(name);
    setName(name);
  }

  public void updateEmail(String email) {
    validateEmail(email);
    setEmail(email);
  }
}