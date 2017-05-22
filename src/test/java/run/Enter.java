package run;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by anatoly on 22.05.17.
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {"junit:target/login.xml" }, features = {"features"}, glue = "step")
public class Enter {
}
