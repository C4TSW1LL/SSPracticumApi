package helpers;

import dto.DataPokemon;

import java.util.ArrayList;
import java.util.List;


public class GetFromBody {
    public static List<String> getAbilitiesList(List<DataPokemon.AbilitiesItem> abilities) {
        List<String> abilitiesName = new ArrayList<>();
        for (DataPokemon.AbilitiesItem element : abilities) {
            abilitiesName.add(element.getAbility().getName());
        }
        return abilitiesName;
    }
}
