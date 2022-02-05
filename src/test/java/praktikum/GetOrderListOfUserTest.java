package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class GetOrderListOfUserTest {
    public User user;
    public UserClient userClient;
    public String userAccessToken;
    public OrderClient orderClient;
    public String orderIngredients;
    List<String> ingredients = new ArrayList<>();


    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
        orderClient = new OrderClient();
        ingredients = new OrderClient().getIngredients().extract().path("data._id");
        orderIngredients = ingredients.get(1);

    }

    @After
    public void tearDown() {
        UserClient.delete(userAccessToken);
    }

    @Test
    @DisplayName("Check get order list by autorized user")
    @Description("Get order list by autorized user")
    public void  getOrderListByAutorizedUser() {
        userAccessToken = userClient.create(user).extract().path("accessToken");

        Ingredients orderData = new Ingredients(orderIngredients);
        ValidatableResponse createOrders = new OrderClient().createWithAutorization(orderData, userAccessToken);
        int statusCodeCreateOrders = createOrders.extract().statusCode();
        assertThat("Status code is incorrect", statusCodeCreateOrders, equalTo(200));

        ValidatableResponse getOrders = orderClient.getOrdersListWithAutorization(userAccessToken);
        int statusCodeGetOrders = getOrders.extract().statusCode();
        boolean isOrderGet = getOrders.extract().path("success");

        assertThat("Status code of create orders is incorrect", statusCodeGetOrders, equalTo(200));
        assertTrue("Orders is not get", isOrderGet);

    }

    @Test
    @DisplayName("Check get order list by unautorized user")
    @Description("Get order list by unautorized user")
    public void  getOrderListByUnautorizedUser() {

        ValidatableResponse response = orderClient.getOrdersListWithoutAutorization();
        int statusCodeGetOrders = response.extract().statusCode();
        String expectedMessage = response.extract().path("message");
        String unautorizedMessage = "You should be authorised";
        assertThat("Status code of create orders is incorrect", statusCodeGetOrders, equalTo(401));

        assertThat("Ожидаемый текст ошибки: " + unautorizedMessage + ". Фактический: " + expectedMessage,
                expectedMessage, (equalTo(unautorizedMessage)));

    }
}
