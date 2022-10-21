package pe.bruno.com.fileattachment.web.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiFieldError {
    private String fieldError;
    private String description;
}
