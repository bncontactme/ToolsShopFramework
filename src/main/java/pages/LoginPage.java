package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.WaitTimeUtils;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Field Locators
    private By emailField = By.id("email");
    private By passwordField = By.cssSelector("#password");
    // Button Locators
    private By loginButton = By.className("btnSubmit");
    private By signInHyperlink = By.cssSelector("#navbarSupportedContent > ul > li:nth-child(4) > a");
    private By seePasswordButton = By.cssSelector("#password > div > div > button");
    //Text Locators
    private By errorMessageEmail = By.id("email-error");
    private By errorMessagePassword = By.id("password-error");
    // Actions
    public void clickProfileButton() {
        WebElement profilebtn = WaitTimeUtils.waitForElementToBeClickable(driver, signInHyperlink);
        profilebtn.click();
    }

    public void clickContinueButton() {
        WebElement continuebtn = WaitTimeUtils.waitForElementToBeClickable(driver, loginButton);
        continuebtn.click();
    }

    public void clickSeePasswordButton() {
        WebElement seePasswordBtn = WaitTimeUtils.waitForElementToBeClickable(driver, seePasswordButton);
        seePasswordBtn.click();
    }

    public void inputUsernameField(String usernameInput) {
        WebElement usernameFld = WaitTimeUtils.waitForElementToBeClickable(driver, emailField);
        usernameFld.sendKeys(usernameInput);
    }

    public void inputPasswordField(String passwordInput) {
        // Locate the password field using the locator
        WebElement passwordFld = WaitTimeUtils.waitForElementToBeClickable(driver, passwordField);

        // Use JavaScript to set the value of the password field
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", passwordFld, passwordInput);
    }

    public void verifyErrorMessageEmail(){
        WebElement errorMsg = WaitTimeUtils.waitForElementToBeVisible(driver, errorMessageEmail);
        Assert.assertTrue(errorMsg.isDisplayed());
        Assert.assertTrue(errorMsg.getText().contains("Email is required"));
    }

    public void verifyErrorMessagePassword(){
        WebElement errorMsg = WaitTimeUtils.waitForElementToBeVisible(driver, errorMessagePassword);
        Assert.assertTrue(errorMsg.isDisplayed());
        Assert.assertTrue(errorMsg.getText().contains("Password is required"));
    }

    // Methods
    public void performFullLogin(String usernameInput, String passwordInput) {
        clickProfileButton();
        inputUsernameField(usernameInput);
        clickSeePasswordButton();
        inputPasswordField(passwordInput);
        clickContinueButton();
    }

}