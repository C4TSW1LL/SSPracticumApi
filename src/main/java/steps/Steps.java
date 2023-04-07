package steps;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import utilits.ResponseWrapper;

import static io.restassured.RestAssured.given;

@RequiredArgsConstructor
public class Steps {

    private final RequestSpecification requestSpecification;
    private static final String BASE_URL = "pokemon/";

    @Step("GET запрос на получение данных покемона по имени")
    @Attachment(value = "Вложение", type = "application/json", fileExtension = ".txt")
    public ResponseWrapper getPokemonByName(String name) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .get(BASE_URL + name + "/")
                .andReturn());
    }

    @Step("GET запрос на получение списка покемонов с обозначенной длинной")
    @Attachment(value = "Вложение", type = "application/json", fileExtension = ".txt")
    public ResponseWrapper getPokemonList(int listRange) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .get(BASE_URL + "?limit=" + listRange + "&offset=0")
                .andReturn());
    }
}