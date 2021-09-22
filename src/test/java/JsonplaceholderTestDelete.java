import com.fasterxml.jackson.core.JsonParser;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderTestDelete {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    private static Faker faker;

    @Test
    public void deleteRandomPost()
    {
        Response getNumberOfPosts = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = getNumberOfPosts.jsonPath();
        List<String> num = json.getList("title");

        assertEquals(100, num.size());

        faker = new Faker();
        int randomNumber = faker.number().numberBetween(1, num.size());

        Response response = given()
                .when()
                .delete(BASE_URL + "/" + randomNumber)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
}
