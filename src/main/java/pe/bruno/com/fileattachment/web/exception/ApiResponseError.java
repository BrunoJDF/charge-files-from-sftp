package pe.bruno.com.fileattachment.web.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApiResponseError {
    private int statusCode;
    private String message;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;
    private List<ApiFieldError> apiErrors = new ArrayList<>();
    @JsonIgnore
    private Exception exception;
    @JsonIgnore
    private List<FieldError> fieldErrors = new ArrayList<>();

    public ApiResponseError(int statusCode, String message, Exception exception) {
        this.statusCode = statusCode;
        this.message = message;
        this.exception = exception;
        this.timeStamp = LocalDateTime.now();
        filterError();
    }

    private void filterError(){
        if(exception instanceof WebExchangeBindException){

            boolean hasErrors = ((WebExchangeBindException) exception).getBindingResult().hasErrors();
            if(hasErrors){
                message = "Error en JSON";
                fieldErrors = ((WebExchangeBindException) exception).getBindingResult().getFieldErrors();
                enumErrors(fieldErrors);
            }
        }
    }

    private void enumErrors(List<FieldError> errors) {
        for (FieldError e:
                errors) {
            apiErrors.add(
                    ApiFieldError.builder()
                            .fieldError(e.getField())
                            .description(e.getDefaultMessage())
                            .build()
            );
        }
    }
}
