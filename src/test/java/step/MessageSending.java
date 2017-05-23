package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by anatoly on 23.05.17.
 */

public class MessageSending {
    public static WebDriver dr;
    public static WebDriverWait wdw;

    public MessageSending() {
        System.setProperty("webdriver.chrome.driver", "/home/anatoly/IdeaProjects/comcucumbertesting/chromedriver");
        dr = new ChromeDriver();
        wdw = new WebDriverWait(dr, 4);
    }

    @Given("^open browser at page gmail$")
    public void open_browser_at_page_gmail() throws Throwable {
        dr.get("https://google.ru");
        dr.findElement(By.id("gb_70")).click();

        wdw.until(presenceOfElementLocated(By.id("identifierId"))).sendKeys("ananabramov");
        dr.findElement(By.id("identifierNext")).click();

        wdw.until(presenceOfElementLocated(By.name("password"))).sendKeys("p5vsp8gvA");
        dr.findElement(By.id("passwordNext")).click();


    }

    @When("^push buttom write and keyboard$")
    public void push_buttom_write_and_keyboard() throws Throwable {


        //

        dr.get("https://mail.google.com/");
        dr.get("https://mail.google.com/");
        dr.get("https://mail.google.com/");

        wdw.until(presenceOfElementLocated(By.id(":3y"))).click();
        wdw.until(presenceOfElementLocated(By.id("itamenu")));
        Actions actions = new Actions(dr);
        String openKeyboard= Keys.chord(Keys.ALT, Keys.SHIFT, Keys.getKeyFromUnicode('k'));
        actions.sendKeys(openKeyboard);
        actions.perform();
        /*MenuItem menu = new div(dr.findElement(By.id("itamenu")));
        menu.selectByVisibleText("Русская клавиатура");*/

       // dr.findElement().click();
    }

    @Then("^enter user \"([^\"]*)\"$")
    public void enter_user(String arg1) throws Throwable {

    }

    @Then("^enter message subject \"([^\"]*)\"$")
    public void enter_message_subject(String arg1) throws Throwable {

    }

    @Then("^enter message text \"([^\"]*)\"$")
    public void enter_message_text(String arg1) throws Throwable {

    }

    @Then("^closed window$")
    public void closed_window() throws Throwable {

    }

}
