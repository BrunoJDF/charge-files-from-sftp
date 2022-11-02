package pe.bruno.com.fileattachment.application.dto.tieto;

import lombok.Data;

@Data
public class TokenDto {
    private long id;
    private String token;
    private boolean active = true;
}
