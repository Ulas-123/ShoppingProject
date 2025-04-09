package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class MainPage {


    @FindBy(xpath = "//div[@class='link account-user']")
    public WebElement loginTab;
    @FindBy(xpath = "//button[@class='q-primary q-fluid q-button-medium q-button submit']")
    public WebElement loginButton;

    @FindBy(xpath = "//input[@id='login-email']")
    public WebElement eMailBox;

    @FindBy(xpath = "//input[@id='login-password-input']")
    public WebElement passwordBox;

    @FindBy(xpath = "//input[@class='V8wbcUhU']")
    public WebElement searchBox;
    @FindBy(xpath = "//button[.='Tümünü Reddet']")
    public WebElement rejectButton;


    public MainPage(){

        PageFactory.initElements(Driver.getDriver(), this);
    }
}
