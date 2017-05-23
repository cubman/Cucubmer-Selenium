package run;

/**
 * Created by anatoly on 23.05.17.
 */
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by anatoly on 22.05.17.
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {"junit:target/sending.xml" }, features = {"features/SendMessage.feature"}, glue = "step")
public class Send {
}
