package model.exception;

public class EntityAlreadyExistsException extends DomainException {
  public EntityAlreadyExistsException(String message) {
    super(message);
  }
}
