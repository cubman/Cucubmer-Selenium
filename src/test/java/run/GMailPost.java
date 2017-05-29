package run;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by anatoly on 29.05.17.
 */

@RunWith(Cucumber.class)
@CucumberOptions(format = {"junit:target/GMailPost.xml" }, features = {"features/SortedGMail.feature"}, glue = "step")
public class GMailPost {
}
