package com.root.api;

import com.root.api.model.AggregateDetails;
import com.root.api.model.Trip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class DrivingDataProcessor {

    private final static Logger LOGGER = Logger.getLogger(DrivingDataProcessor.class.getName());
    Map<String, AggregateDetails> detailsMap = new HashMap<>();

    public DrivingDataProcessor(String fileName) throws IOException {
        readFileAndPopulateDict(fileName);
    }

    /**
     * Read file and process all the commands
     *
     * @param fileName input file
     * @throws IOException error
     */
    private void readFileAndPopulateDict(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(this::processCmd);
        }
    }

    private void processCmd(String line) {
        try {
            // if we process driver cmd then return
            if (processDriverCmd(line)) return;
            processTripCmd(line);
        } catch (Exception ex) {
            LOGGER.info("Error during processing command: " + line);
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }

    /**
     * Process Driver and initiate aggregate object
     *
     * @param line command instructions
     */
    private boolean processDriverCmd(String line) {
        Matcher driverMatcher = Commands.DRIVER.matcher(line);
        // only process if exact match found
        if (driverMatcher.find()) {
            String driverName = driverMatcher.group(3).trim(); // retrieve driver name
            detailsMap.computeIfAbsent(driverName, t -> new AggregateDetails(driverName));
            return true;
        }
        return false;
    }

    /**
     * Process trip data and aggregate information for specific driver
     *
     * @param line command instructions
     */
    private void processTripCmd(String line) {
        Matcher tripMatcher = Commands.TRIP.matcher(line);
        // only process if exact match found
        if (tripMatcher.find()) {
            // create trip by extracting details from command
            Trip trip = createTrip(tripMatcher);
            // Discard any trips that average a speed of less than 5 mph or greater than 100 mph.
            // Discard if driver hasn't not been initiated
            if (trip.speed() >= 5 && trip.speed() <= 100 && detailsMap.containsKey(trip.getDriverName())) {
                // collect aggregate details
                AggregateDetails aggregateDetails = detailsMap.get(trip.getDriverName());
                aggregateDetails.addMiles(trip.getMiles());
                aggregateDetails.addTotalTime(trip.getTime());
            } else {
                LOGGER.info("skipping trip: " + trip);
            }
        } else {
            LOGGER.info("skipping invalid cmd: " + line);
        }
    }

    private Trip createTrip(Matcher tripMatcher) {
        String driverName = tripMatcher.group(3).trim();
        String startTime = tripMatcher.group(5).trim();
        String endTime = tripMatcher.group(7).trim();
        String miles = tripMatcher.group(9).trim();
        return new Trip(driverName, startTime, endTime, miles);
    }

    /**
     * Report criteria
     * 1. Generate a report containing each driver with total miles driven and average speed
     * 2. Sort the output by most miles driven to least
     * 3. Round miles and miles per hour to the nearest integer
     */
    public String totalMilesAndAverageReport() {
        StringBuilder sb = new StringBuilder();
        List<AggregateDetails> aggregateDetailsList = new ArrayList<>(detailsMap.values());
        // sort using total miles driven
        aggregateDetailsList.sort((a, b) -> (int) (b.getTotalMiles() - a.getTotalMiles()));
        for (AggregateDetails aggregateDetail : aggregateDetailsList) {
            String msg = String.format("%s: %d miles @ %d mph", aggregateDetail.getDriverName(), aggregateDetail.getTotalMiles(), aggregateDetail.avgMilesPerHr());
            LOGGER.info(msg);
            sb.append(msg + "\n");
        }
        return sb.toString();
    }
}
