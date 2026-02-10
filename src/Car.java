public class Car extends AbstractVehicle {

    public Car(VehicleID id) {
        super(id);
    }

    public String getVehicleType() {
        return "Car";
    }

    public int getDistanceRequirement(){
        return 10000;
    }

}
