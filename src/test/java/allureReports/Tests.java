package allureReports;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Tests {

    WebDriver driver;


    @BeforeClass
    public void setup()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.zalando.pl/");
        driver.manage().window().maximize();

    }

    @Test(priority = 1)
    public void  smokeTest()
    {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='uc-btn-accept-banner']")));
        driver.findElement(By.xpath("//button[@id='uc-btn-accept-banner']")).click();
        boolean logo =  driver.findElement(By.xpath("//img[@class='z-navicat-header_svgLogo']")).isDisplayed();
        Assert.assertEquals(logo, true);
    }

    @Description("This test check if new user can create account")
    @Test(priority = 2)
    public void  registrationTest()
    {
        driver.findElement(By.xpath("//a[@title='Zaloguj się']")).click();
        driver.findElement(By.xpath("//button[@aria-label='Przejdź dalej']")).click();

        driver.findElement(By.xpath("//input[@placeholder='Imię lub firma']")).sendKeys("Test");
        driver.findElement(By.xpath("//input[@placeholder='Nazwisko lub firma cd.']")).sendKeys("Selenium");

        final String randomEmail = randomEmail();
        driver.findElement(By.xpath("//input[@placeholder='Adres e-mail']")).sendKeys(randomEmail);
        driver.findElement(By.xpath("//input[@placeholder='Hasło']")).sendKeys("Test.123");

        driver.findElement(By.xpath("//span[contains(text(),'Brak preferencji')]")).click();
        driver.findElement(By.xpath("//button[@aria-label='Zarejestruj się']")).click();
        driver.findElement(By.xpath("//a[@title='Moje konto']")).click();

        boolean account =  driver.findElement(By.xpath("//h2[contains(text(),'Witaj Test')]")).isDisplayed();
        Assert.assertEquals(account, true);

        Actions actions = new Actions(driver);
        WebElement myAccount = driver.findElement(By.xpath("//a[@title='Moje konto']"));
        actions.moveToElement(myAccount).perform();

        driver.findElement(By.xpath("//span[contains(text(),'Wyloguj się')]")).click();

    }

    @Description("This test check if existing user can login on his account")
    @Test(priority = 3)
    public void  loginTest()
    {
        driver.findElement(By.xpath("//a[@title='Zaloguj się']")).click();

        driver.findElement(By.xpath("//input[@placeholder='Adres e-mail']")).sendKeys("testseleniumdn@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Hasło']")).sendKeys("Test.123");
        driver.findElement(By.xpath("//button[@aria-label='Zaloguj się']")).click();

        driver.findElement(By.xpath("//a[@title='Moje konto']")).click();

        boolean account =  driver.findElement(By.xpath("//h2[contains(text(),'Witaj Dominik')]")).isDisplayed();
        Assert.assertEquals(account, true);

        Actions actions = new Actions(driver);
        WebElement myAccount = driver.findElement(By.xpath("//a[@title='Moje konto']"));
        actions.moveToElement(myAccount).perform();

        driver.findElement(By.xpath("//span[contains(text(),'Wyloguj się')]")).click();

    }

    @AfterClass
    public void tearDown()
    {
        driver.quit();
    }

    private static String randomEmail() {
        return "random" + UUID.randomUUID().toString() + "@testSelenium.com";
    }
}
