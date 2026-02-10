
import java.util.Date;

public class CustomerRecord {

    private final Name name;
    private final Date birthDate;
    private final boolean haveC_licence;
    private final int customerID;

    private static int nextID = 1;

    public CustomerRecord(Name name, Date birthDate, boolean haveC_licence) {
        this.name = name;
        this.birthDate = birthDate;
        this.haveC_licence = haveC_licence;
        this.customerID = nextID++;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public boolean isHaveC_licence() {
        return haveC_licence;
    }

    public Name getName(){
        return name;
    }

    public int getCustomerID() {
        return customerID;
    }



}
