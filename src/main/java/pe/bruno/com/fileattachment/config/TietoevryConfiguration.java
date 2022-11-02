package pe.bruno.com.fileattachment.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
@Getter
public class TietoevryConfiguration {
    @Value("${api.tietoevry.user}")
    private String tietoUser;
    @Value("${api.tietoevry.password}")
    private String tietoPasswd;
    @Value("${api.tietoevry.uri}")
    private String tietoUri;

    @Bean
    public WebClient getWebClientTieto() {
        SslContext sslContext;
        try {
            sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
        } catch (SSLException e) {
            throw new RuntimeException(e);
        }
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
        return WebClient.builder()
                .baseUrl(tietoUri)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
