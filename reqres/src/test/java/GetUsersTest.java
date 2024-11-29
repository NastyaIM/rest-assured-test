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

    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(EndPoints.basePath)
            .setContentType(ContentType.JSON)
            .build();

    ResponseSpecification responseSpecOK = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    ResponseSpecification responseSpecNotFound = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .build();

    @Test
    public void getUsersAndCheckListIsValidTest() {
        List<User> users = given()
                .spec(requestSpec)
                .when()
                .get(EndPoints.users + "?page=2")
                .then()
                .spec(responseSpecOK)
                .extract().body().jsonPath().getList("data", User.class);

        users.forEach(user -> Assert.assertTrue(user.getAvatar().contains(user.getId().toString())));
        users.forEach(user -> Assert.assertTrue(user.getEmail().endsWith("@reqres.in")));
        Assert.assertEquals(users.size(), 6);
    }

    @Test
    public void getSingleUserAndCheckIsValidTest() {
        User expectedUser = User.builder()
                .id(2)
                .email("janet.weaver@reqres.in")
                .firstName("Janet")
                .lastName("Weaver")
                .avatar("https://reqres.in/img/faces/2-image.jpg")
                .build();
        User actualUser = given()
                .spec(requestSpec)
                .when()
                .get(EndPoints.users + "/2")
                .then()
                .spec(responseSpecOK)
                .extract().body().jsonPath().getObject("data", User.class);

        Assert.assertNotNull(actualUser);
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void getSingleUserNotFound() {
        given()
                .spec(requestSpec)
                .when()
                .get(EndPoints.users + "/23")
                .then()
                .spec(responseSpecNotFound);
    }

}
