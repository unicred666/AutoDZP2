package ru.netology;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Value;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void activeUser(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(new Gson().toJson(user))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static void blockedUser(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(new Gson().toJson(user))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static Faker faker = new Faker();

    public static String generateLogin() {
        return faker.name().username();
    }

    public static String generatePassword() {
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String status) {
            return new UserInfo(generateLogin(), generatePassword(), status);
        }
    }

    @Value
    @AllArgsConstructor
    public static class UserInfo {
        String login;
        String password;
        String status;
    }
}