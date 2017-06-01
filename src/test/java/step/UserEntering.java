package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


/**
 * Created by anatoly on 22.05.17.
 */
@RunWith(JUnit4.class)
public class UserEntering extends TestCase {
    public static WebDriver dr;
    public static WebDriverWait wdw;

    public UserEntering() {
        System.setProperty("webdriver.chrome.driver", "/home/anatoly/IdeaProjects/comcucumbertesting/chromedriver");
        dr = new ChromeDriver();
        wdw = new WebDriverWait(dr, 8);
    }

    @Test
    @Given("^open browser at page google\\.ru$")
    public void open_browser_at_page_google_ru() throws Throwable {
        // открытие страницы google.ru
        dr.get("https://google.ru");
        // нажатие кнопки "Войти"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("gb_70"))).click();
    }

    @Test
    @When("^push buttom login enter \"([^\"]*)\"$")
    public void push_buttom_login_enter(String arg1) throws Throwable {

        // Ввод имени пользователя
        wdw.until(presenceOfElementLocated(By.id("identifierId"))).sendKeys(arg1);
        // нажатие кнопки "продолжить"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("identifierNext"))).click();

    }

    @Test
    @When("^enter \"([^\"]*)\"$")
    public void enter(String arg1) throws Throwable {

        // ввод пароля
        wdw.until(presenceOfElementLocated(By.name("password"))).sendKeys(arg1);
        // нажатие кнопки "продолжить"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext"))).click();
    }

    @Test
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
