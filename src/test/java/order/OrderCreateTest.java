package order;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private OrderClient orderClient;
    private static OrderCreate order;
    int track;
    int statusCode;
    Faker faker = new Faker(Locale.forLanguageTag("ru"));
    private final String firstName = faker.name().firstName();
    private final String lastName = faker.name().lastName();
    private final String address = faker.address().fullAddress().substring(7);
    private final String metroStation = faker.regexify("[1-10]");
    private final String phone = faker.regexify("(8|\\+7)9\\d{9}");
    private final String rentTime = faker.regexify("[1-9]");
    private final String deliveryDate = faker.date().future(1, TimeUnit.HOURS).toInstant().toString();
    private final String comment = faker.name().username();
    private final String[] color;

    public OrderCreateTest(OrderCreate order, String[] color, int statusCode) {
        this.order = order;
        this.statusCode = statusCode;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][]{
                {order, new String[]{"GREY"}, 201},
                {order, new String[]{"BLACK"}, 201},
                {order, null, 201},
                {order, new String[]{"BLACK", "GREY"}, 201}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp(){
        orderClient.deleteOrder(track);
    }

    @Test
    @DisplayName("Создание заказа")
    public void orderCreated(){
        OrderCreate order = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse responseCreate = orderClient.createOrder(order);
        int actualStatusCode = responseCreate.extract().statusCode();
        track = responseCreate.extract().path("track");
        assertThat(track, notNullValue());
        assertEquals(statusCode,actualStatusCode);
    }
}