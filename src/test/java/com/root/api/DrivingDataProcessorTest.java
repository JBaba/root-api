package com.root.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DrivingDataProcessorTest {

    @Test
    public void testReport() throws IOException {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        DrivingDataProcessor drivingDataProcessor = new DrivingDataProcessor(absolutePath + "/input.txt");
        String report = drivingDataProcessor.totalMilesAndAverageReport();
        String expected = "Lauren: 42 miles @ 34 mph\n" +
                "Dan: 39 miles @ 47 mph\n" +
                "Kumi: 0 miles @ 0 mph\n";
        Assertions.assertEquals(expected, report);
    }

}
