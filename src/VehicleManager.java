import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

/**
 * The VehicleManager class acts as the main class for managing the business.
 * It maintains records of all vehicles, registered customers, and currently hired vehicles.
 * This class provides functionality to:
 * 1.Add new vehicles.
 * 2.Register new customers.
 * 3.Check vehicle availability.
 * 4.Process vehicle hiring with age and license validation.
 * 5.Process vehicle returns, including mileage updates and service checks.
 * @author Fandi Zhou
 */
public class VehicleManager {


    ArrayList<Vehicle> allVehicles= new ArrayList<>();//A list holding all vehicles.

    HashSet<CustomerRecord> CustomerRecords= new HashSet<>();//A set of registered customer records.

    /**
     * A map tracking currently hired vehicles.
     * Key: Customer ID (Integer).
     * Value: A collection of Vehicles hired by customers.
     */
    Map<Integer, Collection<Vehicle>> hiredVehicles = new HashMap<>();


    /**
     * This method adds a new vehicle of the specified type vehicleType to the system and
     * allocates it a vehicle ID.
     * @param vehicleType the type of vehicle to create (e.g., "Car", "Van").
     * @return the newly created Vehicle object.
     */
    public Vehicle addVehicle(String vehicleType){

        Vehicle a = AbstractVehicle.getInstance(vehicleType);

        allVehicles.add(a);
        return a;

    }

    /**
     *This method returns the number of vehicles of the specified type (a car or a van) that are Not hired.
     * @param vehicleType the type of vehicle to count (e.g., "Car", "Van").
     * @return the number of vehicles available for hire.
     */
    public int noOfAvailableVehicles(String vehicleType) {
        int count = 0;

        // Iterate through the entire fleet of vehicles.
        for (Vehicle v : allVehicles) {
            if (v.getVehicleType().equals(vehicleType) && !v.isHired()) {
                // Check two conditions:
                // 1. The vehicle type matches what we are looking for (Car or Van).
                // 2. The vehicle is NOT currently hired (!v.isHired()).
                count++;
            }
        }

        return count;
    }

    /**
     * Registers a new customer in the system.
     * @param firstName (the customer's first name).
     * @param lastName (the customer's last name).
     * @param dob (the customer's date of birth).
     * @param hasCommercialLicense (true if the customer holds a commercial driver's license (needed for Vans)).
     * @return the newly created CustomerRecord Object.
     * @throws IllegalArgumentException if a record for this customer already exists.
     */

    public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) {
        Name name = new Name(firstName, lastName);

        CustomerRecord c = new CustomerRecord(name, dob, hasCommercialLicense);

        // Check if this customer already exists in our set.
        if (CustomerRecords.contains(c)) {
            throw new IllegalArgumentException("Duplicate record found");
        }else {// If unique, add to the set and return the new record.
            CustomerRecords.add(c);
            return c;
        }
    }

    /**
     * Method to hire a vehicle for a customer.

     * Rules: 1. A customer cannot hire more than three vehicles of all types.
     *        2. The current mileage of the vehicle must not exceed the distance required for service.
     *        3. To hire a car, a customer must be at least 18 years old.
     *        4. To hire a van, a customer must have a commercial licence and be at least 23.
     *        5. the van must not currently require an inspection.

     * @param customerRecord the customer attempting to hire the vehicle.
     * @param vehicleType    the type of vehicle requested ("Car" or "Van").
     * @param duration       the duration of the hire in days (used to determine Van inspection requirements).
     * @return {@code true} if the vehicle was successfully hired; false if no vehicle is available or the hire limit is reached.
     * @throws IllegalArgumentException if the customer does not meet the age or license requirements.
     */
    public boolean hireVehicle(CustomerRecord customerRecord, String vehicleType, int duration) {

        //count the age of the current customer
        Date birthDate = customerRecord.getBirthDate();
        LocalDate birth = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        // Calculate the exact age in years.
        int age = Period.between(birth, now).getYears();


        // Driver must be at least 18 to rent a Car.
        if (vehicleType.equals("Car") && age <18){ throw new IllegalArgumentException("Age must above 18 to rent a Car!");}

        //Specific requirements for Vans.
        //Driver must be at least 23 and have Commercial licence to rent a Van.
        if (vehicleType.equals("Van")){
            if(age < 23){throw new IllegalArgumentException("Age must above 23 to rent a Van!");}
            if(!customerRecord.isHaveC_licence()){ throw new IllegalArgumentException("Commercial Licence Not Found!");}
        }


        int id = customerRecord.getCustomerID();//Get the customer id

        Collection<Vehicle> hired_V = hiredVehicles.get(id);// Retrieve the collection of vehicles currently hired by this customer

       // A customer can only hire a maximum of 3 vehicles at once.
        if (hired_V != null && hired_V.size() >= 3) {
            System.out.println("Vehicle already hired at maximum number");
            return  false;
        }

        Vehicle v = null;

        for (Vehicle v_ : allVehicles) {
            // We need a vehicle that matches the type AND is not currently hired.
            if (v_.getVehicleType().equals(vehicleType)&& !v_.isHired() && v_.getCurrentMileage() < v_.getDistanceRequirement()) {
                    if(v_ instanceof Van){
                        Van van =  (Van)v_;//casting v_ into Van obj so it can use its set method.
                        if(van.requiresInspection()){ continue;}//If the van is flagged for inspection, it cannot be hired. Skip it.
                        if(duration >= 10){van.setNeedInspection(true);}// Set the inspection status if the duration is above 10.
                    }
                    v = v_;// Found a valid vehicle.
                break;
            }
        }


        if (v == null) {// If 'v' is still null, it means no suitable vehicle was found in the loop.
            System.out.println("No available vehicle found!");
            return  false;
        }

        if (hired_V == null) {// If this is the customer's first hire, initialize the list in the map.
            hired_V =  new ArrayList<>();
            hiredVehicles.put(id, hired_V);
        }
        hired_V.add(v);

        v.setHired(true);//Update the status to the hired Vehicle.

        return true;

    }



    /**
     * Method of the return of a hired vehicle, Updates the vehicle's mileage and status.
     * Also performs maintenance checks:
     * 1. If the vehicle requires a standard service based on total mileage.
     * 2. If the vehicle is a Van, checks if it requires a post-hire inspection.
     * 3. Removes the vehicle from the customer's list of hired vehicles.

     * @param vehicleID      the unique ID of the vehicle being returned.
     * @param customerRecord the customer returning the vehicle.
     * @param mileage        the distance traveled during this specific hire period.
     */
    public void returnVehicle(VehicleID vehicleID ,CustomerRecord customerRecord, int mileage) {

        //Get the list of vehicles currently hired by this customer.
        int id = customerRecord.getCustomerID();
        Collection<Vehicle> hired_Vehicle = hiredVehicles.get(id);

        if (hired_Vehicle == null) {//Means customer has no hired vehicles.
            System.out.println("No hired vehicle found!");
            return;
        }

        Vehicle TargetV =  null;
        for (Vehicle v: hired_Vehicle) {
            // Compare IDs to make sure we find the correct vehicle.
            // Using String.valueOf handles potential nulls safely.
            if (String.valueOf(v.getVehicleID()).equals(String.valueOf(vehicleID))) {
                TargetV = v;
                break;
            }
        }

        if (TargetV == null) {// Means the vehicle wasn't found in the customer's hired list.
            System.out.println("No return vehicle found!");
            return;
        }

        // Update the total mileage of the vehicle.
        int newMileage = TargetV.getCurrentMileage() + mileage;
        TargetV.setCurrentMileage(newMileage);

        TargetV.setHired(false);// Mark the vehicle as available.

        if(TargetV.performServiceIfDue()){// Check if exceeds service interval.
            System.out.println("The vehicle of " + vehicleID + "has been serviced.");
        }

        if(TargetV instanceof Van){
            Van van =  (Van)TargetV;

            // If the van was flagged for inspection due to long hire, run the below.
            if(van.requiresInspection()){
                van.setNeedInspection(false);// Reset the inspection flag so it can be hired again.
                System.out.println("The vehicle of " + vehicleID + "inspection done!");
            }
        }

        hired_Vehicle.remove(TargetV);// Remove the vehicle from the customer's current hire list.


        //If the customer has no other active vehicle hires remaining after the removal, the entire customer entry is deleted from the data structure.
        if(hired_Vehicle.isEmpty()){
            hiredVehicles.remove(id);
        }
    }


    /**
     * Retrieves an unmodifiable collection of vehicles currently hired by a specific customer.
     *
     * @param customerRecord the customer whose hired vehicles are to be retrieved.
     * @return a Collection of Vehicle objects. Returns an empty list if the customer has no active hires.
     */
    public Collection<Vehicle> getVechilesByCustomer (CustomerRecord customerRecord){

        Collection<Vehicle> hired_Vehicle_record = hiredVehicles.get(customerRecord.getCustomerID());

        if (hired_Vehicle_record == null) {
            System.out.println("No available vehicle found!");
            return Collections.emptyList();//Return an empty list instead of null if no records found.
        }

        //Return an unmodifiable collection so the caller cannot modify it.
        return Collections.unmodifiableCollection(hired_Vehicle_record);
    }
}
