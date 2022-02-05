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



public class UserAutorizedUpdateDataTest {
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
    @DisplayName("Check update name of autorized user")
    @Description("Updating name of autorized user")
    public void  updateOnlyNameOfAutorizedUser() {

        userAccessToken = userClient.create(user).extract().path("accessToken");
        ValidatableResponse response = userClient.updateWithAutorization(User.updateOnlyName(), userAccessToken);

        boolean expectedAnswer = response.extract().path("success");
        int statusCode = response.extract().statusCode();
        String userInfo = response.extract().path("user.name", "user.password");

        assertTrue("User is not updated", expectedAnswer);
        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("User name is not update", userInfo, is(not(0)));

        }

    @Test
    @DisplayName("Check update password of autorized user")
    @Description("Updating password of autorized user")
    public void  updateOnlyPasswordOfAutorizedUser() {

        userAccessToken = userClient.create(user).extract().path("accessToken");
        ValidatableResponse response = userClient.updateWithAutorization(User.updateOnlyPassword(), userAccessToken);

        boolean expectedAnswer = response.extract().path("success");
        int statusCode = response.extract().statusCode();
        String userInfo = response.extract().path("user.name", "user.password");

        assertTrue("User is not updated", expectedAnswer);
        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("User name is not update", userInfo, is(not(0)));

    }

    @Test
    @DisplayName("Check update email of autorized user")
    @Description("Updating email of autorized user")
    public void  updateOnlyEmailOfAutorizedUser() {

        userAccessToken = userClient.create(user).extract().path("accessToken");
        ValidatableResponse response = userClient.updateWithAutorization(User.updateOnlyEmail(), userAccessToken);

        boolean expectedAnswer = response.extract().path("success");
        int statusCode = response.extract().statusCode();
        String userInfo = response.extract().path("user.name", "user.password");

        assertTrue("User is not updated", expectedAnswer);
        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("User name is not update", userInfo, is(not(0)));

    }

    }

