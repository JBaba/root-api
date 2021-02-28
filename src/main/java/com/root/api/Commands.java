package com.root.api;

import java.util.regex.Pattern;

public class Commands {

    /**
     * Regex Being used to identify if Driver and Trip command is in correct format
     */

    public static final Pattern DRIVER = Pattern.compile("^(Driver)(.*)$", Pattern.CASE_INSENSITIVE);

    /**
     * Advantage: When following invalid commands are passed,
     * Trip Dan 07:15 07:45 17.X
     * Trip Dan 07:15 07:45 17.
     * Trip Dan 07:15 07:4X 17.3
     * Trip Dan 07:1X 07:45 17.3
     * Trip Dan 0715 07:45 17.3
     * no match in patter. And we don't have to write complicated string parser in java
     *
     * RegEx groups explained,
     * (Trip) -> Identifies Trip command
     * (.*) -> "Driver Name"
     * ([0-9]{2}:[0-9]{2}) -> Looks for first time "07:15"
     * (.*) -> Spaces between
     * ([0-9]{2}:[0-9]{2}) -> Looks for second time "07:15"
     * (.*[0-9]{1,}[.]{0,1}[0-9]{1,}) -> Looks for valid double value 12.0 or int value 12
     */
    public static final Pattern TRIP = Pattern.compile("^(Trip)(.*)([0-9]{2}:[0-9]{2})(.*)([0-9]{2}:[0-9]{2})(.*[0-9]{1,}[.]{0,1}[0-9]{1,})$", Pattern.CASE_INSENSITIVE);

    /**
     * Suitable replacement if we want to except time "1:2" where zero is truncated
     */
    //public static final Pattern TRIP = Pattern.compile("^(Trip)(.*)(\s[0-9]{1,}[:]{1}[0-9]{0,})(.*)([0-9]{1,}:[0-9]{1,})(.*\s[0-9]{1,}[.]{0,1}[0-9]{1,})$", Pattern.CASE_INSENSITIVE);

}
