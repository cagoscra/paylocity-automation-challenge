package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page URL
    private static final String LOGIN_URL = "https://wmxrwq14uc.execute-api.us-east-1.amazonaws.com/Prod/Account/Login";

    // Web Elements
    @FindBy(id = "Username")
    private WebElement usernameField;

    @FindBy(id = "Password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = ".text-danger.validation-summary-valid")
    private WebElement errorContainer;

    @FindBy(css = ".navbar-brand")
    private WebElement navbarBrand;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Actions
    public void navigateToLogin() {
        driver.get(LOGIN_URL);
        wait.until(ExpectedConditions.visibilityOf(usernameField));
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public DashboardPage login(String username, String password) {
        navigateToLogin();
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new DashboardPage(driver);
    }

    // Validations
    public boolean isLoginPageDisplayed() {
        try {
            return usernameField.isDisplayed() &&
                    passwordField.isDisplayed() &&
                    loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorDisplayed() {
        try {
            return errorContainer.isDisplayed() &&
                    !errorContainer.getAttribute("class").contains("validation-summary-valid");
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}