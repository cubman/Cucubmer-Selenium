package run;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by anatoly on 23.05.17.
 */

@RunWith(Cucumber.class)
@CucumberOptions(format = {"junit:target/post.xml" }, features = {"features/Post.feature"}, glue = "step")
public class Post {
}
