package model;

import manager.VehicleID;

public interface Vehicle {
        /**
         * Returns the unique ID of the vehicle.
         * All Vehicles must have an ID
         * @return the manager.VehicleID object
         */

        VehicleID getVehicleID();
        /**
         * Returns the model.Vehicle type.
         * a model.Vehicle can be either a car or a van
         * @return a string representing the vehicle type ("car" or "van")
         */
        String getVehicleType();
        /**
         * Indicates whether the vehicle is currently hired or not.
         * @return true if the model.Vehicle is hired; false otherwise
         */
        boolean isHired();
        void setHired(boolean h);
        /**
         * Returns the distance the vehicle must travel before it needs a service
         * @return an integer (the service distance requirement)
         */
        public int getDistanceRequirement() ;
        /**
         * Returns the distance a vehicle has traveled since the last service
         * @return an integer (the current mileage since the last service)
         */
        public int getCurrentMileage();
        /**
         * set the current mileage of the vehicle
         */
        public void setCurrentMileage(int mileage);
        /**
         * Checks whether the vehicle requires a service and performs the service if
         due.
         * If a service is required, the current mileage is reset.
         * @return true if the model.Vehicle required a service and it was performed; false
        otherwise
         */
        public boolean performServiceIfDue();

        public boolean requiresInspection();
}
