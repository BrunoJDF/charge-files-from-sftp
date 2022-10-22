package pe.bruno.com.fileattachment.config;

import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pe.bruno.com.fileattachment.config.mapper.MapperConfiguration;

@Configuration
@ComponentScan(basePackages = "pe.bruno.com.fileattachment.config.mapper")
@AllArgsConstructor
public class OrikaConfiguration {

    private final MapperConfiguration mapperConfiguration;

    @Bean
    public MapperFactory mapperFactory() {
        var mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperConfiguration.registerConfigurableMapper(mapperFactory);
        return mapperFactory;
    }

    @Bean
    public MapperFacade mapperFacade(MapperFactory mapperFactory) {
        return mapperFactory.getMapperFacade();
    }

}
