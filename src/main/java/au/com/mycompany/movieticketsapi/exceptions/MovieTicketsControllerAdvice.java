package au.com.mycompany.movieticketsapi.exceptions;

import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.COLON;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.SEMI_COLON;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.SYSTEM_ERROR_MESSAGE;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.VALIDATION_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.UNEXPECTED_ERROR_MESSAGE;

import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MovieTicketsControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        AtomicReference<String> details = new AtomicReference<>(EMPTY);
        exception.getBindingResult()
            .getAllErrors()
            .forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                details.set(details.get().concat(fieldName).concat(COLON).concat(errorMessage).concat(SEMI_COLON));
            });

        log.error(VALIDATION_ERROR_MESSAGE, exception);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, details.get());
        return new ResponseEntity<>(problemDetail, BAD_REQUEST);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ProblemDetail> handleSystemException(final SystemException exception) {
        log.error(SYSTEM_ERROR_MESSAGE, exception);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, SYSTEM_ERROR_MESSAGE);
        return new ResponseEntity<>(problemDetail, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(final Exception exception) {
        log.error(UNEXPECTED_ERROR_MESSAGE, exception);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, UNEXPECTED_ERROR_MESSAGE);
        return new ResponseEntity<>(problemDetail, INTERNAL_SERVER_ERROR);
    }
}
