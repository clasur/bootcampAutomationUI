package basicSelenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Date;

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
        driver.findElement(By.xpath("//textarea[@id='NewItemContentInput']")).sendKeys(nameTask);
        driver.findElement(By.xpath("//input[@id='NewItemAddButton']")).click();

        Thread.sleep(1000);
        int actualResult1=driver.findElements(By.xpath("//div [@class=\"ItemContentDiv\"]")).size();
        Assertions.assertTrue(actualResult1 >= 1
                ,"ERROR The task was not created");

        //update task
        String nameTaskUpdate = "Update Task";
        driver.findElement(By.xpath("//div[@class=\"ItemContentDiv\"][1]")).click();
        driver.findElement(By.xpath("//div[@class=\"ItemContentDiv\"][1]")).clear();
        Thread.sleep(4000);
        driver.findElement(By.xpath("//textarea[@id=\"ItemEditTextbox\"][1]")).sendKeys(nameTaskUpdate);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//img[@class=\"ItemMenu\" and @itemid=\"11079150\"]")).click();
        driver.findElement(By.xpath("//ul[@id=\"itemContextMenu\"]//li[.= 'Edit']]")).click();
        driver.findElement(By.xpath("//*[@id=\"CurrentProjectTitle\"]")).click();

        Thread.sleep(1000);
        int actualResult2=driver.findElements(By.xpath("//div[@class=\"ItemContentDiv\"][1]")).size();
        Assertions.assertTrue(actualResult2 >= 1,"ERROR the task  as not update");

    }

}
