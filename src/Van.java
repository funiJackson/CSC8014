public class Van extends AbstractVehicle {
    private boolean needInspection;

    public Van(VehicleID id) {
        super(id);
        this.needInspection = false;
    }

    public String getVehicleType() {
        return "Van";
    }

    public int getDistanceRequirement(){
        return 5000;
    }

    public boolean requiresInspection(){
        return needInspection;
    }

    public void setNeedInspection(boolean needInspection) {
        this.needInspection = needInspection;
    }

}
