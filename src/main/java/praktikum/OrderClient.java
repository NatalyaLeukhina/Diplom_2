package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {


    private static final String ORDER_PATH = "api";

    @Step
    public ValidatableResponse createWithAutorization(Ingredients ingredients, String bearerToken) {
        return given()
                .headers("Authorization", bearerToken)
                .spec(getBaseSpec())
                .body(ingredients)
                .when()
                .post(ORDER_PATH + "/orders")
                .then()
                .log().body();


    }

    @Step
    public ValidatableResponse createWithUnautorization(Ingredients ingredients) {
        return given()
                .spec(getBaseSpec())
                .body(ingredients)
                .when()
                .post(ORDER_PATH + "/orders")
                .then()
                .log().body();


    }

    @Step
    public ValidatableResponse getOrdersListWithAutorization(String bearerToken) {
        return given()
                .headers("Authorization", bearerToken)
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH + "/orders")
                .then()
                .log().body();
    }


    @Step
    public ValidatableResponse getOrdersListWithoutAutorization() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH + "/orders")
                .then()
                .log().body();


    }

    @Step
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH + "/ingredients")
                .then()
                .log().body();


    }

}
