package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class CartPage {

    @FindBy(xpath = "//p[@title='Apple iPhone 16 128GB White']")
    public WebElement productInTheCart;

    @FindBy(xpath = "//button[.='Anladım']")
    public WebElement gotItButton;


    public CartPage(){

        PageFactory.initElements(Driver.getDriver(), this);
    }
}
