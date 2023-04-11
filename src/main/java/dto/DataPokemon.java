package dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties (ignoreUnknown = true)
public class DataPokemon{
	private List<AbilitiesItem> abilities;
	private String name;
	private int weight;

	@Data
	@Builder
	public static class AbilitiesItem {
		@JsonProperty("is_hidden")
		private boolean isHidden;
		private Ability ability;
		private int slot;
	}

	@Data
	@Builder
	public static class Ability{
		private String name;
		private String url;
	}
}