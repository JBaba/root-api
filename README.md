# root-api

## Code Execution Details
* I have compiled this using `Java - 11`
* Also providing `root-api-1.0.jar` executable
  * `java -jar root-api-1.0.jar input.txt`
* You can also use `runWithDocker.sh` file to mount under container
  * `sudo bash runWithDocker.sh /home/jbaba/Documents/javaworkspace/root-api/input.txt`


## Object modeling / Software design
### General Project Structure
1. `App.java` excepts our command line arguments
2. We have 2 model classes
    1. `Trip.java` represents our trip data
    2. `AggregateDetails.java` represents trip aggregate data
3. `DrivingDataProcessor.java` generates report
4. `Commands.java` has list of commands which our app excepts
    
### `App.java` explained

We aim to solve following things,
1. Entry point for our api 
2. Validate command line args are valid (`validateProgramArguments`, `validateFile`)
3. Generate report and print on console

```java
    public void start(String[] args) throws Exception {
        // validate command line arguments
        validateProgramArguments(args);
        String fileName = args[0];
        validateFile(fileName);
        // process data
        DrivingDataProcessor drivingDataProcessor = new DrivingDataProcessor(fileName);
        // Generate a report containing each driver with total miles driven and average speed
        String report = drivingDataProcessor.totalMilesAndAverageReport();
        System.out.println(report);
    }
```

### `Trip.java` explained

`Trip` command in problem statement represents following details,

1. The command (Trip) - we can ignore as our `Trip` model represents this command details
2. driver name - We will need `String` class to represent our driver name
3. start time - We will convert out time into minutes for speed calculations. We can have max 1440 minutes this will fit into `int` primitive type
4. stop time - Same as `start time`
5. miles driven - We can have fractional miles 19.7, so we will use `double` primitive type to represent miles driven.

Also, when we init our trip object our input values for time is given in string representation of hh:mm. We will convert time into minutes.

Code,
```java
    private final String driverName;
    private final int startTimeInMin;
    private final int endTimeInMin;
    private final double miles;
```

Additionally, we need calculate speed for a trip to check out if its below 5 miles/hr or above 100 miles/hr.

we will use `speed = distance / time` formula to calculate speed.

Code,
```java
    public double speed() {
        // time calculated per minute
        double milerPerMin = miles / (endTimeInMin - startTimeInMin);
        // apply multiplier for hr
        return milerPerMin * 60;
    }
```

### `AggregateDetails.java` explained

Our report will need average miles driven by a driver.

Here we will track total number of miles and total time.

```java
    private final String driverName;
    private double totalMiles = 0;
    private long totalTime = 0;
```

Calculate average speed,

```java
    public int avgMilesPerHr() {
        // infinite division error
        if (totalMiles == 0) return 0;
        // speed = distance / time
        return (int) Math.round((totalMiles / totalTime) * 60);
    }
```

### `DrivingDataProcessor.java` explained

We need two member variables,
1. `logger` which can be used to log info during execution 
2. We need to track an individual driver's aggregate details for report (`Map`)

```java
    private final static Logger LOGGER = Logger.getLogger(DrivingDataProcessor.class.getName());
    Map<String, AggregateDetails> detailsMap = new HashMap<>();
```

#### why map?
Our commands from `input.txt` are tracking driver's trip details. We can create map with key as driver name.
As soon as next driver details comes in we can have quick o(1) access to `AggregateDetails` and then keep incrementing time and miles.

Report,

```java
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
```

### `Commands.java` explained

I am using Regex to identify Driver and Trip commands. Correct pattern will identify and will have exact match.

#### Driver Command RegEx: ^(Driver)(\s+)([\w\d\s]{1,})$

RegEx groups explained,
1. **(Driver)** -> Identifies Driver command
2. **(\s+)** -> Space after Driver command
3. **([\w\d\s]{1,})** -> Driver name ex. Naimish, Naimish Viradia, Naimish123, Naimish 123

#### Trip Command RegEx: ^(Trip)(\s+)([\w\d\s]{1,})(\s+)([0-9]{2}:[0-9]{2})(.*)([0-9]{2}:[0-9]{2})(\s+)([0-9]{1,}[.]{0,1}[0-9]{1,})$

RegEx groups explained,
1. (Trip) -> Identifies Trip command
2. (\s+) -> Space
3. ([\w\d\s]{1,}) -> Driver name ex. Naimish, Naimish Viradia, Naimish123, Naimish 123
4. (\s+) -> Space
5. ([0-9]{2}:[0-9]{2}) -> Looks for first time "07:15"
6. (\s+) -> Space
7. ([0-9]{2}:[0-9]{2}) -> Looks for second time "07:15"
8. (\s+) -> Space
9. ([0-9]{1,}[.]{0,1}[0-9]{1,}) -> Looks for valid double value 12.0 or int value 12


Advantage: When following invalid commands are passed,
* Trip Dan 07:15 07:45 17.X
* Trip Dan 07:15 07:45 17.
* Trip Dan 07:15 07:4X 17.3
* Trip Dan 07:1X 07:45 17.3
* Trip Dan 0715 07:45 17.3

No match in a pattern, and we don't have to write complicated string parser in java

## Testing approach

Write test for every file.