package com.root.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TripTest {

    @Test
    public void testTrip() {
        Trip trip = new Trip("Dan", "07:15", "07:45", "17.3");
        Assertions.assertEquals(34.6+"", trip.speed()+"");
    }

}
