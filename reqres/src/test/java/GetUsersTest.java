import data.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetUsersTest {
    private static final String basePath = "https://reqres.in/api/";
    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(basePath)
            .setContentType(ContentType.JSON)
            .build();

    ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    @Test
    public void checkGetUsersTest() {
        List<User> users = given()
                .spec(requestSpec)
                .when()
                .contentType(ContentType.JSON)
                .get(EndPoints.getUsers)
                .then()
                .spec(responseSpec)
                .extract().body().jsonPath().getList("data", User.class);

        users.forEach(user -> Assert.assertTrue(user.getAvatar().contains(user.getId().toString())));
        users.forEach(user -> Assert.assertTrue(user.getEmail().endsWith("reqres.in")));
        Assert.assertEquals(users.size(), 6);


    }
}
