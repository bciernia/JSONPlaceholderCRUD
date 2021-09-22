import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderTestPUTPATCH {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    private static Faker faker;

    private JSONObject updatedPost;
    private int fakeUserId;
    private String fakeTitle;
    private String fakeDescription;

    @BeforeAll
    public static void beforeAll()
    {
        faker = new Faker();

    }

    @BeforeEach
    public void beforeEach()
    {
        fakeUserId = faker.number().numberBetween(1, 10);
        fakeTitle = faker.name().title();
        fakeDescription = faker.lorem().sentence();

        updatedPost = new JSONObject();
    }

    @Test
    public void updatePost()
    {
        updatedPost.put("userId", fakeUserId);
        updatedPost.put("title", fakeTitle);
        updatedPost.put("body", fakeDescription);

        Response response = given()
                .contentType("application/json")
                .body(updatedPost.toString())
                .pathParam("id", 1)
                .when()
                .put(BASE_URL + "/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(Integer.toString(fakeUserId), json.get("userId").toString());
        assertEquals(fakeTitle, json.get("title"));
        assertEquals(fakeDescription, json.get("body"));
    }

    @Test
    public void updateTitleInPost()
    {
        updatedPost.put("title", fakeTitle);

        Response response = given()
                .contentType("application/json")
                .body(updatedPost.toString())
                .when()
                .patch(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakeTitle, json.get("title"));
    }
}
