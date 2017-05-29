package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by anatoly on 29.05.17.
 */
public class GMailSort {

    public static WebDriver dr;
    public static WebDriverWait wdw;

    public GMailSort() {
        System.setProperty("webdriver.chrome.driver", "/home/anatoly/IdeaProjects/comcucumbertesting/chromedriver");
        dr = new ChromeDriver(); // драйвер браузера
        wdw = new WebDriverWait(dr, 10); // драйвер ожидания
    }
    @Given("^loggined user and at page GMail: (.*)$")
    public void loggined_user_and_at_page_GMail_ananabramov_p_vsp_gv(List<String> arg1) throws Throwable {
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

        // переход на страницу сообщений
        dr.navigate().to("https://mail.google.com/");
    }

    @When("^GMail sorted by date$")
    public void gmail_sorted_by_date() throws Throwable {
        wdw.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".gb_R, .gb_tf")));
        wdw.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".gstl_50, .gstt"))).click();
        dr.findElement(By.className("gbqfif")).sendKeys(String.format("date-begin:%s date-end:%s",
                new SimpleDateFormat("yyyy/MM/dd").format(new Date(0)),
                new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime())));

        dr.findElement(By.className("gbqfb")).click();
    }

    @Then("^check is sorted GMail$")
    public void check_is_sorted_GMail() throws Throwable {
        Date prev = Calendar.getInstance().getTime();

                                                         //" T-I   J-J5-Ji   amD   T-I-awG         T-I-ax7   T-I-Js-Gs            L3"
        WebElement right = dr.findElement(By.cssSelector(".T-I, .J-J5-Ji, .amD, .T-I-awG, .amE, .T-I-ax7, .T-I-Js-Gs, .T-I-JE, .L3"));
        while (true) {
            wdw.until(ExpectedConditions.visibilityOfElementLocated(By.className("Cp")));
            List<WebElement> lew = dr.findElement(By.className("Cp")).findElement(By.xpath("*")).findElement(By.xpath("*")).findElements(By.xpath("*")).get(1)
                    .findElements(By.xpath("*"));

            System.out.print(lew.size());
            for (WebElement w : lew) {
                Date d = new SimpleDateFormat("dd MMM yyyy г., HH:mm", Locale.getDefault()).
                        parse(
                                w.findElements(By.xpath("*")).
                                        get(7).
                                        findElements(By.xpath("*")).
                                        get(0).
                                        getAttribute("aria-label")
                        );
                System.out.print(d.toString());
                if (d.getTime() > prev.getTime())
                    throw new Exception("wrong time");

                prev = d;
            }
            if (!right.isEnabled())
                break;
            right.click();
        }
    }
}