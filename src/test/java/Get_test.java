import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class Get_test {

    @Test
    public void getBookingIdsTest(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Response response = RestAssured.when().get("/booking");

        response.then().assertThat().statusCode(200);
        response.then().log().body();
        // Schema verification
        response.then().assertThat().body("[0]", hasKey("bookingid"));
    }

    //1. Verificar que get Booking retorna 404 status code cuando se provee un id inexistente
    @Test
    public void idInexistenteTest(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Response response = RestAssured.given().pathParam("id", "2788890809")
                .when().get("booking/{id}");

        response.then().log().body();
        //Verificacion Not Found en el body
        response.then().assertThat().statusCode(404).body(equalTo("Not Found"));

    }
    //2. Verificar que get Booking retorna 404 status code cuando se provee un id con letras
    @Test
    public void idLetrasTest(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Response response = RestAssured.given().pathParam("id", "jgajkdo")
                .when().get("booking/{id}");

        response.then().log().body();
        //Verificacion Not Found en el body
        response.then().assertThat().statusCode(404).body(equalTo("Not Found"));

    }
}
