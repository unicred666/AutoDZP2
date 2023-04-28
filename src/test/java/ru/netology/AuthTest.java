package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.*;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginActiveUser() {
        UserInfo user = DataGenerator.Registration.generateUser("active");
        activeUser(user);
        $(".input__box>[type='text']").setValue(user.getLogin());
        $(".input__box>[type='password']").setValue(user.getPassword());
        $(".button").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldLoginBlockedUser() {
        UserInfo user = DataGenerator.Registration.generateUser("blocked");
        blockedUser(user);
        $(".input__box>[type='text']").setValue(user.getLogin());
        $(".input__box>[type='password']").setValue(user.getPassword());
        $(".button").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldWrongLogin() {
        UserInfo user = Registration.generateUser("active");
        activeUser(user);
        var anotherLogin = generateLogin();
        $(".input__box>[type='text']").setValue(anotherLogin);
        $(".input__box>[type='password']").setValue(user.getPassword());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldWrongPassword() {
        UserInfo user = Registration.generateUser("active");
        activeUser(user);
        var anotherPassword = generatePassword();
        $(".input__box>[type='text']").setValue(user.getLogin());
        $(".input__box>[type='password']").setValue(anotherPassword);
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}