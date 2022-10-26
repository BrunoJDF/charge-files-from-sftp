package pe.bruno.com.fileattachment.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pe.bruno.com.fileattachment.web.exception.ApiResponseError;
import pe.bruno.com.fileattachment.web.exception.BadRequestException;
import pe.bruno.com.fileattachment.web.exception.NotFoundException;

@Slf4j
@ControllerAdvice
public class FileHandler {

    private ApiResponseError responseError;

    @ExceptionHandler
    public ResponseEntity<ApiResponseError> exceptionHandler(Exception ex) {
        responseError = constructResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex);
        log.error("exception handler", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseError> handleNotFoundException(NotFoundException ex) {
        responseError = constructResponse(HttpStatus.NOT_FOUND.value(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseError> handleBadRequestException(BadRequestException ex) {
        responseError = constructResponse(HttpStatus.BAD_REQUEST.value(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    private ApiResponseError constructResponse(int value, Exception ex) {
        return new ApiResponseError(value, ex.getMessage(), ex);
    }
}
