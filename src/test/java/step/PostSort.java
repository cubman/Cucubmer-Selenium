package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Da;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
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

    @Given("^loggined user and at page inbox: (.*)$")
    public void loggined_user_and_at_page_inbox(List<String> arg1) throws Throwable {
        dr.get("https://google.ru");
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("gb_70"))).click();

        wdw.until(presenceOfElementLocated(By.id("identifierId"))).sendKeys(arg1.get(0));
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("identifierNext"))).click();

        wdw.until(presenceOfElementLocated(By.name("password"))).sendKeys(arg1.get(1));
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext"))).click();

        wdw.until(presenceOfElementLocated(By.cssSelector(".gb_Cc, .gb_hb, .gb_dg, .gb_R")));

        dr.navigate().to("https://www.google.ru/inbox/");
        wdw.until(presenceOfElementLocated(By.className("learn-more-btn"))).click();

        mainLink = arg1.get(2);
    }

    @When("^sorted by date desc$")
    public void sorted_by_date_desc() throws Throwable {
        wdw.until(presenceOfElementLocated(By.cssSelector(".te, .Nm, .qz")));
        wdw.until(presenceOfElementLocated(By.className("k5"))).click();

        wdw.until(presenceOfElementLocated(By.cssSelector(".gc, .sp, .g-lW, .qW"))).
                sendKeys(String.format("after:%s before:%s",
                        new SimpleDateFormat("yyyy/MM/dd").format(new Date(0)),
                        new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())));

      //  wdw.until(presenceOfElementLocated(By.className("rj"))).click();
    }


    private List<WebElement> getMessages(JavascriptExecutor js, int amountOfScrolling ) throws InterruptedException {
        List<WebElement> ls = new LinkedList<WebElement>();
        for (int i = 0; i <= amountOfScrolling; ++i) {

            if (i == amountOfScrolling)
                ls = dr.findElement(By.cssSelector(".yDSKFc, .viy5Tb")).
                        findElements(By.className("DsPmj")).get(1).
                        findElement(By.cssSelector(".ai-cA, .scroll-list-section-body")).
                        findElements(By.cssSelector(".scroll-list-item, .top-level-item, .scroll-list-item-highlighted"));

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(1000);
        }
        return  ls;
    }

    private String getLink(String msgId) {
        return mainLink + msgId;
    }

    private String getMsgId(WebElement we) {
        String atr = we.getAttribute("data-msg-id");
        if (atr.contains("msg-f:"))
            return we.getAttribute("data-msg-id").split("msg-f:")[1];
        if (atr.contains("msg-a:"))
            return we.getAttribute("data-msg-id").split("msg-a:")[1];
        return null;
    }

    private Date getDateCreation(WebElement we) throws ParseException, InterruptedException {
        String mId = getMsgId(we);
        //System.out.print(mId);

        dr.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
        ArrayList<String> tabs = new ArrayList<String> (dr.getWindowHandles());
        dr.switchTo().window(tabs.get(1));


        dr.navigate().to(getLink(mId));

        try {
            wdw.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("raw_message_text")));
        }
        catch (Exception e) {
            dr.close();
            dr.switchTo().window(tabs.get(0));
            System.out.print("missed message");
            return new Date(0);
        }

        String [] str = dr.findElement(By.className("raw_message_text")).getText().split("\n");

        Date d = new Date(0);

        for (String s : str)
            if (s.startsWith("Date:")) {
                d = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(s.substring(6));
                break;
            }

        dr.close();
        dr.switchTo().window(tabs.get(0));

        return d;
    }

    @Then("^check is sorted$")
    public void check_is_sorted() throws Throwable {
        JavascriptExecutor js = ((JavascriptExecutor) dr);

        List<WebElement> messages = getMessages(js, 2);

        System.out.print(messages.size());
        System.out.print("\n\n");

        Date prev = new Date(Calendar.getInstance().getTimeInMillis());
        for (int i= 0; i < messages.size(); ++i) {
            System.out.print(i + " - ");

            js.executeScript("window.scrollTo(0," + (messages.get(i).getLocation().y - 100) + ")");
            wdw.until(ExpectedConditions.visibilityOf(messages.get(i)));
            wdw.until(ExpectedConditions.elementToBeClickable(messages.get(i)));
            messages.get(i).findElement(By.className("jS")).click();


            List<WebElement> lew = messages.get(i).findElements(By.xpath("*")).
                    get(2).findElements(By.xpath("*")).
                    get(4).findElements(By.xpath("*")).get(0)
                    .findElements(By.xpath("*"));

            wdw.until(ExpectedConditions.visibilityOf(lew.get(0)));
            Date cur_el = getDateCreation(lew.get(0));
            System.out.print(cur_el);

            if (cur_el.getTime() > prev.getTime())
                throw new Exception(cur_el + " more " + prev);

            prev = cur_el;

            System.out.print("\n");
        }

        //dr.quit();
    }

}
