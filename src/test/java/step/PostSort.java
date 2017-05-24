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
    private String mainLink;

    public PostSort() {
        System.setProperty("webdriver.chrome.driver", "/home/anatoly/IdeaProjects/comcucumbertesting/chromedriver");
        dr = new ChromeDriver(); // драйвер браузера
        wdw = new WebDriverWait(dr, 10); // драйвер ожидания
    }

    @Given("^loggined user and at page inbox: (.*)$")
    public void loggined_user_and_at_page_inbox(List<String> arg1) throws Throwable {
        // открытие страницы google.ru
        dr.get("https://google.ru");
        // нажатие кнопки "Войти"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("gb_70"))).click();

        // Ввод имени пользователя
        wdw.until(presenceOfElementLocated(By.id("identifierId"))).sendKeys(arg1.get(0));
        // нажатие кнопки "продолжить"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("identifierNext"))).click();

        // ввод пароля
        wdw.until(presenceOfElementLocated(By.name("password"))).sendKeys(arg1.get(1));
        // нажатие кнопки "продолжить"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext"))).click();

        // ожидание загрузки значка вошедшего пользователя
        wdw.until(presenceOfElementLocated(By.cssSelector(".gb_Cc, .gb_hb, .gb_dg, .gb_R")));

        // переход на страницу inbox
        dr.navigate().to("https://www.google.ru/inbox/");
        // нажатие кнопки "войти"
        wdw.until(presenceOfElementLocated(By.className("learn-more-btn"))).click();

        // установка основной ссылки
        mainLink = arg1.get(2);
    }

    @When("^sorted by date desc$")
    public void sorted_by_date_desc() throws Throwable {
        // ожидание загрузки окна ввода
        wdw.until(presenceOfElementLocated(By.cssSelector(".te, .Nm, .qz")));
        // нажатие на окно поиска
        wdw.until(presenceOfElementLocated(By.className("k5"))).click();

        // внесение результата поиска after: .. before: ..
        wdw.until(presenceOfElementLocated(By.cssSelector(".gc, .sp, .g-lW, .qW"))).
                sendKeys(String.format("after:%s before:%s",
                        new SimpleDateFormat("yyyy/MM/dd").format(new Date(0)),
                        new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())));

      //  wdw.until(presenceOfElementLocated(By.className("rj"))).click();
    }


    // получить все сообщения за время перемещения ползунка scroll
    private List<WebElement> getMessages(JavascriptExecutor js, int amountOfScrolling ) throws InterruptedException {
        List<WebElement> ls = new LinkedList<WebElement>();
        for (int i = 0; i <= amountOfScrolling; ++i) {

            if (i == amountOfScrolling)
                ls = dr.findElement(By.cssSelector(".yDSKFc, .viy5Tb")).// контент
                        findElements(By.className("DsPmj")).get(1). // 2 блок контена со всеми результатами
                        findElement(By.cssSelector(".ai-cA, .scroll-list-section-body")). // блок с сообщениями
                        findElements(By.cssSelector(".scroll-list-item, .top-level-item, .scroll-list-item-highlighted")); // все сообщения

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)"); // опуститься до самого низу
            Thread.sleep(1000); // ожидание подгрузки
        }
        return  ls; // успевшие загрузиться
    }

    // возвращениессылки на даннные сообщения
    private String getLink(String msgId) {
        return mainLink + msgId;
    }

    // получение идентификатора сообщения
    private String getMsgId(WebElement we) {
        String atr = we.getAttribute("data-msg-id");
        if (atr.contains("msg-f:"))
            return we.getAttribute("data-msg-id").split("msg-f:")[1];
        if (atr.contains("msg-a:"))
            return we.getAttribute("data-msg-id").split("msg-a:")[1];
        return null;
    }

    // получить дату создания сообщения
    private Date getDateCreation(WebElement we) throws ParseException, InterruptedException {
        String mId = getMsgId(we); // id сообщения
        //System.out.print(mId);

        dr.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t"); // открыть новуювкладку
        ArrayList<String> tabs = new ArrayList<String> (dr.getWindowHandles()); // список вкладок
        dr.switchTo().window(tabs.get(1)); // перейти во 2 вкладку

        dr.navigate().to(getLink(mId)); // перейти по ссылке

        try {
            // если во время загрузки не появиться поле с данными, то закрыть вкладку
            wdw.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("raw_message_text")));
        }
        catch (Exception e) {
            dr.close();
            dr.switchTo().window(tabs.get(0));
            System.out.print("missed message");
            return new Date(0);
        }

        String [] str = dr.findElement(By.className("raw_message_text")).getText().split("\n");// текст данных

        Date d = new Date(0);

        // найти ту строку, которая начинается с Date:
        for (String s : str)
            if (s.startsWith("Date:")) {
                d = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(s.substring(6));
                break;
            }

        dr.close(); // закрыть вклдку с данными о сообщении
        dr.switchTo().window(tabs.get(0)); // перейти в почту

        return d; // вернуть дату создания
    }

    @Then("^check is sorted$")
    public void check_is_sorted() throws Throwable {
        JavascriptExecutor js = ((JavascriptExecutor) dr); // для прокрутки

        List<WebElement> messages = getMessages(js, 2); // получить сообщения прокрутив до конца amountOfScrolling раз

        System.out.print(messages.size()); // количество сообщений
        System.out.print("\n\n");

        Date prev = new Date(Calendar.getInstance().getTimeInMillis()); // начальная дата, самая большая
        for (int i= 0; i < messages.size(); ++i) {
            System.out.print(i + " - ");

            js.executeScript("window.scrollTo(0," + (messages.get(i).getLocation().y - 100) + ")"); // прокрутить до сообщения
            wdw.until(ExpectedConditions.visibilityOf(messages.get(i)));
            wdw.until(ExpectedConditions.elementToBeClickable(messages.get(i)));
            messages.get(i).findElement(By.className("jS")).click(); // нажать на сообщения


            List<WebElement> lew = messages.get(i).findElements(By.xpath("*")).
                    get(2).findElements(By.xpath("*")).
                    get(4).findElements(By.xpath("*")).get(0)
                    .findElements(By.xpath("*")); // список сообщений

            wdw.until(ExpectedConditions.visibilityOf(lew.get(0)));
            Date cur_el = getDateCreation(lew.get(0)); // дата создания сообщения
            System.out.print(cur_el);

            if (cur_el.getTime() > prev.getTime()) // дата пред. сообщения больше текущей
                throw new Exception(cur_el + " more " + prev);

            prev = cur_el;

            System.out.print("\n");
        }

        //dr.quit();
    }

}
