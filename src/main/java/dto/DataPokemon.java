package dto;

import java.util.List;
import lombok.Data;

@Data
public class DataPokemon{
	private List<AbilitiesItem> abilities;
	private String name;
	private int weight;
}