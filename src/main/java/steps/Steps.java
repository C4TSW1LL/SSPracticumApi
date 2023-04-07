package steps;

import lombok.RequiredArgsConstructor;
import utilits.ResponseWrapper;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

@RequiredArgsConstructor
public class GetPokemon {

    private static final String BASE_URL = "pokemon/";


    public ResponseWrapper getPokemonByName(String name) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .get(BASE_URL + name + "/")
                .andReturn());


    }
}