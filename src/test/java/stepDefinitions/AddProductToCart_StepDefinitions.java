package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Keys;
import pages.CartPage;
import pages.MainPage;
import pages.ProductsPage;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;
import utilities.Driver;

public class AddProductToCart_StepDefinitions {


    MainPage mainPage = new MainPage();
    ProductsPage productsPage = new ProductsPage();
    CartPage cartPage = new CartPage();

    @Given("I go to trendyol")
    public void ı_go_to_trendyol() {

        String url = ConfigurationReader.getProperty("trendyolUrl");// trendyol sayfasına gidilir.
        Driver.getDriver().get(url);

        BrowserUtils.waitForPageToLoad(10);// Sayfa yüklenene kadar beklenir.

        mainPage.rejectButton.click();// Çerezler reddedilir.





    }

    @When("I put the product name in the search box")
    public void ı_put_the_product_name_in_the_search_box() {

        mainPage.searchBox.sendKeys("Iphone 16" + Keys.ENTER); // Arama kısmına keyword yazılır ve ENTER a basılır

        BrowserUtils.sleep(2);


    }

    @And("I choose the product")
    public void ı_choose_the_product() {


        productsPage.choosenProduct.click(); //İstenilen ürüne tıklanır.

        BrowserUtils.switchToNewWindow(); // Tıklama sonrası yeni sekmeye geçiş yapılır.


    }

    @And("I add the cart")
    public void ı_add_the_cart() {


        BrowserUtils.waitForVisibility(productsPage.gotItButton, 20);

        productsPage.gotItButton.click(); // Gelen uyarının onaylama butonuna basılır.

        BrowserUtils.waitForClickablility(productsPage.addCartButton,10); // Elemente tıklanana kadar beklenir.

        BrowserUtils.clickWithJS(productsPage.addCartButton); // Sepete ekle butonuna tıklanır.

        BrowserUtils.waitFor(2);

    }

    @And("I go to cart")
    public void ı_go_to_cart() {

        productsPage.myCartButton.click(); // Sepetim butonuna tıklanır.

        BrowserUtils.waitForVisibility(cartPage.gotItButton,10);

        cartPage.gotItButton.click(); // Sepetteki uyarı onaylanır.


    }

    @Then("I should see the product in the cart")
    public void ı_should_see_the_product_in_the_cart() {

        BrowserUtils.verifyElementDisplayed(cartPage.productInTheCart); // Ürünün sepette olduğu doğrulanır.

    }

}
