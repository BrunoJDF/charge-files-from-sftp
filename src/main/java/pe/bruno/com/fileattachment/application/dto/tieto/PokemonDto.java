package pe.bruno.com.fileattachment.application.dto.tieto;

import lombok.Data;

@Data
public class PokemonDto {
    public int id;
    public String name;
    public int base_experience;
    public int height;
    public boolean is_default;
    public int order;
    public int weight;
}
