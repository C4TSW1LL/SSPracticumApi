import config.TestData;
import dto.DataPokemon;
import dto.PokemonListResult;
import helpers.GetFromBody;
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
        responseWrapper = steps.getPokemonByName(TestData.POKEMON_NAME_ONE);
        responseWrapper.assertStatusCode(HttpStatus.SC_OK);
        DataPokemon responseRattata = responseWrapper.as(DataPokemon.class);

        responseWrapper = steps.getPokemonByName(TestData.POKEMON_NAME_TWO);
        responseWrapper.assertStatusCode(HttpStatus.SC_OK);
        DataPokemon responsePigeotto = responseWrapper.as(DataPokemon.class);

        step("Сравнение веса покемонов", () -> {
            ResponseWrapper.assertSoftly(softly -> {
                softly.assertThat(responseRattata.getWeight() < responsePigeotto.getWeight())
                        .withFailMessage("Вес оказался больше")
                        .isEqualTo(true);
            });
        });

        step("Проверка наличия спобосности run-away", () -> {
            ResponseWrapper.assertSoftly(softly -> {
                softly.assertThat(GetFromBody.getAbilitiesList(responseRattata.getAbilities()).contains("run-away"))
                        .withFailMessage("Спобосность Run-Away не найдена")
                        .isEqualTo(true);

                softly.assertThat(GetFromBody.getAbilitiesList(responsePigeotto.getAbilities()).contains("run-away"))
                        .withFailMessage("Способоность Run-Away найдена")
                        .isEqualTo(false);
            });
        });
    }

    @Test
    @Feature("Сравнение списка покемонов")
    public void checkPokemonListLimit() {
        responseWrapper = steps.getPokemonList(TestData.LISTRANGE);
        responseWrapper.assertStatusCode(HttpStatus.SC_OK);
        PokemonListResult responsePokemonList = responseWrapper.as(PokemonListResult.class);

        step("Проверка длинны списка покемонов", () -> {
            ResponseWrapper.assertSoftly(softly -> {
                softly.assertThat(responsePokemonList.getResults().size())
                        .withFailMessage("Количество покемонов не совпадает с ожидаемым")
                        .isEqualTo(TestData.LISTRANGE);
            });
        });

        step("У каждого покемона есть имя", () -> {
            ResponseWrapper.assertSoftly(softly -> {
                responsePokemonList.getResults().forEach(x -> softly.assertThat(x.getName())
                        .withFailMessage("У покемона не оказалось имени")
                        .isNotNull());
            });
        });
    }
}
