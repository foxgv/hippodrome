import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


class MainTest {

    @Test
    @Timeout(value = 21)
    public void mainTimeout() throws Exception {
        Main.main(null);
    }
}