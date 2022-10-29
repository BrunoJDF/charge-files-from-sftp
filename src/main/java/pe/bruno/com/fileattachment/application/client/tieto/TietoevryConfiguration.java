package pe.bruno.com.fileattachment.application.client.tieto;

import feign.RequestInterceptor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TietoevryConfiguration {
    @Value("${api.tietoevry.user}")
    private String tietoUser;
    @Value("${api.tietoevry.password}")
    private String tietoPassword;

    @Bean
    public RequestInterceptor getTietoevryInterceptor(){
        return requestTemplate -> {
            requestTemplate.header("User-Agent", "file-attachment/1.0");
        };
    }
}
