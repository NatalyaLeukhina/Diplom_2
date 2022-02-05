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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderAutorizedUserTest {

   public User user;
   public UserClient userClient;
   public String userAccessToken;
   public String orderIngredients;
   List<String> ingredients = new ArrayList<>();


    @Before
   public void setUp() {
       user = User.getRandom();
       userClient = new UserClient();


   }

    @After
    public void tearDown() {
        UserClient.delete(userAccessToken);
    }

    @Test
    @DisplayName("Check that the order will not be created with invalid data by autorized user")
    @Description("Created order with invalid data by autorized user")
    public void  createOrderWithInvalidDataByAutorizedUser() {

        String userAccessToken = userClient.create(user).extract().path("accessToken");
        Ingredients orderIngredients = new Ingredients("blaBlaBla");
        ValidatableResponse response = new OrderClient().createWithAutorization(orderIngredients, userAccessToken);
        int statusCode = response.extract().statusCode();

        assertThat("Status code is incorrect", statusCode, equalTo(500));


    }
    @Test
    @DisplayName("Check that the order will be created by autorized user")
    @Description("Created order with ingredients by autorized user")
    public void  createOrderByAutorizedUser() {

        ingredients = new OrderClient().getIngredients().extract().path("data._id");
        orderIngredients = ingredients.get(1);
        String userAccessToken = userClient.create(user).extract().path("accessToken");
        Ingredients createOrder = new Ingredients(orderIngredients);

        ValidatableResponse response = new OrderClient().createWithAutorization(createOrder, userAccessToken);
        int statusCode = response.extract().statusCode();
        String orderInfo = response.extract().path("name", "order.number");
        boolean isOrderCreate = response.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("Order name and number is empty", orderInfo, is(not(0)));
        assertTrue("Order is not created", isOrderCreate);


    }

    @Test
    @DisplayName("Check that the order will not be created without ingredients by autorized user")
    @Description("Created order without ingredients by autorized user")
    public void  createOrderWithoutIngredientsByAutorizedUser() {

        String userAccessToken = userClient.create(user).extract().path("accessToken");
        Ingredients createOrder = new Ingredients(null);

        ValidatableResponse response = new OrderClient().createWithAutorization(createOrder, userAccessToken);

        int statusCode = response.extract().statusCode();
        assertThat("Status code is incorrect", statusCode, equalTo(400));

        boolean isOrderCreate = response.extract().path("success");
        assertFalse("User is login", isOrderCreate);

        String expectedMessage = response.extract().path("message");
        String invalidDataMessage = "Ingredient ids must be provided";

        assertThat("Ожидаемый текст ошибки: " + invalidDataMessage + ". Фактический: " + expectedMessage,
                expectedMessage, (equalTo(invalidDataMessage)));



    }







}
