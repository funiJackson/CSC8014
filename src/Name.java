import java.util.Objects;

public final class Name {


    private final String firstName, lastName;

    public Name (String firstName, String lastName) {
        this.firstName= firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){return true;}
        if (obj == null){return false;}
        if (getClass() != obj.getClass()){return false;}
         Name name = (Name) obj;

        return Objects.equals(firstName, name.firstName) && Objects.equals(lastName, name.lastName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public String getFirstName() {return firstName;}
    public String getlastName() {return lastName;}
    public String toString() {return firstName + " " + lastName;}
}

