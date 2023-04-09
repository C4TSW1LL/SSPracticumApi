package steps;

import config.BaseConfig;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import utilits.ResponseWrapper;

import static io.restassured.RestAssured.given;


public class Steps {

    private static final String BASE_URL = "pokemon/";

    private final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    protected final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(config.url())
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build();

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