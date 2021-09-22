import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderTestPOST {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";

    @Test
    public void AddNewPost()
    {
        JSONObject newPost = new JSONObject();

        newPost.put("userId", 1);
        newPost.put("title", "Testowy tytuł");
        newPost.put("body", "Testowe body");

        Response response = given()
                .contentType("application/json")
                .body(newPost.toString())
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("101", json.get("id").toString());
        assertEquals("1", json.get("userId").toString());
        assertEquals("Testowy tytuł", json.get("title"));
        assertEquals("Testowe body", json.get("body"));
    }
}
