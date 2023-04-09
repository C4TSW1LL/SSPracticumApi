import config.BaseConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import steps.Steps;
import utilits.ResponseWrapper;

public class BaseTest {

    private final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    protected final Steps steps = new Steps(getRequestSpecification());

    protected ResponseWrapper responseWrapper;

    private RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(config.url())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

}