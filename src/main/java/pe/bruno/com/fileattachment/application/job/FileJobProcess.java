package pe.bruno.com.fileattachment.application.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pe.bruno.com.fileattachment.application.client.PokemonClient;
import pe.bruno.com.fileattachment.application.dto.file.FileDto;
import pe.bruno.com.fileattachment.application.dto.tieto.PokemonDto;

@Slf4j
@RequiredArgsConstructor
public class FileJobProcess implements Job {
    private final PokemonClient pokemonClient;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        generateToken(FileDto.builder().build());
        PokemonDto response = pokemonClient.getPokemon("35");
        log.info("pokemon {}", response);
    }

    private void generateToken(FileDto savedFile) {
        log.info("generando token ");
    }
}
