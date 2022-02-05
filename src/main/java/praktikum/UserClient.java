package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredClient{

    private static final String USER_PATH = "api/auth";

    @Step
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then()
                .log().body();
    }

    @Step
    public ValidatableResponse login (UserCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_PATH + "/login")
                .then()
                .log().body();

    }

    @Step
    public ValidatableResponse update (User user){
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch(USER_PATH + "/user")
                .then()
                .log().body();
    }

    @Step
    public ValidatableResponse updateWithAutorization (User user, String bearerToken){
        return given()
                .headers("Authorization", bearerToken)
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch(USER_PATH + "/user")
                .then()
                .log().body();
    }


    @Step
    public static void delete(String userAccessToken) {
        if (userAccessToken == null) {
            return;
        }
                given()
                .spec(getBaseSpec())
                .auth().oauth2(userAccessToken)
                .when()
                .delete(USER_PATH + "/user")
                .then();

    }


}



