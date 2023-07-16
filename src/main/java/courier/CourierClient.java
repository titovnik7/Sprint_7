package courier;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {
    public static final String COURIER_API = "/api/v1/courier/";
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

    public ValidatableResponse createCourier(CourierCreate courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post(COURIER_API)
                .then().log().all();
    }

    public ValidatableResponse loginCourier(CourierLogin login) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(login)
                .when()
                .post(COURIER_API + "login")
                .then().log().all();
    }

    public ValidatableResponse deleteCourier(int id) {
        String strId = Integer.toString(id);
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .when()
                .delete(COURIER_API + strId)
                .then().log().all();
    }
}
