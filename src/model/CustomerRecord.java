package model;

import java.util.Date;
import java.util.Objects;

/**
 * Class of a registered customer in the vehicle-hire system.
 * This class stores immutable personal details of a customer about their name, date of birth and licensing information.
 * It also manages a unique Customer ID for each instance.
 *
 * @author Fandi Zhou
 */
public class CustomerRecord {

    private final Name name;//Full name of the customer including first and last name.
    private final Date birthDate;
    private final boolean haveC_licence;
    private final int customerID;

    private static int nextID = 1;//A static counter used to generate unique id for customer, starts at 1 and increments with every new model.CustomerRecord created.

    /**
     * Constructs a new model.CustomerRecord.
     * Initializes the customer's personal details and automatically assigns a unique Customer ID.
     *
     * @param name          the model.Name object representing the customer's full name.
     * @param birthDate     the customer's date of birth.
     * @param haveC_licence true if the customer has a commercial license and false otherwise.
     */
    public CustomerRecord(Name name, Date birthDate, boolean haveC_licence) {
        this.name = name;
        this.birthDate = new Date(birthDate.getTime());//Immutable defense.
        this.haveC_licence = haveC_licence;
        this.customerID = nextID++;
    }


    /**
     * Retrieves the customer's date of birth.
     * @return the Date of birth.
     */
    public Date getBirthDate() {
        return new Date(birthDate.getTime());//Set Immutability so client side can not change it.
    }

    /**
     * Checks if the customer possesses a commercial driver's license.
     * @return code true if the customer has a C-license or vice versa.
     */
    public boolean isHaveC_licence() {
        return haveC_licence;
    }

    /**
     * Get the customer's name object.
     * @return the model.Name object.
     */
    public Name getName(){
        return name;
    }

    /**
     * Get the unique ID assigned to this customer.
     * @return the customer ID as an integer.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Override methods to compares this customer record to another object for equality.
     * Two model.CustomerRecord objects are considered equal if they have the same model.Name and the same Date of Birth.
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument and false otherwise.
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj){return true;}// If both references point to the same object in memory hence they are equal.
        if (obj == null){return false;}//If the other object is null, they cannot be equal.

        if (!(obj instanceof CustomerRecord)) {return false;}//Check if they belong to same class.
        CustomerRecord that = (CustomerRecord) obj;//Downcasting obj to model.CustomerRecord so it can compare later.

        return Objects.equals(name, that.name) && Objects.equals(birthDate, that.birthDate);// Compare the name and birthDate fields see if it is matched.
    }


    /**
     * Generates a hash code for this customer record.
     * This method must correspond to equals Object. Since equality depends on model.Name and BirthDate, the hash code must be generated from these same fields.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate);
    }


}
