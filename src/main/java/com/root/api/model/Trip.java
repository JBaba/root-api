package com.root.api.model;

public class Trip {

    private static final String TIME_SEP = ":";
    private final String driverName;
    private final int startTimeInMin;
    private final int endTimeInMin;
    private final double miles;

    public Trip(String driverName, String startTime, String endTime, String miles) {
        this.driverName = driverName;
        this.miles = Double.parseDouble(miles);
        startTimeInMin = calTimeInMinFromString(startTime);
        endTimeInMin = calTimeInMinFromString(endTime);
    }

    // convert string format to time in minutes
    private int calTimeInMinFromString(String time) {
        String[] timeSplit = time.split(TIME_SEP);
        int hr = Integer.parseInt(timeSplit[0]);
        int min = Integer.parseInt(timeSplit[1]);
        return (hr * 60) + min;
    }

    public double getMiles() {
        return miles;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getTime() {
        return endTimeInMin - startTimeInMin;
    }

    /**
     * calculate miles per hr
     *
     * @return miles per hr
     */
    public double speed() {
        // time calculated per minute
        double milerPerMin = miles / (endTimeInMin - startTimeInMin);
        // apply multiplier for hr
        return milerPerMin * 60;
    }


    @Override
    public String toString() {
        return "Trip{" +
                "driverName='" + driverName + '\'' +
                ", speed=" + this.speed() +
                '}';
    }
}
