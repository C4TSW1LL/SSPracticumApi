package steps;

import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import utilits.ResponseWrapper;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

@RequiredArgsConstructor
public class Steps {

    private final RequestSpecification requestSpecification;
    private static final String BASE_URL = "pokemon/";


    public ResponseWrapper getPokemonByName(String name) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .get(BASE_URL + name + "/")
                .andReturn());
    }

    public ResponseWrapper getPokemonList() {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .get(BASE_URL)
                .andReturn());
    }
}