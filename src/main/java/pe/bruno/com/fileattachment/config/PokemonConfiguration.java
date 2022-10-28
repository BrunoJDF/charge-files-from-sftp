package pe.bruno.com.fileattachment.config;

import feign.RequestInterceptor;

//@Configuration
public class PokemonConfiguration {

    //Bean
    public RequestInterceptor getPokemonInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("User-Agent", "file-attachment/1.0");
        };
    }
}
