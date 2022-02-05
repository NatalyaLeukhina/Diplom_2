package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;



public class UserCreateTest {

    public User user;
    public UserClient userClient;
    public String userAccessToken;


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
    @DisplayName("Check creating new user")
    @Description("Creating new user")
    public void checkUserCanBeCreatedTest() {
        User user = User.getRandom();


        ValidatableResponse response = userClient.create(user);

        int statusCode = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");
        userAccessToken = response.extract().path("accessToken");


        assertTrue("User is not created", isUserCreated);
        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("User access token is incorrect", userAccessToken, is(not(0)));
    }

    @Test
    @DisplayName("Check duplicate user can not be created")
    @Description("Creating a user with the name, email and password of an existing user")
    public void duplicateUserCannotBeCreated() {
        userClient.create(user);
        ValidatableResponse response = userClient.create(user);

        int statusCode = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");
        String thisNameIsUsed = "User already exists";

        String expectedMessage = response.extract().path("message");

        assertFalse("User is created", isUserCreated);

        assertThat("Ожидаемый текст ошибки: " + thisNameIsUsed + ". Фактический: " + expectedMessage,
                expectedMessage, (equalTo(thisNameIsUsed)));

        assertThat("Status code is incorrect", statusCode, equalTo(403));

    }


}

