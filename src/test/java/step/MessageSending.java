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
        // открытие страницы google.ru
        dr.get("https://google.ru");
        // нажатие кнопки "Войти"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("gb_70"))).click();

        // Ввод имени пользователя
        wdw.until(presenceOfElementLocated(By.id("identifierId"))).sendKeys("ananabramov");
        // нажатие кнопки "продолжить"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("identifierNext"))).click();

        // ввод пароля
        wdw.until(presenceOfElementLocated(By.name("password"))).sendKeys("p5vsp8gvA");
        // нажатие кнопки "продолжить"
        wdw.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext"))).click();

        // ожидание загрузки значка вошедшего пользователя
        wdw.until(presenceOfElementLocated(By.cssSelector(".gb_Cc, .gb_hb, .gb_dg, .gb_R")));

        // переход на страницу сообщений
        dr.navigate().to("https://mail.google.com/");
    }

    @When("^push buttom write and keyboard$")
    public void push_buttom_write_and_keyboard() throws Throwable {
        wdw.until(presenceOfElementLocated(By.className("aic"))).click(); // нажать "Написать"
        wdw.until(presenceOfElementLocated(By.className("AD"))); // дождаться кнопки с клавиатурой
    }

    @Then("^enter user \"([^\"]*)\"$")
    public void enter_user(String arg1) throws Throwable {
        WebElement el = dr.findElement(By.name("to")); // активировать поле с вводом имени
        el.click();
        el.sendKeys(arg1); // переслать имя
    }

    @Then("^enter message subject \"([^\"]*)\"$")
    public void enter_message_subject(String arg1) throws Throwable {
        wdw.until(presenceOfElementLocated(By.id("itamenu"))).
                findElements(By.cssSelector(".d-Na-JX-I, .d-Na-JG, .d-Na-IF")).get(1).click(); // нажать кнопку со значком клавиатуры
        dr.findElements(By.className("d-Na-N-M7-awE")).get(1).click(); // русская раскладка
        wdw.until(presenceOfElementLocated(By.className("RK-QJ"))); // дождаться клавиатуры

        WebElement el = dr.findElement(By.name("subjectbox")); // тема сообщения
        el.click(); // активировать поле
        String res_str = ""; // результат сообщения по кодам

        // написание сообщения
        for (String x: arg1.split(" "))
            res_str += dr.findElement(By.id(x)).findElement(By.cssSelector(".RK-Mo")).getText();

        // передать текст
        el.sendKeys(res_str);
    }

    @Then("^enter message text \"([^\"]*)\"$")
    public void enter_message_text(String arg1) throws Throwable {
        dr.findElement(By.cssSelector(".RK-QJ-Jk-Pl, .RK-Qq-Mq")).click(); // закрыть клавиатуру
        wdw.until(presenceOfElementLocated(By.id("itamenu"))).
                findElements(By.cssSelector(".d-Na-JX-I, .d-Na-JG, .d-Na-IF")).
                get(1).click(); // активировать клавиатуру с английским языком
        dr.findElements(By.className("d-Na-N-M7-awE")).get(2).click(); // английская раскладка

        // нажать на текст сообщения
        WebElement el = dr.findElement(By.cssSelector(".Am, .Al"));
        el.click();
        el.sendKeys(arg1); // ввести текст
    }

    @Then("^closed window$")
    public void closed_window() throws Throwable {
       // нажать кнопку отправить
        dr.findElement(By.cssSelector(".gU, .Up")).click();
        try {
            // если окно ошибки появилось
            wdw.until(presenceOfElementLocated(By.className("Kj-JD-Jl")));
            throw new Exception(); // послать ошибку

        } catch (TimeoutException e) { // вышли из-за того, что не вывелось окно, значит все хорошо

        }
        catch (Exception e) {
            dr.quit();// закрыть браузер
            throw new Exception("window was  not closed"); // сообщить об ошибке
        }
        dr.quit(); // закрыть браузер
    }

}
