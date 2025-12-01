package services;

import java.util.List;

import interfaces.HasEmail;
import model.exception.EntityAlreadyExistsException;

public interface EmailValidatableService<T extends HasEmail> {

  default void validateEmail(List<T> entities, T newEntity) {
    boolean exists = entities.stream().anyMatch(e -> e.getEmail().equalsIgnoreCase(newEntity.getEmail()));
    if (exists) {
      throw new EntityAlreadyExistsException("Email already registered.");
    }
  }

}
