package pe.bruno.com.fileattachment.application.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.bruno.com.fileattachment.application.dto.tieto.PokemonDto;

//@FeignClient(value = "pokeapi", url = "https://pokeapi.co/api/v2", configuration = PokemonConfiguration.class)
public interface PokemonClient {
    @GetMapping(value = "/pokemon/{idOrName}")
    PokemonDto getPokemon(@PathVariable String idOrName);
}
