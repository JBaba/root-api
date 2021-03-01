package com.root.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

public class CommandsTest {

    @Test
    public void testValidDriverCmd() {
        String input = "Driver Naimish";
        Matcher matcher = Commands.DRIVER.matcher(input);
        Assertions.assertTrue(matcher.find());
        Assertions.assertEquals("Driver", matcher.group(1).trim());
        Assertions.assertEquals("Naimish", matcher.group(3).trim());
        // case sensitive test
        input = "driver Naimish Viradia";
        matcher = Commands.DRIVER.matcher(input);
        Assertions.assertTrue(matcher.find());
        Assertions.assertEquals("driver", matcher.group(1).trim());
        Assertions.assertEquals("Naimish Viradia", matcher.group(3).trim());
        // user name with nums
        input = "driver Naimish 123";
        matcher = Commands.DRIVER.matcher(input);
        Assertions.assertTrue(matcher.find());
        Assertions.assertEquals("driver", matcher.group(1).trim());
        Assertions.assertEquals("Naimish 123", matcher.group(3).trim());
    }

    @Test
    public void testInValidDriverCmd() {
        String input = "XYZ Naimish";
        Matcher matcher = Commands.DRIVER.matcher(input);
        Assertions.assertFalse(matcher.find());
    }

    @Test
    public void testValidTripCmd() {
        String input = "Trip Naimish 07:10 07:12 17.3";
        Matcher matcher = Commands.TRIP.matcher(input);
        Assertions.assertTrue(matcher.find());
        Assertions.assertEquals("Trip", matcher.group(1).trim());
        Assertions.assertEquals("Naimish", matcher.group(3).trim());
        Assertions.assertEquals("07:10", matcher.group(5).trim());
        Assertions.assertEquals("07:12", matcher.group(7).trim());
        Assertions.assertEquals("17.3", matcher.group(9).trim());
        input = "trip Naimish Viradia 07:10 07:12 17.3";
        matcher = Commands.TRIP.matcher(input);
        Assertions.assertTrue(matcher.find());
        Assertions.assertEquals("trip", matcher.group(1).trim());
        Assertions.assertEquals("Naimish Viradia", matcher.group(3).trim());
        Assertions.assertEquals("07:10", matcher.group(5).trim());
        Assertions.assertEquals("07:12", matcher.group(7).trim());
        Assertions.assertEquals("17.3", matcher.group(9).trim());
        input = "Trip Naimish Viradia 07:10 07:12 17";
        matcher = Commands.TRIP.matcher(input);
        Assertions.assertTrue(matcher.find());
        Assertions.assertEquals("17", matcher.group(9).trim());
    }

    @Test
    public void testInValidTripCmd() {
        String[] invalid = {
                "Trip Naimish 7:1 1:1 17.3",
                "Trip Naimish 07:1 1:1 17.3",
                "Trip Naimish 7:10 1:1 17.3",
                "Trip Naimish 07:10 1:1 17.3",
                "Trip Naimish 07:10 01:1 17.3",
                "Trip Naimish 07: 1:1 17.3",
                "Trip Naimish 07:1 1: 17.3",
                "Trip Naimish 07:1 1:1 17.",
                "Trip Naimish 07:1 1:1",
                "Tripp Naimish 07:1 1:1 17.3",
                "Trip Naimish"
        };
        for (String input : invalid) {
            Matcher matcher = Commands.TRIP.matcher(input);
            Assertions.assertFalse(matcher.find());
        }
    }

}
