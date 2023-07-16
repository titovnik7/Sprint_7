package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList() {
        ValidatableResponse response = orderClient.getOrderList();
        int statusCode = response.extract().statusCode();
        assertEquals(200, statusCode);
        ArrayList list = response.extract().path("orders");
        assertThat(list, notNullValue());
    }
}