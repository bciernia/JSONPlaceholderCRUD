import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class JsonplaceholderEmailCheck {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private boolean flag = false;

    @Test
    public void IsAnyEmailEndedOnPLWithForLoop()
    {
        Response response = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> emailList = json.getList("email");

        for(int i=0; i<emailList.size();i++)
        {
            if(emailList.get(i).endsWith(".pl"))
            {
                System.out.println("There is email ended on .pl");
                flag = true;
                break;
            }
        }

        if(!flag)
            System.out.println("No email ends with \".pl\"");
    }

    @Test
    public void IsAnyEmailEndedOnPLWithStream()
    {
        Response response = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> emailList = json.getList("email");

        boolean flag = emailList.stream().anyMatch(name -> name.endsWith(".pl"));

        if(!flag)
            System.out.println("No email ends with \".pl\"");
        else
            System.out.println("There is email ended on .pl");
    }

}
