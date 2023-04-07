import config.TestData;
import dto.DataPokemon;
import dto.PokemonListResult;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import utilits.ResponseWrapper;

import static io.qameta.allure.Allure.step;

@Epic("PokemonAPI")
@Execution(ExecutionMode.CONCURRENT)
public class PokemonTest extends BaseTest {

    @Test
    @Feature("Сравнение Rattata и Piggeotto")
    public void checkPokemonData() {
        step("Get запрос на имя Rattata", () -> {
            responseWrapper = steps.getPokemonByName(TestData.POKEMON_NAME_ONE);
            responseWrapper.assertStatusCode(HttpStatus.SC_OK);
        });
        DataPokemon responseRattata = responseWrapper.as(DataPokemon.class);

        step("Get запрос на имя Piggeotto", () -> {
            responseWrapper = steps.getPokemonByName(TestData.POKEMON_NAME_TWO);
            responseWrapper.assertStatusCode(HttpStatus.SC_OK);
        });
        DataPokemon responsePigeotto = responseWrapper.as(DataPokemon.class);

        step("Сравнение веса покемонов", () -> {
            ResponseWrapper.assertSoftly(softly -> {
                        softly.assertThat(responseRattata.getWeight() < responsePigeotto.getWeight()).withFailMessage("Вес оказался больше").isEqualTo(true);
                    });
        });

        step("Проверка обладания способностью run-away", () -> {
            ResponseWrapper.assertSoftly(softly -> {
                softly.assertThat(responseWrapper.getAbilitiesName(responseRattata.getAbilities()).contains("run-away")).withFailMessage("Спобосность Run-Away не найдена").isEqualTo(true);
                softly.assertThat(responseWrapper.getAbilitiesName(responsePigeotto.getAbilities()).contains("run-away")).withFailMessage("Способоность Run-Away найдена").isEqualTo(false);
            });
        });
    }

    @Test
    @Feature("Сравнение списка покемонов")
    public void checkPokemonListLimit() {
        step("Get запрос на получение списка покемонов", () -> {
            responseWrapper = steps.getPokemonList(TestData.LISTRANGE);
            responseWrapper.assertStatusCode(HttpStatus.SC_OK);
        });
        PokemonListResult responsePokemonList = responseWrapper.as(PokemonListResult.class);

        step("Проверка длинны списка покемонов", () -> {
            ResponseWrapper.assertSoftly(softly -> {
                softly.assertThat(responsePokemonList.getResults().size()).withFailMessage("Количество покемонов не совпадает с ожидаемым").isEqualTo(TestData.LISTRANGE);
                responsePokemonList.getResults().forEach(x -> softly.assertThat(x.getName()).withFailMessage("У покемона не оказалось имени").isNotNull());
            });
        });
    }
}
