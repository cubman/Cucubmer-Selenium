package step;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


/**
 * Created by anatoly on 22.05.17.
 */
public class UserEntering {
    public static WebDriver dr;
    public static WebDriverWait wdw;

    public UserEntering() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        dr = new ChromeDriver();
        wdw = new WebDriverWait(dr, 4);
    }
    @Given("^open browser at page google\\.ru$")
    public void open_browser_at_page_google_ru() throws Throwable {
        System.setProperty("webdriver.chrome.driver", "/home/anatoly/IdeaProjects/comcucumbertesting/chromedriver");

       // dr.manage().window().fullscreen();
        dr.get("https://google.ru");


        dr.findElement(By.id("gb_70")).click();
    }

    @When("^push buttom login enter \"([^\"]*)\"$")
    public void push_buttom_login_enter(String arg1) throws Throwable {
        wdw.until(presenceOfElementLocated(By.id("identifierId"))).sendKeys(arg1);


        dr.findElement(By.id("identifierNext")).click();
    }

    @When("^enter \"([^\"]*)\"$")
    public void enter(String arg1) throws Throwable {
        wdw.until(presenceOfElementLocated(By.name("password"))).sendKeys(arg1);

       // Set beforePopup = dr.getWindowHandles();

// click the link which creates the popup window
       dr.findElement(By.id("passwordNext")).click();
        dr.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
 /*
// get all the window handles after the popup window appears
        Set afterPopup = dr.getWindowHandles();

// remove all the handles from before the popup window appears
        afterPopup.removeAll(beforePopup);

// there should be only one window handle left
        if(afterPopup.size() == 1) {
            dr.switchTo().window((String)afterPopup.toArray()[0]);
        }*/
    }

    @Then("^enter google \"([^\"]*)\"$")
    public void enter_google(String arg1) throws Throwable {
        //System.out.print(dr.getPageSource());



        int sz = dr.findElements(By.id("lst-ib")).size();

        System.out.print(sz);
        System.out.print(dr.getCurrentUrl());


       /* if ("wrong".equals(arg1))
            assertTrue(sz == 0);
        if ("ok".equals(arg1))
            assertTrue( sz != 0);

*/
       assertTrue(dr.getCurrentUrl().equals("https://accounts.google.com/signin/v2/sl/pwd?hl=ru&passive=true&continue=https%3A%2F%2Fwww.google.ru%2F&flowName=GlifWebSignIn&flowEntry=ServiceLogin&cid=1&navigationDirection=forward"));
        //  wdw.until(presenceOfElementLocated(By.id("lst-ib")));
        //assertEquals("https://google.ru", dr.getCurrentUrl() );
       // wdw.until(presenceOfElementLocated(By.id("sb_ifc0")));

        //System.out.print(dr.getCurrentUrl());
        //if (dr.getCurrentUrl() != "https://google.ru")
        //    throw new OutOfMemoryError();


        dr.close();
        dr.quit();
    }

}
