import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jTest {
    private static final Logger logger = LoggerFactory.getLogger(Log4jTest.class);
    public static void main(String[] args) {
        logger.debug("DEBUG TEST");
        logger.error("ERROR TEST");
        logger.info("INFO TEST");
    }
}
