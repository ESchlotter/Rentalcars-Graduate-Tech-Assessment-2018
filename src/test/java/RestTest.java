import com.Rentalcars.RentalcarsApplication;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by Eduard Schlotter on 08/01/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RentalcarsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTest {
    @LocalServerPort
    int randomServerPort;


    @Before
    public void setUp() {
        RestAssured.port =  randomServerPort;
        System.out.println("Setup");
    }

    @Test
    public void pricesCorrectOrder() {
        when().
                get("/prices").
                then().
                statusCode(HttpStatus.SC_OK).
                body(startsWith("Ascending Price Listing <br> <br> \n" +
                                "ChevroletSpark - 120.16 <br> "),
                     endsWith("VW Sharan - 789.75 <br> \n"));
    }

    @Test
    public void specsErrorCheck() {
        when().
                get("/specs").
                then().
                statusCode(HttpStatus.SC_OK).
                body(not(containsString("null"))); // If null then invalid SIPP read
    }

    @Test
    public void ratingsErrorCheck() {
        when().
                get("/ratings").
                then().
                statusCode(HttpStatus.SC_OK).
                body(not(containsString("null")));
    }

    @Test
    public void ratingsCorrectOrder() {
        when().
                get("/ratings").
                then().
                statusCode(HttpStatus.SC_OK).
                body(startsWith("Highest Rated Suppliers <br> <br> \n" +
                                "Ford Focus - Compact - Hertz - 8.9 <br> "),
                        endsWith("Kia Picanto - Mini - Hertz - 8.9 <br> \n"));
    }

    @Test
    public void scoresCorrectOrder() {
        when().
                get("/scores").
                then().
                statusCode(HttpStatus.SC_OK).
                body(startsWith("Highest Scoring Vehicles <br> <br> \n" +
                                "Nissan Juke - 7 - 8.9 - 15.9 <br>"),
                        endsWith("Kia Picanto - 1 - 8.9 - 9.9 <br> \n"));
    }

    /* Not Null */
    @Test
    public void pricesNotNull() {
        when().
                get("/prices").
                then().
                statusCode(HttpStatus.SC_OK).
                body(notNullValue());
    }

    @Test
    public void specsNotNull() {
        when().
                get("/specs").
                then().
                statusCode(HttpStatus.SC_OK).
                body(notNullValue());
    }
    @Test
    public void ratingsNotNull() {
        when().
                get("/ratings").
                then().
                statusCode(HttpStatus.SC_OK).
                body(notNullValue());
    }
    @Test
    public void scoresNotNull() {
        when().
                get("/scores").
                then().
                statusCode(HttpStatus.SC_OK).
                body(notNullValue());
    }
}
