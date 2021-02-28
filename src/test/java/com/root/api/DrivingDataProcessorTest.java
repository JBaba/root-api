package com.root.api;

public class DrivingDataProcessorTest {

    public static void main(String[] args) throws Exception {
        new DrivingDataProcessor("/home/jbaba/Documents/javaworkspace/root-api/src/main/resources/input.txt")
                .totalMilesAndAverageReport();
    }

}
