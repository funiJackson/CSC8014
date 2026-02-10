import java.util.*;

public class VehicleID {

    private final String firstComponent;
    private final int secondComponent;
    private final String strRep;

    private static final Map<String, VehicleID> allIDs = new HashMap<>();

    private VehicleID(String firstComponent, int secondComponent, String strRep) {
        this.firstComponent= firstComponent;
        this.secondComponent = secondComponent;
        this.strRep = strRep;
    }


    public static VehicleID getInstance(String type) {
        Random r= new Random();

        while (true) {

            if(type.equals("Car")) {
                String firstChar = "C";
                char secondChar = (char) ('A' + r.nextInt(26));
                int thirdChar = r.nextInt(10);
                String temp_firstComponent = firstChar + secondChar + thirdChar;

                int n = r.nextInt(500);
                int temp_secondComponent = n * 2;

                String temp_ID = temp_firstComponent + "-" + String.format("%03d", temp_secondComponent);

                if(!allIDs.containsKey(temp_ID)) {
                    VehicleID newID = new VehicleID(temp_firstComponent, temp_secondComponent, temp_ID);
                    allIDs.put(temp_ID, newID);

                    return newID;
                }
            }else if(type.equals("Van")) {
                String firstChar = "V";
                char secondChar = (char) ('A' + r.nextInt(26));
                int thirdChar = r.nextInt(10);
                String temp_firstComponent = firstChar + secondChar + thirdChar;

                int n = r.nextInt(500);
                int temp_secondComponent = n * 2 + 1;

                String temp_ID = temp_firstComponent + "-" + String.format("%03d", temp_secondComponent);

                if(!allIDs.containsKey(temp_ID)) {
                    VehicleID newID = new VehicleID(temp_firstComponent, temp_secondComponent, temp_ID);
                    allIDs.put(temp_ID, newID);

                    return newID;
                }
            }else {
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
            }
        }
    }

    public String getFirstComponent() {
        return firstComponent;
    }
    public int getSecondComponent() {
        return secondComponent;
    }

    public String toString() {
        return strRep;
    }

}
