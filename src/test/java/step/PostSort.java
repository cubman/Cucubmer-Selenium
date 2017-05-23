package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by anatoly on 23.05.17.
 */
public class PostSort {

    public static WebDriver dr;
    public static WebDriverWait wdw;

    public PostSort() {
        System.setProperty("webdriver.chrome.driver", "/home/anatoly/IdeaProjects/comcucumbertesting/chromedriver");
        dr = new ChromeDriver();
        wdw = new WebDriverWait(dr, 8);
    }

    @Given("^loggined user and at page inbox$")
    public void loggined_user_and_at_page_inbox() throws Throwable {
        dr.get("https://google.ru");
        dr.findElement(By.id("gb_70")).click();

        wdw.until(presenceOfElementLocated(By.id("identifierId"))).sendKeys("ananabramov");
        dr.findElement(By.id("identifierNext")).click();

        wdw.until(presenceOfElementLocated(By.name("password"))).sendKeys("p5vsp8gvA");
        dr.findElement(By.id("passwordNext")).click();

        wdw.until(presenceOfElementLocated(By.cssSelector(".gb_Cc, .gb_hb, .gb_dg, .gb_R")));

        dr.get("https://www.google.ru/inbox/");
        wdw.until(presenceOfElementLocated(By.className("learn-more-btn"))).click();
        /*dr.get("https://www.google.ru/inbox/");
        dr.get("https://www.google.ru/inbox/");*/
    }

    @When("^sorted by date desc$")
    public void sorted_by_date_desc() throws Throwable {
        wdw.until(presenceOfElementLocated(By.cssSelector(".te, .Nm, .qz")));
        dr.findElement(By.className("k5")).click();

        dr.findElement(By.cssSelector(".gc, .sp, .g-lW, .qW")).sendKeys(String.format("after:%s before:%s", new SimpleDateFormat("yyyy/MM/dd").format(new Date(0)),  new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())));
       // dr.findElement(By.className("rj")) .click();
    }

    @Then("^check is sorted$")
    public void check_is_sorted() throws Throwable {

    }

}
