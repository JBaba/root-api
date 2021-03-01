package com.root.api.model;

/**
 * Track drivers aggregated details
 */
public class AggregateDetails {

    private final String driverName;
    private double totalMiles = 0;
    private long totalTime = 0;

    public AggregateDetails(String driverName) {
        this.driverName = driverName;
    }

    public void addMiles(double miles) {
        totalMiles += miles;
    }

    public void addTotalTime(int time) {
        totalTime += time;
    }

    public long getTotalMiles() {
        return Math.round(totalMiles);
    }

    public String getDriverName() {
        return driverName;
    }

    public int avgMilesPerHr() {
        // infinite division error
        if (totalMiles == 0) return 0;
        // speed = distance / time
        return (int) Math.round((totalMiles / totalTime) * 60);
    }

}
