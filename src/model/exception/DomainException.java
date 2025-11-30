package model.exception;

public class DomainException extends RuntimeException {
  private static final String PREFIX = "[DomainException] ";

  protected DomainException(String message) {
    super(PREFIX + message);
  }

  public static void when(boolean hasError, String msg) {
    if (hasError) {
      throw new DomainException(msg);
    }
  }
}
