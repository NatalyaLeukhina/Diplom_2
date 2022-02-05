package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;


public class UserLoginValidationTest {
    public User user;
    private UserClient userClient;
    private String userAccessToken;

    private static UserCredentials getInvalidData() {
        final String email = "test-data100@yandex.ru";
        final String password = "password100";
        return new UserCredentials(email, password);
    }


    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
        userClient.create(user);
    }

    @After
    public void tearDown() {
        UserClient.delete(userAccessToken);
    }

    @Test
    @DisplayName("Check user authorization with invalid data")
    @Description("Authorize the user with invalid email and password")
    public void checkUserCanBeLoginWithInvalidDataTest() {

        ValidatableResponse response = userClient.login(getInvalidData());
        userAccessToken = response.extract().path("accessToken");


        int statusCode = response.extract().statusCode();
        boolean isUserLogin = response.extract().path("success");
        String invalidDataMessage = "email or password are incorrect";
        String expectedMessage = response.extract().path("message");


        assertThat("Status code is incorrect", statusCode, equalTo(401));
        assertFalse("User is login", isUserLogin);
        assertThat("Ожидаемый текст ошибки: " + invalidDataMessage + ". Фактический: " + expectedMessage,
                expectedMessage, (equalTo(invalidDataMessage)));

        }
}

