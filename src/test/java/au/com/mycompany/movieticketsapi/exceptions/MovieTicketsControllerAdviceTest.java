package au.com.mycompany.movieticketsapi.exceptions;

import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.AGE_IS_REQUIRED_ERROR_MESSAGE;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.COLON;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.SEMI_COLON;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.SYSTEM_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.UNEXPECTED_ERROR_MESSAGE;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


@ExtendWith(MockitoExtension.class)
public class MovieTicketsControllerAdviceTest {
    private static final String ERROR = "Error";
    private static final String AGE = "age";
    private static final String OBJECT = "object";

    @InjectMocks
    private MovieTicketsControllerAdvice movieTicketsControllerAdvice;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @Test
    public void testHandleMethodArgumentNotValidException() {
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new FieldError(OBJECT, AGE, AGE_IS_REQUIRED_ERROR_MESSAGE)));
        ResponseEntity<ProblemDetail> responseEntity = movieTicketsControllerAdvice.handleMethodArgumentNotValidException(methodArgumentNotValidException);
        assertNotNull(responseEntity);
        ProblemDetail problemDetail = responseEntity.getBody();
        assertNotNull(problemDetail);
        assertEquals(BAD_REQUEST.value(), problemDetail.getStatus());
        assertEquals(AGE.concat(COLON).concat(AGE_IS_REQUIRED_ERROR_MESSAGE).concat(SEMI_COLON), problemDetail.getDetail());
    }

    @Test
    public void testHandleSystemException() {
        SystemException systemException = new SystemException(ERROR);
        ResponseEntity<ProblemDetail> responseEntity = movieTicketsControllerAdvice.handleSystemException(systemException);
        assertNotNull(responseEntity);
        ProblemDetail problemDetail = responseEntity.getBody();
        assertNotNull(problemDetail);
        assertEquals(INTERNAL_SERVER_ERROR.value(), problemDetail.getStatus());
        assertEquals(SYSTEM_ERROR_MESSAGE, problemDetail.getDetail());
    }

    @Test
    public void testHandleException() {
        Exception exception = new Exception(ERROR);
        ResponseEntity<ProblemDetail> responseEntity = movieTicketsControllerAdvice.handleException(exception);
        assertNotNull(responseEntity);
        ProblemDetail problemDetail = responseEntity.getBody();
        assertNotNull(problemDetail);
        assertEquals(INTERNAL_SERVER_ERROR.value(), problemDetail.getStatus());
        assertEquals(UNEXPECTED_ERROR_MESSAGE, problemDetail.getDetail());
    }
}
