import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specification {
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(EndPoints.basePath)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification getResponseSpecOK() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification getResponseSpecNotFound() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }
}
