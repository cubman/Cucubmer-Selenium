package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        wdw = new WebDriverWait(dr, 10);
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

        dr.navigate().to("https://www.google.ru/inbox/");
        wdw.until(presenceOfElementLocated(By.className("learn-more-btn"))).click();
        /*dr.get("https://www.google.ru/inbox/");
        dr.get("https://www.google.ru/inbox/");*/
    }

    @When("^sorted by date desc$")
    public void sorted_by_date_desc() throws Throwable {
        wdw.until(presenceOfElementLocated(By.cssSelector(".te, .Nm, .qz")));
        dr.findElement(By.className("k5")).click();

        dr.findElement(By.cssSelector(".gc, .sp, .g-lW, .qW")).sendKeys(String.format("after:%s before:%s", new SimpleDateFormat("yyyy/MM/dd").format(new Date(0)),  new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())));
        wdw.until(presenceOfElementLocated(By.className("rj"))).click();
    }

    @Then("^check is sorted$")
    public void check_is_sorted() throws Throwable {

       // System.out.print(dr.findElements(By.className("DsPmj")).size());

//Identify the WebElement which will appear after scrolling down

        JavascriptExecutor js = ((JavascriptExecutor) dr);

        int max = 3;

        List<WebElement> ls = new LinkedList<WebElement>();
        for (int i = 0; i <= max; ++i) {

            if (i == max) {
               // wdw.until(ExpectedConditions.visibilityOf( dr.findElement(By.cssSelector(".yDSKFc, .viy5Tb")).findElement(By.className("DsPmj"))));
                ls = dr.findElement(By.cssSelector(".yDSKFc, .viy5Tb")).findElements(By.className("DsPmj")).get(1).findElement(By.cssSelector(".ai-cA, .scroll-list-section-body")).findElements(By.cssSelector(".scroll-list-item, .top-level-item"));

            }
            //System.out.print(dr.findElement(By.cssSelector(".yDSKFc, .viy5Tb")).findElements(By.className("DsPmj")).size());

            // System.out.print(dr.findElement(By.cssSelector(".yDSKFc, .viy5Tb")).findElements(By.className("DsPmj")).size());

            //   List<WebElement> lwe = dr.findElement(By.cssSelector(".yDSKFc, .viy5Tb")).findElement(By.cssSelector(".ai-cA, .scroll-list-section-body"))
            //  System.out.print(lwe);
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(1000);
        }

        if (ls.size() == 0)
            return;


// now execute query which actually will scroll until that element is not appeared on page.

        js.executeScript("arguments[0].scrollIntoView(true);",ls.get(0));
        ls.get(0).click();
        ls.get(0).findElement(By.cssSelector(".pA, .s2")).click();
    }

}
