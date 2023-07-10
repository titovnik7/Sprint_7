package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourierCreateTest {
    private CourierCreate courier;
    private CourierClient courierClient;
    private CourierCreate courierWithoutLogin;
    private CourierCreate courierWithoutPassword;
    private CourierCreate sameCourier;
    private int id;

    @Before
    public void setUp() {
        courier = CourierGenerator.fullFieldsCourier();
        courierClient = new CourierClient();
        courierWithoutLogin = CourierGenerator.withoutLoginCourier();
        courierWithoutPassword = CourierGenerator.withoutPasswordCourier();
        sameCourier = new CourierCreate(courier.getLogin(), courier.getPassword(), courier.getFirstName());
    }

    @After
    public void cleanUp(){
        if (id > 0)
            courierClient.deleteCourier(id);
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяем, что запрос возвращает правильные код ответ и тело ok: true")
    public void courierCreated(){
        ValidatableResponse responseCreate = courierClient.createCourier(courier);
        int statusCode = responseCreate.extract().statusCode();
        boolean messageResponse = responseCreate.extract().path("ok");
        assertEquals(201, statusCode);
        assertTrue(messageResponse);
        ValidatableResponse responseLogin = courierClient.loginCourier(CourierLogin.from(courier));
        id = responseLogin.extract().path("id");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверяем, что нельзя создать курьера без логина")
    public void courierWithoutLogin(){
        ValidatableResponse response = courierClient.createCourier(courierWithoutLogin);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(400, statusCode);
        assertEquals("Недостаточно данных для создания учетной записи", messageResponse);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверяем, что нельзя создать курьера без пароля")
    public void courierWithoutPassword(){
        ValidatableResponse response = courierClient.createCourier(courierWithoutPassword);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(400, statusCode);
        assertEquals("Недостаточно данных для создания учетной записи", messageResponse);
    }
    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверяем, что нельзя создать двух одинаковых курьеров")
    public void courierCreatedTwice(){
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.createCourier(sameCourier);
        int statusCode = response.extract().statusCode();
        String messageResponse = response.extract().path("message");
        assertEquals(409, statusCode);
        assertEquals("Этот логин уже используется. Попробуйте другой.", messageResponse);
        ValidatableResponse responseLogin = courierClient.loginCourier(CourierLogin.from(courier));
        id = responseLogin.extract().path("id");
    }
}