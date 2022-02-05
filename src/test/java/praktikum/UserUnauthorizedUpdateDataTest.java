package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;


@RunWith(Parameterized.class)
public class UserUnauthorizedUpdateDataTest {

        public User user;
        private UserClient userClient;
        public int expectedStatus;
        public String expectedMessage;
        public boolean expectedAnswer;
        public String userAccessToken;

        public UserUnauthorizedUpdateDataTest(User user, boolean expectedAnswer, int expectedStatus, String expectedMessage) {
            this.user = user;
            this.expectedAnswer = expectedAnswer;
            this.expectedStatus = expectedStatus;
            this.expectedMessage = expectedMessage;

        }

        @Parameterized.Parameters

        public static Object[][] getTestData() {
            return new Object[][] {
                    {User.updateOnlyName(), false, 401, "You should be authorised"},
                    {User.updateOnlyPassword(), false, 401, "You should be authorised"},
                    {User.updateOnlyEmail(), false, 401, "You should be authorised"}
            };

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
        @DisplayName("Check creating new user without email or password or name")
        @Description("Creating new user with email and password without name, with email and name without password, with password and name without email")
        public void  invalidRequestIsNotAllowedWithLoginOnly() {

            ValidatableResponse response = userClient.update(user);

            boolean expectedAnswer = response.extract().path("success");
            int statusCode = response.extract().statusCode();
            String message = response.extract().path("message");
            //String userInfo = response.extract().path("user.name", "user.password");

            assertFalse("User is updated", expectedAnswer);
            assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));
            assertThat("Message is incorrect", message, equalTo(expectedMessage));

        }

        }



