package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;

public class OrderUnautorizedUserTest {

    public String orderIngredients;
    List<String> ingredients = new ArrayList<>();

    @Test
    @DisplayName("Check that the order will not be created by unautorized user")
    @Description("Created order with ingredients by unautorized user")
    public void  createOrderByUnautorizedUser() {

        ingredients = new OrderClient().getIngredients().extract().path("data._id");
        orderIngredients = ingredients.get(1);
        Ingredients createOrder = new Ingredients(orderIngredients);

        ValidatableResponse response = new OrderClient().createWithUnautorization(createOrder);
        int statusCode = response.extract().statusCode();
        boolean isOrderCreate = response.extract().path("success");
        String expectedMessage = response.extract().path("message");
        String invalidDataMessage = "You should be authorised";

        assertThat("Status code is incorrect", statusCode, equalTo(400));
        assertFalse("User is login", isOrderCreate);

        assertThat("Ожидаемый текст ошибки: " + invalidDataMessage + ". Фактический: " + expectedMessage,
                expectedMessage, (equalTo(invalidDataMessage)));


    }
}
