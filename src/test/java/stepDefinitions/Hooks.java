package stepDefinitions;


import utilities.BrowserUtils;
import utilities.DBUtils;
import utilities.Driver;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.AfterClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;

public class Hooks {

    @Before
    public void setUp() {
        System.out.println("this is coming from BEFORE");
        Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Driver.getDriver().manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure_Screenshot");
            Driver.closeDriver();
        }

        else {

            BrowserUtils.waitFor(5);
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "LastStep_Screenshot");
            Driver.closeDriver();

        }
    }

    @Before("@db")
    public void setUpDB() {
        System.out.println("Connection to database...");
        DBUtils.createConnection();
    }

    @After("@db")
    public void tearDownDB() {
        System.out.println("Close database connection...");
        DBUtils.destroy();
    }

    @AfterClass
    public static void generateReport() {
        Results results = Runner.parallel(1, "target/surefire-reports");
        assertTrue(results.getErrorMessages(), results.getFailCount() == 0);

    }

}
