package pe.bruno.com.fileattachment.application.client.tieto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.bruno.com.fileattachment.application.dto.tieto.TokenDto;

//@FeignClient(value = "Tieto", url = "https://10.47.156.162", configuration = TietoevryConfiguration.class)
public interface TietoevryClient {

    @PostMapping("/token")
    TokenDto getToken(@RequestParam String user, @RequestParam String passwd);

    @PutMapping("/api/v2/configuration/feature/eir/equipments/provisioning")
    String sendFile(@RequestParam String filePath);
}
