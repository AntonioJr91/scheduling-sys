package model.exception;

public class DomainException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String PREFIX = "[DomainException] ";

  public DomainException(String message) {
    super(PREFIX + message);
  }

  public static void when(boolean hasError, String msg) {
    if (hasError) {
      throw new DomainException(msg);
    }
  }
}
