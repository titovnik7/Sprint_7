package courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public static CourierCreate fullFieldsCourier() {
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierCreate(login, password, firstName);
    }

    public static CourierCreate withoutLoginCourier() {
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierCreate(null, password, firstName);
    }

    public static CourierCreate withoutPasswordCourier() {
        String login = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierCreate(login, null, firstName);
    }

    public static CourierCreate incorrectCourier() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        return new CourierCreate(login, password, "");
    }
}
