import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void missingFileNameArgs() {
        assertMyError(new String[]{}, "Missing argument <filename> to program. Example: java -jar root-api.jar input.txt");
    }

    @Test
    public void moreThenOneArgs() {
        assertMyError(new String[]{"/home", "/home", "/home"}, "More then one argument is not supported. Example: java -jar root-api.jar input.txt");
    }

    @Test
    public void dirArgs() {
        assertMyError(new String[]{"/home"}, "Invalid argument (/home) please provide file not directory.");
    }

    @Test
    public void invalidFileArgs() {
        assertMyError(new String[]{"/home.txt"}, "Invalid (/home.txt) file path.");
    }

    private void assertMyError(String[] args, String expectedMessage) {
        Exception exception = Assertions.assertThrows(Exception.class, () -> new App().start(args));
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}
