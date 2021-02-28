package com.root.api.model;

public class AggregateDetails {

    private final String driverName;
    private double totalMiles = 0;
    private double totalTime = 0;

    public AggregateDetails(String driverName) {
        this.driverName = driverName;
    }

    public void addMiles(double miles) {
        totalMiles += miles;
    }

    public void addTotalTime(int time) {
        totalTime += time;
    }

    public int avgMilesPerHr() {
        if (totalMiles == 0) return 0;
        return (int) Math.round((totalMiles / totalTime) * 60);
    }

    public long getTotalMiles() {
        return Math.round(totalMiles);
    }

    public Object getDriverName() {
        return driverName;
    }
}
