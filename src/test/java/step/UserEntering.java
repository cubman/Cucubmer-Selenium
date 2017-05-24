package step;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;


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
        System.setProperty("webdriver.chrome.driver", "/home/anatoly/IdeaProjects/comcucumbertesting/chromedriver");
        dr = new ChromeDriver();
        wdw = new WebDriverWait(dr, 8);
    }
    @Given("^open browser at page google\\.ru$")
    public void open_browser_at_page_google_ru() throws Throwable {
        // открытие страницы google.ru
        dr.get("https://google.ru");
        // нажатие кнопки "Войти"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("gb_70"))).click();


        dr.get("https://google.ru");

        dr.findElement(By.id("gb_70")).click();
    }

    @When("^push buttom login enter \"([^\"]*)\"$")
    public void push_buttom_login_enter(String arg1) throws Throwable {

        // Ввод имени пользователя
        wdw.until(presenceOfElementLocated(By.id("identifierId"))).sendKeys("ananabramov");
        // нажатие кнопки "продолжить"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("identifierNext"))).click();

    }

    @When("^enter \"([^\"]*)\"$")
    public void enter(String arg1) throws Throwable {

        // ввод пароля
        wdw.until(presenceOfElementLocated(By.name("password"))).sendKeys("p5vsp8gvA");
        // нажатие кнопки "продолжить"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext"))).click();
    }

    @Then("^enter google \"([^\"]*)\"$")
    public void enter_google(String arg1) throws Throwable {
        try {
            // ожидание загрузки значка вошедшего пользователя
            wdw.until(presenceOfElementLocated(By.cssSelector(".gb_Cc, .gb_hb, .gb_dg, .gb_R")));
        } catch (TimeoutException e) {
            dr.quit();
            throw new Exception("user was not passed");
        }

        dr.quit(); // закрыть браузер
    }

}
