package pe.bruno.com.fileattachment.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PokemonConfiguration {

    @Bean
    public RequestInterceptor getPokemonInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("User-Agent", "file-attachment/1.0");
        };
    }
}
