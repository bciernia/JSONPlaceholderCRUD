import groovy.json.JsonOutput;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderTestGET {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";

    @Test
    public void GetAllPosts()
    {
        Response response = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> posts = json.getList("title");

        assertEquals(100, posts.size());
    }

    @Test
    public void GetPostByIdAsPathParam()
    {
        Response response = given()
                .when()
                .pathParam("id", 3)
                .get(BASE_URL + "/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        String titleToCheck = "ea molestias quasi exercitationem repellat qui ipsa sit aut";

        assertEquals("1", json.get("userId").toString());
        assertEquals(titleToCheck, json.get("title"));
    }

    @Test
    public void GetPostByTitleAsQueryParam()
    {
        String param = "nesciunt quas odio";

        Response response = given()
                .when()
                .queryParam("title", param)
                .get(BASE_URL)
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(200, response.getStatusCode());
        assertEquals("1", json.getList("userId").get(0).toString());
        assertEquals("5", json.getList("id").get(0).toString());
    }
}
