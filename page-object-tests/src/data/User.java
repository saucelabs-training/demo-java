package data;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String zipcode;

    public static User validUser() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("username", "standard_user");
        data.put("password", "secret_sauce");
        return new User(data);
    }

    public static User blankPassword() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("password", "");
        return new User(data);
    }

    public static User blankFirstName() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("firstname", "");
        return new User(data);
    }

    public static User blankLastName() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("lastname", "");
        return new User(data);
    }

    public static User blankZipCode() {
        Map<String, String> data = new HashMap<String, String>();
        data.put("zipcode", "");
        return new User(data);
    }

    public static User shippingInfo() {
        return new User();
    }

    public static User invalidUser() {
        return new User();
    }

    public static User randomUser() {
        return new User();
    }

    public User() {
        Faker faker = new Faker();
        this.username = faker.internet().emailAddress();
        this.password = faker.internet().password();
        this.firstname = faker.address().firstName();
        this.lastname = faker.address().lastName();
        this.zipcode = faker.address().zipCode();
    }

    public User(Map<String, String> data) {
        username = data.get("username");
        password = data.get("password");
        firstname = data.get("firstname");
        lastname = data.get("lastname");
        zipcode = data.get("zipcode");

        Faker faker = new Faker();
        this.username = (username != null) ? username : faker.internet().emailAddress();
        this.password = (password != null) ? password : faker.internet().password();
        this.firstname = (firstname != null) ? firstname : faker.address().firstName();
        this.lastname = (lastname != null) ? lastname : faker.address().lastName();
        this.zipcode = (zipcode != null) ? zipcode : faker.address().zipCode();
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getZipcode() { return zipcode; }

}
