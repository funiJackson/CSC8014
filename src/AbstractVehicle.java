public abstract class AbstractVehicle implements Vehicle {

    private boolean isHired;
    private final VehicleID id;
    private int currentMileage;


    AbstractVehicle(VehicleID id) {
        this.isHired = false;
        this.currentMileage = 0;
        this.id = id;
    }

    public static Vehicle getInstance(String type) {
        VehicleID newID = VehicleID.getInstance(type);

        if (type.equals("Car")) {
            return new Car(newID);
        } else if (type.equals("Van")) {
            return new Van(newID);
        } else {
            throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }

    }

    public VehicleID getVehicleID() {
        return id;
    }

    public boolean isHired() {
        return isHired;
    }

    public void setHired(boolean h) {
        this.isHired = h;
    }

    public abstract int getDistanceRequirement();



    public int getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(int mileage) {
        this.currentMileage = mileage;
    }

    public boolean performServiceIfDue() {
        if( this.currentMileage >= getDistanceRequirement()){
            this.currentMileage = 0;
            return true;
        }
        return false;
    }

    public boolean requiresInspection() {
        return false;
    }

}
