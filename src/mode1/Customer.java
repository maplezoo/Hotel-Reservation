package mode1;


import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName + ": " + email;
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (!(o instanceof Customer))
            return false;
        Customer other = (Customer) o;
        boolean firstNameEquals = (this.firstName == null && other.firstName == null)
                || (this.firstName != null && this.firstName.equals(other.firstName));
        boolean lastNameEquals = (this.lastName == null && other.lastName == null)
                || (this.lastName != null && this.lastName.equals(other.lastName));
        boolean emailEquals = (this.email == null && other.email == null)
                || (this.email != null && this.email.equals(other.email));
        return firstNameEquals && lastNameEquals && emailEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
