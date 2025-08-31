import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class Post_test {

    //3.Verificar que Post Booking retorna 200 status code al crearlo con payload valido
    @Test
    public void PostCorrectTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Booking booking = new Booking();
        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(2);
        booking.setDepositpaid(false);

        Booking.BookingDates bookingDates = new Booking.BookingDates();
        bookingDates.setCheckin("2016-07-08");
        bookingDates.setCheckout("2017-07-08");

        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);
        System.out.println(payload);

        Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept", "application/json")
                .header("Connection", "keep-alive")
                .body(payload)
                .when().post("/booking");

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("booking.firstname",equalTo(booking.getFirstname()));
        response.then().assertThat().body("booking.lastname", equalTo(booking.getLastname()));
        response.then().assertThat().body("booking.totalprice", equalTo(booking.getTotalprice()));
        response.then().assertThat().body("booking.depositpaid", equalTo(booking.isDepositpaid()));
        response.then().assertThat().body("booking.bookingdates.checkin", equalTo(booking.getBookingdates().getCheckin()));
        response.then().assertThat().body("booking.bookingdates.checkout", equalTo(booking.getBookingdates().getCheckout()));
        response.then().assertThat().body("booking.additionalneeds", equalTo(booking.getAdditionalneeds()));
        response.then().assertThat().body("bookingid", notNullValue());
        response.then().log().body();
    }

    //4.Verificar que Post Booking retorna 400 status code con el espacio totalprice con numeros negativos
    @Test
    public void TotalPriceNegativoTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Booking booking = new Booking();
        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(-10);
        booking.setDepositpaid(false);

        Booking.BookingDates bookingDates = new Booking.BookingDates();
        bookingDates.setCheckin("2016-07-08");
        bookingDates.setCheckout("2017-07-08");

        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);
        System.out.println(payload);

        Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept", "application/json")
                .header("Connection", "keep-alive")
                .body(payload)
                .when().post("/booking");

        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("booking.firstname",equalTo(booking.getFirstname()));
        response.then().assertThat().body("booking.lastname", equalTo(booking.getLastname()));
        response.then().assertThat().body("booking.totalprice", equalTo(booking.getTotalprice()));
        response.then().assertThat().body("booking.depositpaid", equalTo(booking.isDepositpaid()));
        response.then().assertThat().body("booking.bookingdates.checkin", equalTo(booking.getBookingdates().getCheckin()));
        response.then().assertThat().body("booking.bookingdates.checkout", equalTo(booking.getBookingdates().getCheckout()));
        response.then().assertThat().body("booking.additionalneeds", equalTo(booking.getAdditionalneeds()));
        response.then().assertThat().body("bookingid", notNullValue());
        response.then().log().body();
    }
    //5. Verificar que Post Booking retorna 400 status code cuando checkin y checkout tiene fechas invalidas
    @Test
    public void CheckinCkecoutFechasInvalidasTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        Booking booking = new Booking();
        booking.setFirstname("James");
        booking.setLastname("Brown");
        booking.setTotalprice(10);
        booking.setDepositpaid(false);

        Booking.BookingDates bookingDates = new Booking.BookingDates();
        bookingDates.setCheckin("2016-07-08");
        bookingDates.setCheckout("2015-07-08");

        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds("Breakfast");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);
        System.out.println(payload);

        Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .header("Accept", "application/json")
                .header("Connection", "keep-alive")
                .body(payload)
                .when().post("/booking");

        response.then().assertThat().statusCode(400);
        response.then().assertThat().body("booking.firstname",equalTo(booking.getFirstname()));
        response.then().assertThat().body("booking.lastname", equalTo(booking.getLastname()));
        response.then().assertThat().body("booking.totalprice", equalTo(booking.getTotalprice()));
        response.then().assertThat().body("booking.depositpaid", equalTo(booking.isDepositpaid()));
        response.then().assertThat().body("booking.bookingdates.checkin", equalTo(booking.getBookingdates().getCheckin()));
        response.then().assertThat().body("booking.bookingdates.checkout", equalTo(booking.getBookingdates().getCheckout()));
        response.then().assertThat().body("booking.additionalneeds", equalTo(booking.getAdditionalneeds()));
        response.then().assertThat().body("bookingid", notNullValue());
        response.then().log().body();
    }
}

