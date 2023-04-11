package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PokemonListResult {
    private int count;
    private String next;
    private String previous;
    private List<Results> results;


    @Data
    @Builder
    public static class Results {
        private String name;
        private String url;
    }
}

