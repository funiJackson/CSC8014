package model;

import java.util.Objects;

/**
 * Class of a person's name consisting of a first and last name.
 * This class is designed as an immutable object.
 * @author Fandi Zhou
 */
public final class Name {

    private final String firstName, lastName;

    /**
     * Constructs a new Name object.
     * @param firstName the person's first name.
     * @param lastName  the person's last name.
     */
    public Name (String firstName, String lastName) {
        this.firstName= firstName;
        this.lastName = lastName;
    }

    /**
     * Override equals methods to compares this model.Name to another object for equality.
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument and vice versa.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){return true;}//Check if there are same object.
        if (obj == null){return false;}//Check if the param we pass is null.
        if (!(obj instanceof Name)){return false;}//Check if they belong to same class.
         Name name = (Name) obj;//Downcasting obj to name so it can compare.

        //Check if the first and last name are match.
        return Objects.equals(firstName, name.firstName) && Objects.equals(lastName, name.lastName);
    }

    /**
     * This method is essential for using Name objects in has-based collections
     * It ensures that equal objects (same first/last name) produce the same hash code.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    /**
     * Get the first name.
     * @return the first name string.
     */
    public String getFirstName() {return firstName;}

    /**
     * Get the last name.
     * @return the last name string.
     */
    public String getlastName() {return lastName;}

    /**
     * Returns a string representation of the name.
     * @return the name in Firstname + Lastname format.
     */
    @Override
    public String toString() {return firstName + " " + lastName;}
}

