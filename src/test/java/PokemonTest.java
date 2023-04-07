import dto.DataPokemon;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.Test;
import utilits.ResponseWrapper;

import static io.qameta.allure.Allure.step;

@Epic("12345")
public class PokemonTest extends BaseTest {
    private final static String URL = "https://pokeapi.co/";


    @Test
    public void checkPokemonData() {
        step("Get запрос на имя Rattata", () -> {
            responseWrapper = steps.getPokemonByName("rattata");
        });
        DataPokemon responseRattata = responseWrapper.as(DataPokemon.class);

        step("Get запрос на имя Piggeotto", () -> {
            responseWrapper = steps.getPokemonByName("pidgeotto");
        });
        DataPokemon responsePigeotto = responseWrapper.as(DataPokemon.class);

        step("assert", () -> {
            responseWrapper.assertStatusCode(200);
            ResponseWrapper
                    .assertSoftly(softly -> {
                        softly.assertThat(responseRattata.getWeight() < responsePigeotto.getWeight()).isEqualTo(true);
                        softly.assertThat(responseWrapper.getAbilitiesName(responseRattata.getAbilities()).contains("run-away")).withFailMessage("Спобосность Run-Away не найдена").isEqualTo(true);
                        softly.assertThat(responseWrapper.getAbilitiesName(responsePigeotto.getAbilities()).contains("run-away")).withFailMessage("Способоность Run-Away найдена").isEqualTo(false);
                    });
        });
    }

    @Test
    public void checkPokemonListLimit() {



    }
}
