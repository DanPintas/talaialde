package es.danpintas.talaialde.users.security;

public class LoginErrorException extends RuntimeException {

  private static final long serialVersionUID = -2248632337720522947L;

  public enum LoginErrorType {NO_USER, WRONG_PASSWORD}

  LoginErrorType errorType;

  public LoginErrorException(LoginErrorType errorType) {
    this.errorType = errorType;
  }

  public LoginErrorType getErrorType() {
    return errorType;
  }

}
