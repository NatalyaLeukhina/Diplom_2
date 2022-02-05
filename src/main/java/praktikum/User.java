package praktikum;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.RandomStringUtils;
@JsonInclude(JsonInclude.Include.NON_NULL)

public class User {

    public String email;
    public String password;
    public String name;
    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public User() {

    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }

    public User setEmail (String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setName (String name) {
        this.name = name;
        return this;
    }


    public static User getWithEmailAndPassword() {

        return new User().setEmail(RandomStringUtils.randomAlphabetic(10)).setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithEmailAndName() {
        return new User().setEmail(RandomStringUtils.randomAlphabetic(10)).setName(RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithPasswordAndName() {
        return new User().setPassword(RandomStringUtils.randomAlphabetic(10)).setName(RandomStringUtils.randomAlphabetic(10));
    }

    public static User updateOnlyEmail() {
        return new User().setEmail(RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX);
    }

    public static User updateOnlyPassword() {
        return new User().setPassword(RandomStringUtils.randomAlphabetic(10));

    }

    public static User updateOnlyName() {
        return new User().setName(RandomStringUtils.randomAlphabetic(10));

    }



}
