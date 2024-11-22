package utils;

import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverUtils {
    // Method to initialize WebDriver
    public static org.openqa.selenium.WebDriver getDriver() {
        WebDriverManager.chromedriver().setup();
        org.openqa.selenium.WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }


}
