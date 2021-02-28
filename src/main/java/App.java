import com.root.api.DrivingDataProcessor;

import java.io.File;

public class App {

    public static void main(String[] args) {
        // app start
        new App().start(args);
    }

    private void start(String[] args) {
        try {
            // validate command line arguments
            validateProgramArguments(args);
            String fileName = args[0];
            validateFile(fileName);
            // process data
            DrivingDataProcessor drivingDataProcessor = new DrivingDataProcessor(fileName);
            // Generate a report containing each driver with total miles driven and average speed
            drivingDataProcessor.totalMilesAndAverageReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateFile(String fileName) throws Exception {
        // file validation
        File file = new File(fileName);
        // file is directory
        if (file.isDirectory()) {
            throw new Exception(String.format("Invalid argument (%s) please provide file not directory.", fileName));
        }
        // invalid file path
        if (!file.exists()) {
            throw new Exception(String.format("Invalid (%s) file path.", fileName));
        }
    }

    /**
     * Validate arguments passed to program is valid
     *
     * @param args
     * @throws Exception
     */
    private void validateProgramArguments(String[] args) throws Exception {
        String cmdExample = "Example: java -jar root-api.jar input.txt";
        // if zero arguments
        if (args.length == 0) {
            throw new Exception("Missing argument <filename> to program. " + cmdExample);
        }
        // only expecting one argument <filename>
        if (args.length > 1) {
            throw new Exception("Invalid argument passed to api. " + cmdExample);
        }
    }

}
