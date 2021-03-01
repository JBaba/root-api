package com.root.api;

import java.util.regex.Pattern;

public class Commands {

    /**
     * Regex Being used to identify if Driver and Trip command is in correct format
     *
     * RegEx groups explained,
     * (Driver) -> Identifies Driver command
     * (\s+) -> Space after Driver command
     * ([\w\d\s]{1,}) -> Driver name ex. Naimish, Naimish Viradia, Naimish123, Naimish 123
     */

    public static final Pattern DRIVER = Pattern.compile("^(Driver)(\\s+)([\\w\\d\\s]{1,})$", Pattern.CASE_INSENSITIVE);

    /**
     * RegEx groups explained,
     * (Trip) -> Identifies Trip command
     * (\s+) -> Space
     * ([\w\d\s]{1,}) -> Driver name ex. Naimish, Naimish Viradia, Naimish123, Naimish 123
     * (\s+) -> Space
     * ([0-9]{2}:[0-9]{2}) -> Looks for first time "07:15"
     * (\s+) -> Space
     * ([0-9]{2}:[0-9]{2}) -> Looks for second time "07:15"
     * (\s+) -> Space
     * ([0-9]{1,}[.]{0,1}[0-9]{1,}) -> Looks for valid double value 12.0 or int value 12
     *
     * Advantage: When following invalid commands are passed,
     * Trip Dan 07:15 07:45 17.X
     * Trip Dan 07:15 07:45 17.
     * Trip Dan 07:15 07:4X 17.3
     * Trip Dan 07:1X 07:45 17.3
     * Trip Dan 0715 07:45 17.3
     * no match in pattern. And we don't have to write complicated string parser in java
     */
    public static final Pattern TRIP = Pattern.compile("^(Trip)(\\s+)([\\w\\d\\s]{1,})(\\s+)([0-9]{2}:[0-9]{2})(.*)([0-9]{2}:[0-9]{2})(\\s+)([0-9]{1,}[.]{0,1}[0-9]{1,})$", Pattern.CASE_INSENSITIVE);

}
