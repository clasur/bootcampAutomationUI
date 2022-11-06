package basicSelenium;

import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class BasicSeleniumTest {

    WebDriver driver;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://todo.ly/");
    }

    @AfterEach
    public void cleanup(){
        driver.quit();
    }

    @Test
    public void verifyCRUDProject() throws InterruptedException {

        // login
        driver.findElement(By.xpath("//img[contains(@src,'pagelogin')]")).click();
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxEmail")).sendKeys("bootcamp@mojix44.com");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxPassword")).sendKeys("12345");
        driver.findElement(By.id("ctl00_MainContent_LoginControl1_ButtonLogin")).click();
        Thread.sleep(1000);
        Assertions.assertTrue(driver.findElement(By.id("ctl00_HeaderTopControl1_LinkButtonLogout")).isDisplayed()
                                    ,"ERROR login was incorrect");

        // create
        String nameProject="Mojix"+new Date().getTime();
        driver.findElement(By.xpath("//td[text()='Add New Project']")).click();
        driver.findElement(By.id("NewProjNameInput")).sendKeys(nameProject);
        driver.findElement(By.id("NewProjNameButton")).click();
        Thread.sleep(1000);
        int actualResult=driver.findElements(By.xpath(" //td[text()='"+nameProject+"'] ")).size();
        Assertions.assertTrue(actualResult >= 1
                ,"ERROR The project was not created");

        //create task
        String nameTask = "New Task";
        WebElement textTask = driver.findElement(By.xpath("//textarea[@id='NewItemContentInput']"));
        textTask.sendKeys(nameTask);
        driver.findElement(By.xpath("//input[@id='NewItemAddButton']")).click();


        Thread.sleep(1000);
        int actualResult1=driver.findElements(By.xpath("//div [@class=\"ItemContentDiv\"]")).size();
        Assertions.assertTrue(             actualResult1 >= 1
                ,"ERROR The task was not created");

        //update task
        String nameTaskUpdate = "Update";
        WebElement items = driver.findElement(By.id("mainItemList"));
        Thread.sleep(2000);
        List<WebElement> items2 = items.findElements(By.tagName("li"));
        WebElement item = items2.get(items2.size()-1);
        String idItem = "//textarea [@itemid="+item.getAttribute("itemid")+"]";
        driver.findElement(By.xpath("//div[@itemid="+item.getAttribute("itemid")+"]")).click();
        WebDriverWait waitById = new WebDriverWait(driver, Duration.ofSeconds(5));
        waitById.until(ExpectedConditions.elementToBeClickable(By.xpath(idItem)));
        WebElement textArea = driver.findElement(By.xpath(idItem));
        textArea.click();

        textArea.sendKeys(nameTaskUpdate);

        driver.findElement(By.xpath("//img[contains(@style,'inline')]")).click();
        driver.findElement(By.xpath("//ul[@id=\"itemContextMenu\"]//a[@href=\"#edit\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"CurrentProjectTitle\"]")).click();

        Thread.sleep(1000);
        int actualResult2 =driver.findElements(By.xpath("//div[contains(@style,'color: rgb(0, 0, 0);')]")).size();
        Assertions.assertTrue(actualResult2 >= 1,"ERROR the task  as not update");

    }

}
