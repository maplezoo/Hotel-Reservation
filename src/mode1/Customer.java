package mode1;

import java.util.Map;
import java.util.regex.Pattern;

public class Customer implements Map<String, Customer> {
    String firstName;
    String lastName;
    String email;

    private final String  emailRegex = "^(.+)@(.+).(.+)$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String first, String last, String email){
        if (!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Error, invalid email.");
        }
        this.firstName = first;
        this.lastName = last;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return firstName + lastName;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName + ": " + email;
    }
}
