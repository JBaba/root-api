package com.root.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AggregatedDetailsTest {

    @Test
    public void testAggregate() {
        AggregateDetails aggregateDetails = new AggregateDetails("Naimish");
        aggregateDetails.addTotalTime(50);
        aggregateDetails.addTotalTime(40);
        aggregateDetails.addMiles(40);
        aggregateDetails.addMiles(60);
        Assertions.assertEquals(67, aggregateDetails.avgMilesPerHr());
    }

}
