package net.avalith.elections.exceptions;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class ApiError {

  private List<String> errors;
  private String message;
  private HttpStatus status;

  /**
   * Method Api error. Detect and throw an error
   *
   * @param status  Status of the http request
   * @param message message returned
   * @param errors  list of errors found
   */
  public ApiError(HttpStatus status, String message, List<String> errors) {
    super();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  /**
   * Method Api error. Detect and throw an error
   *
   * @param status  Status of the http request
   * @param message message returned
   * @param error   error found
   */
  public ApiError(HttpStatus status, String message, String error) {
    super();
    this.status = status;
    this.message = message;
    errors = Arrays.asList(error);
  }
}