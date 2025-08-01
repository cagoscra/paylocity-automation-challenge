package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Main page elements
    @FindBy(css = ".navbar-brand")
    private WebElement navbarBrand;

    @FindBy(css = "a[href='/Prod/Account/LogOut']")
    private WebElement logoutLink;

    // Employee table
    @FindBy(id = "employeesTable")
    private WebElement employeesTable;

    @FindBy(css = "#employeesTable tbody tr")
    private List<WebElement> employeeRows;

    // Add Employee button
    @FindBy(id = "add")
    private WebElement addEmployeeButton;

    // Employee Modal elements
    @FindBy(id = "employeeModal")
    private WebElement employeeModal;

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "dependants")
    private WebElement dependantsField;

    @FindBy(id = "addEmployee")
    private WebElement addEmployeeModalButton;

    @FindBy(id = "updateEmployee")
    private WebElement updateEmployeeModalButton;

    @FindBy(css = "#employeeModal .btn-secondary")
    private WebElement cancelEmployeeButton;

    // Delete Modal elements
    @FindBy(id = "deleteModal")
    private WebElement deleteModal;

    @FindBy(id = "deleteEmployee")
    private WebElement deleteEmployeeModalButton;

    @FindBy(css = "#deleteModal .btn-secondary")
    private WebElement cancelDeleteButton;

    @FindBy(id = "deleteFirstName")
    private WebElement deleteFirstNameSpan;

    @FindBy(id = "deleteLastName")
    private WebElement deleteLastNameSpan;

    // Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Page validations
    public boolean isDashboardDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(employeesTable));
            return navbarBrand.isDisplayed() &&
                    employeesTable.isDisplayed() &&
                    addEmployeeButton.isDisplayed();
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

    // Employee table operations
    public int getEmployeeCount() {
        waitForTableToLoad();
        // Check if "No employees found" message is displayed
        if (employeeRows.size() == 1) {
            WebElement firstRow = employeeRows.get(0);
            if (firstRow.getText().contains("No employees found")) {
                return 0;
            }
        }
        return employeeRows.size();
    }

    public boolean isEmployeeInTable(String firstName, String lastName) {
        waitForTableToLoad();
        for (WebElement row : employeeRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                String tableFirstName = cells.get(1).getText();
                String tableLastName = cells.get(2).getText();
                if (firstName.equals(tableFirstName) && lastName.equals(tableLastName)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Add Employee functionality
    public void clickAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeButton));
        addEmployeeButton.click();
        wait.until(ExpectedConditions.visibilityOf(employeeModal));
    }

    public void fillEmployeeForm(String firstName, String lastName, String dependants) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField));

        firstNameField.clear();
        firstNameField.sendKeys(firstName);

        lastNameField.clear();
        lastNameField.sendKeys(lastName);

        dependantsField.clear();
        dependantsField.sendKeys(dependants);
    }

    public void clickAddEmployeeInModal() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeModalButton));
        addEmployeeModalButton.click();
        waitForModalToClose();
    }

    public void addEmployee(String firstName, String lastName, String dependants) {
        clickAddEmployee();
        fillEmployeeForm(firstName, lastName, dependants);
        clickAddEmployeeInModal();
    }

    // Edit Employee functionality
    public void clickEditEmployee(String firstName, String lastName) {
        waitForTableToLoad();
        for (WebElement row : employeeRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                String tableFirstName = cells.get(1).getText();
                String tableLastName = cells.get(2).getText();
                if (firstName.equals(tableFirstName) && lastName.equals(tableLastName)) {
                    WebElement editIcon = cells.get(8).findElement(By.className("fa-edit"));
                    wait.until(ExpectedConditions.elementToBeClickable(editIcon));
                    editIcon.click();
                    wait.until(ExpectedConditions.visibilityOf(employeeModal));
                    return;
                }
            }
        }
        throw new RuntimeException("Employee not found: " + firstName + " " + lastName);
    }

    public void clickUpdateEmployeeInModal() {
        wait.until(ExpectedConditions.elementToBeClickable(updateEmployeeModalButton));
        updateEmployeeModalButton.click();
        waitForModalToClose();
    }

    // Delete Employee functionality
    public void clickDeleteEmployee(String firstName, String lastName) {
        waitForTableToLoad();
        for (WebElement row : employeeRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3) {
                String tableFirstName = cells.get(1).getText();
                String tableLastName = cells.get(2).getText();
                if (firstName.equals(tableFirstName) && lastName.equals(tableLastName)) {
                    WebElement deleteIcon = cells.get(8).findElement(By.className("fa-times"));
                    wait.until(ExpectedConditions.elementToBeClickable(deleteIcon));
                    deleteIcon.click();
                    wait.until(ExpectedConditions.visibilityOf(deleteModal));
                    return;
                }
            }
        }
        throw new RuntimeException("Employee not found: " + firstName + " " + lastName);
    }

    public void confirmDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteEmployeeModalButton));
        deleteEmployeeModalButton.click();
        waitForModalToClose();
    }

    public void deleteEmployee(String firstName, String lastName) {
        clickDeleteEmployee(firstName, lastName);
        confirmDelete();
    }

    // Helper methods
    private void waitForTableToLoad() {
        wait.until(ExpectedConditions.visibilityOf(employeesTable));
        // Wait a bit for JavaScript to populate the table
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void waitForModalToClose() {
        wait.until(ExpectedConditions.invisibilityOf(employeeModal));
        wait.until(ExpectedConditions.invisibilityOf(deleteModal));
        // Wait for table to refresh
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Modal state checks
    public boolean isEmployeeModalVisible() {
        try {
            return employeeModal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDeleteModalVisible() {
        try {
            return deleteModal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}