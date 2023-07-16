package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourierLoginTest {
    private CourierCreate courier;
    private CourierClient courierClient;
    private CourierLogin courierWithoutLogin;
    private CourierLogin courierWithoutPassword;
    private CourierLogin courierIncorrectData;
    private int idCourier;

    @Before
    public void setUp() {
        courier = CourierGenerator.fullFieldsCourier();
        courierClient = new CourierClient();
        courierWithoutLogin = new CourierLogin(courier.getLogin(), "");
        courierWithoutPassword = new CourierLogin("", courier.getPassword());
        courierIncorrectData = CourierLogin.from(CourierGenerator.incorrectCourier());
    }

    @After
    public void cleanUp() {
        if (idCourier > 0)
            courierClient.deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Проверка вход в систему с помощью логина и пароля")
    public void courierLogin() {
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierLogin.from(courier));
        idCourier = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(200, statusCode);
    }

    @Test
    @DisplayName("Авторизации без указания логина")
    @Description("Проверка входа в систему без указания логина")
    public void courierWithoutLogin() {
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(courierWithoutLogin);
        int statusCode = loginResponse.extract().statusCode();
        String messageResponse = loginResponse.extract().path("message");
        assertEquals(400, statusCode);
        assertEquals("Недостаточно данных для входа", messageResponse);
    }

    @Test
    @DisplayName("Авторизации без указания пароля")
    @Description("Проверка входа в систему без указания пароля")
    public void courierWithoutPassword() {
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(courierWithoutPassword);
        int statusCode = loginResponse.extract().statusCode();
        String messageResponse = loginResponse.extract().path("message");
        assertEquals(400, statusCode);
        assertEquals("Недостаточно данных для входа", messageResponse);
    }

    @Test
    @DisplayName("Некорректные данные авторизации")
    @Description("Проверка входа в систему с некорректными данными авторизации")
    public void courierIncorrectData() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierIncorrectData);
        int statusCode = loginResponse.extract().statusCode();
        String messageResponse = loginResponse.extract().path("message");
        assertEquals(404, statusCode);
        assertEquals("Учетная запись не найдена", messageResponse);
    }
}