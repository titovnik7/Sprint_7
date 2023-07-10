package order;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient {
    public static final String ORDER_API = "/api/v1/orders/";
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

    public ValidatableResponse createOrder(OrderCreate order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(order)
                .when()
                .post(ORDER_API)
                .then().log().all();
    }

    public ValidatableResponse getOrderList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .when()
                .get(ORDER_API)
                .then().log().all();
    }

    public ValidatableResponse deleteOrder(int track) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(track)
                .when()
                .put(ORDER_API + "cancel")
                .then().log().all();
    }
}
