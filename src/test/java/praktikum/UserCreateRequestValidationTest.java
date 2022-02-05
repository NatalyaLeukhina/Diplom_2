package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;


@RunWith(Parameterized.class)
public class UserCreateRequestValidationTest {
    public User user;
    public int expectedStatus;
    public String expectedMessage;
    public boolean expectedAnswer;
    public String userAccessToken;

    public UserCreateRequestValidationTest(User user, boolean expectedAnswer, int expectedStatus, String expectedMessage) {
        this.user = user;
        this.expectedAnswer = expectedAnswer;
        this.expectedStatus = expectedStatus;
        this.expectedMessage = expectedMessage;

    }

    @Parameterized.Parameters

    public static Object[][] getTestData() {
        return new Object[][] {
                {User.getWithEmailAndPassword(), false, 403, "Email, password and name are required fields"},
                {User.getWithEmailAndName(), false, 403, "Email, password and name are required fields"},
                {User.getWithPasswordAndName(), false, 403, "Email, password and name are required fields"}
        };

    }

    @After
    public void tearDown() {
        UserClient.delete(userAccessToken);
    }

    @Test
    @DisplayName("Check creating new user without email or password or name")
    @Description("Creating new user with email and password without name, with email and name without password, with password and name without email")
    public void  invalidRequestIsNotAllowedWithLoginOnly() {

        ValidatableResponse response = new UserClient().create(user);

        boolean expectedAnswer = response.extract().path("success");
        String message = response.extract().path("message");
        int statusCode = response.extract().statusCode();

        assertFalse("User is created", expectedAnswer);
        assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));
        assertThat("Message is incorrect", message, equalTo(expectedMessage));

    }

}