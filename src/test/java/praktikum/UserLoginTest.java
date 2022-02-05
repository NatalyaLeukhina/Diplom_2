package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class UserLoginTest {

    public User user;
    private UserClient userClient;
    private String userAccessToken;
    private String userRefreshToken;
    public String userInfo;

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
    @DisplayName("Check user authorization")
    @Description("Authorize the user")
    public void checkExistingUserCanBeLogInTest() {

        ValidatableResponse response = userClient.login(UserCredentials.from(user));


        int statusCode = response.extract().statusCode();
        boolean isUserLogin = response.extract().path("success");
        userInfo = response.extract().path("user.name", "user.password");
        userAccessToken = response.extract().path("accessToken");
        userRefreshToken = response.extract().path("refreshToken");


        assertTrue("User is not login", isUserLogin);
        assertThat("Status code is incorrect", statusCode, equalTo(200));

        assertThat("User access token is incorrect", userAccessToken, is(not(0)));
        assertThat("User refresh token is incorrect", userRefreshToken, is(not(0)));
        assertThat("User email and password is incorrect", userInfo, is(not(0)));

    }
    }



