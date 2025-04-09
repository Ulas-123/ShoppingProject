package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class ProductsPage {


    @FindBy(xpath = "//a[@href='/apple/iphone-16-128gb-white-p-857296082?boutiqueId=681310&merchantId=968']")
    public WebElement choosenProduct;

    @FindBy(xpath = "//button[@class='add-to-basket']")
    public WebElement addCartButton;

    @FindBy(xpath = "//p[.='Sepetim']")
    public WebElement myCartButton;
    @FindBy(xpath = "//button[.='Anladım']")
    public WebElement gotItButton;







    public ProductsPage(){

        PageFactory.initElements(Driver.getDriver(), this);
    }
}
