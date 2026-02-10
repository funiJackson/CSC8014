import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

public class VehicleManager {


    ArrayList<Vehicle> allVehicles= new ArrayList<>();

    HashSet<CustomerRecord> CustomerRecords= new HashSet<>();

    Map<Integer, Collection<Vehicle>> hiredVehicles = new HashMap<>();


    public Vehicle addVehicle(String vehicleType){

        Vehicle a = AbstractVehicle.getInstance(vehicleType);

        allVehicles.add(a);
        return a;

    }

    public int noOfAvailableVehicles(String vehicleType) {
        int count = 0;

        for (Vehicle v : allVehicles) {
            if (v.getVehicleType().equals(vehicleType) && !v.isHired()) {
                count++;
            }
        }

        return count;
    }

    public CustomerRecord addCustomerRecord(String firstName, String lastName, Date dob, Boolean hasCommercialLicense) {
        Name name = new Name(firstName, lastName);

        CustomerRecord c = new CustomerRecord(name, dob, hasCommercialLicense);

        if (CustomerRecords.contains(c)) {
            throw new IllegalArgumentException("Duplicate record found");
        }else {
            CustomerRecords.add(c);
            return c;
        }
    }

    public boolean hireVehicle(CustomerRecord customerRecord, String vehicleType, int duration) {

        //count the age of the current customer
        Date birthDate = customerRecord.getBirthDate();
        LocalDate birth = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        int age = Period.between(birth, now).getYears();

        if (vehicleType.equals("Car") && age <18){ throw new IllegalArgumentException("Age must above 18 to rent a Car!");}
        if (vehicleType.equals("Van")){
            if(age < 23){throw new IllegalArgumentException("Age must above 23 to rent a Van!");}
            if(!customerRecord.isHaveC_licence()){ throw new IllegalArgumentException("Commercial Licence Not Found!");}
        }

        int id = customerRecord.getCustomerID();

        Collection<Vehicle> hired_V = hiredVehicles.get(id);
        if (hired_V != null && hired_V.size() >= 3) {
            System.out.println("Vehicle already hired at maximum number");
            return  false;
        }

        Vehicle v = null;

        for (Vehicle v_ : allVehicles) {
            if (v_.getVehicleType().equals(vehicleType)&& !v_.isHired()) {
                    if(v_ instanceof Van){
                        Van van =  (Van)v_;//casting v_ into Van obj so it can use its set method.
                        if(van.requiresInspection()){ continue;}
                        if(duration >= 10){van.setNeedInspection(true);}
                    }
                v = v_;
                break;
            }
        }


        if (v == null) {
            System.out.println("No available vehicle found!");
            return  false;
        }

        if (hired_V == null) {
            hired_V =  new ArrayList<>();
            hiredVehicles.put(id, hired_V);
        }
        hired_V.add(v);

        v.setHired(true);

        return true;

    }

    public void returnVehicle(VehicleID vehicleID ,CustomerRecord customerRecord, int mileage) {
        int id = customerRecord.getCustomerID();
        Collection<Vehicle> hired_Vehicle = hiredVehicles.get(id);

        if (hired_Vehicle == null) {
            System.out.println("No available vehicle found!");
            return;
        }

        Vehicle TargetV =  null;
        for (Vehicle v: hired_Vehicle) {
            if (String.valueOf(v.getVehicleID()).equals(String.valueOf(vehicleID))) {
                TargetV = v;
                break;
            }
        }

        if (TargetV == null) {
            System.out.println("No return vehicle found!");
            return;
        }

        int newMileage = TargetV.getCurrentMileage() + mileage;
        TargetV.setCurrentMileage(newMileage);

        TargetV.setHired(false);

        if(TargetV.performServiceIfDue()){
            System.out.println("The vehicle of " + vehicleID + "has been serviced.");
        }

        if(TargetV instanceof Van){
            Van van =  (Van)TargetV;
            if(van.requiresInspection()){
                van.setNeedInspection(false);
                System.out.println("The vehicle of " + vehicleID + "inspection done!");
            }
        }

        hired_Vehicle.remove(TargetV);

        if(hired_Vehicle.isEmpty()){
            hiredVehicles.remove(id);
        }
    }

    public Collection<Vehicle> getVechilesByCustomer (CustomerRecord customerRecord){

        Collection<Vehicle> hired_Vehicle_record = hiredVehicles.get(customerRecord.getCustomerID());

        if (hired_Vehicle_record == null) {
            System.out.println("No available vehicle found!");
            return Collections.emptyList();
        }

        return Collections.unmodifiableCollection(hired_Vehicle_record);
    }
}
