package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.bind.Element;
import java.awt.*;
import java.util.LinkedList;
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
        dr.get("https://mail.google.com/");
        dr.get("https://mail.google.com/");
        dr.get("https://mail.google.com/");

        wdw.until(presenceOfElementLocated(By.className("aic"))).click();
        wdw.until(presenceOfElementLocated(By.className("AD")));


        /*MenuItem menu = new div(dr.findElement(By.id("itamenu")));
        menu.selectByVisibleText("Русская клавиатура");*/

       // dr.findElement().click();
    }

    @Then("^enter user \"([^\"]*)\"$")
    public void enter_user(String arg1) throws Throwable {
        WebElement el = dr.findElement(By.name("to"));
        el.click();
        el.sendKeys(arg1);
    }

    @Then("^enter message subject \"([^\"]*)\"$")
    public void enter_message_subject(String arg1) throws Throwable {
        wdw.until(presenceOfElementLocated(By.id("itamenu"))).findElements(By.cssSelector(".d-Na-JX-I, .d-Na-JG, .d-Na-IF")).get(1).click();
        dr.findElements(By.className("d-Na-N-M7-awE")).get(1).click();
        wdw.until(presenceOfElementLocated(By.className("RK-QJ")));

        WebElement el = dr.findElement(By.name("subjectbox"));
        el.click();
        String res_str = "";

        for (String x: arg1.split(" ")) {
            res_str += dr.findElement(By.id(x)).findElement(By.cssSelector(".RK-Mo")).getText();
        }
        el.sendKeys(res_str);
    }

    @Then("^enter message text \"([^\"]*)\"$")
    public void enter_message_text(String arg1) throws Throwable {
        dr.findElement(By.cssSelector(".RK-QJ-Jk-Pl, .RK-Qq-Mq")).click();
        wdw.until(presenceOfElementLocated(By.id("itamenu"))).findElements(By.cssSelector(".d-Na-JX-I, .d-Na-JG, .d-Na-IF")).get(1).click();
        dr.findElements(By.className("d-Na-N-M7-awE")).get(2).click();
        wdw.until(presenceOfElementLocated(By.className("RK-QJ")));

        WebElement el = dr.findElement(By.cssSelector(".Am, .Al, .editable, .LW-avf"));
        el.click();
        el.sendKeys(arg1);
    }

    @Then("^closed window$")
    public void closed_window() throws Throwable {
       // dr.findElement(By.cssSelector(".RK-QJ-Jk-Pl, .RK-Qq-Mq")).click();
        dr.findElement(By.cssSelector(".gU, .Up")).click();
        try {
            wdw.until(presenceOfElementLocated(By.className("Kj-JD-Jl")));
            throw new Exception();

        } catch (TimeoutException e) {

        }
        catch (Exception e) {
            dr.quit();
            throw new Exception("window was  not closed");
        }
        dr.quit();
    }

}
