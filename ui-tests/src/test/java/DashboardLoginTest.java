import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class DashboardLoginTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    void loginPageLoads() {
        String loginUrl = System.getProperty("login.url");
        driver.get(loginUrl);
        Assertions.assertTrue(driver.getTitle().contains("Login") || driver.getPageSource().contains("Login"));
    }

    @Test
    void loginWithValidCredentials() {
        String loginUrl = System.getProperty("login.url");
        String username = System.getProperty("test.username");
        String password = System.getProperty("test.password");

        driver.get(loginUrl);
        driver.findElement(By.id("Username")).sendKeys(username);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Espera simple, puedes mejorar con WebDriverWait
        Assertions.assertFalse(driver.getPageSource().contains("Login")); // No debería seguir en login
    }

    @Test
    void loginWithInvalidCredentials() {
        String loginUrl = System.getProperty("login.url");

        driver.get(loginUrl);
        driver.findElement(By.id("Username")).sendKeys("usuario_invalido");
        driver.findElement(By.id("Password")).sendKeys("clave_invalida");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Espera simple, puedes mejorar con WebDriverWait
        Assertions.assertTrue(driver.getPageSource().contains("Login") || driver.getPageSource().contains("Invalid"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}