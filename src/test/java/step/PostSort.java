package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.bind.Element;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by anatoly on 23.05.17.
 */
public class PostSort {

    public static WebDriver dr;
    public static WebDriverWait wdw;
    private String mainLink = "";

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
    }

    @When("^sorted by date desc$")
    public void sorted_by_date_desc() throws Throwable {
        wdw.until(presenceOfElementLocated(By.cssSelector(".te, .Nm, .qz")));
        dr.findElement(By.className("k5")).click();

        wdw.until(presenceOfElementLocated(By.cssSelector(".gc, .sp, .g-lW, .qW"))).
                sendKeys(String.format("after:%s before:%s",
                        new SimpleDateFormat("yyyy/MM/dd").format(new Date(0)),
                        new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())));

        wdw.until(presenceOfElementLocated(By.className("rj"))).click();
    }


    private List<WebElement> getMessages(JavascriptExecutor js, int amountOfScrolling ) throws InterruptedException {
        List<WebElement> ls = new LinkedList<WebElement>();
        for (int i = 0; i <= amountOfScrolling; ++i) {

            if (i == amountOfScrolling)
                ls = dr.findElement(By.cssSelector(".yDSKFc, .viy5Tb")).
                        findElements(By.className("DsPmj")).get(1).
                        findElement(By.cssSelector(".ai-cA, .scroll-list-section-body")).
                       // findElements(By.cssSelector(".an, .b9"));
                        findElements(By.cssSelector(".scroll-list-item, .top-level-item"));

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(1000);
        }
        return  ls;
    }

    private String getFirstLink(JavascriptExecutor js, WebElement we) {

        js.executeScript("arguments[0].scrollIntoView(true);", dr.findElement(By.className("ai-b8")));
        wdw.until(ExpectedConditions.elementToBeClickable(we));
        we.click();

        return "https://mail.google.com/mail/u/0/?ik=9a3ca749ae&view=om&permmsgid=msg-f:1568184822149037543";
    }

    private void initMainLink(String link) {
        mainLink = link.split("msg-f:")[0];
    }

    private String getLink(String msgId) {
        return mainLink + "msg-f:" + msgId; //msgId.split("msg-f:")[1];
    }

    private String getMsgId(WebElement we) {
        return we.getAttribute("data-msg-id").split("msg-f:")[1];
    }

    private Date getDateCreation(WebElement we) throws ParseException {
        dr.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");

        String mId = getMsgId(we);
        System.out.print(mId);
        ArrayList<String> tabs = new ArrayList<String> (dr.getWindowHandles());
        dr.switchTo().window(tabs.get(1));

        dr.get(getLink(mId));

        wdw.until(ExpectedConditions.presenceOfElementLocated(By.className("raw_message_text")));
        String [] str = dr.findElement(By.className("raw_message_text")).getText().split("\n");

        dr.close();
        dr.switchTo().window(tabs.get(0));

        return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(str[2].substring(6));
    }

    @Then("^check is sorted$")
    public void check_is_sorted() throws Throwable {
        JavascriptExecutor js = ((JavascriptExecutor) dr);

        List<WebElement> messages = getMessages(js, 3);

        System.out.print(messages.size());
        System.out.print("\n\n");

        if (messages.size() == 0)
            return;

        String getLink = getFirstLink(js, messages.get(0));
        initMainLink(getLink);

        for (int i= 0; i < 1; ++i) {
            //System.out.print(i);
            /*js.executeScript("window.scrollTo(0," + messages.get(i).getLocation().y + ")");
            wdw.until(ExpectedConditions.elementToBeClickable(messages.get(i)));
            messages.get(i).click();*/

            List<WebElement> lew = messages.get(i).findElements(By.xpath("*")).
                    get(2).findElements(By.xpath("*")).
                    get(4).findElements(By.xpath("*")).get(0)
                    .findElements(By.xpath("*"));

            //head.click();

            System.out.print(getDateCreation(lew.get(0)));
        }
            // dr.navigate().to(getLink(getLink));

           // System.out.print(lew.get(0).getText());
        dr.quit();
    }

}
