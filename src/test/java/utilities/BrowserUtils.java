package utilities;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BrowserUtils {


    /*
This method will accept int (in seconds) and execute Thread.sleep
for given duration
 */
    public static void sleep(int second) {
        second *= 1000;
        try {
            Thread.sleep(second);
        } catch (InterruptedException e) {

        }
    }

    /*
    This method accepts 3 arguments.
    Arg1: webdriver
    Arg2: expectedInUrl : for verify if the url contains given String.
        - If condition matches, will break loop.
    Arg3: expectedInTitle to be compared against actualTitle
     */
    public static void switchWindowAndVerify(String expectedInUrl, String expectedInTitle) {


        Set<String> allWindowsHandles = Driver.getDriver().getWindowHandles();

        for (String each : allWindowsHandles) {

            Driver.getDriver().switchTo().window(each);

            System.out.println("Current URL: " + Driver.getDriver().getCurrentUrl());

            if (Driver.getDriver().getCurrentUrl().contains(expectedInUrl)) {
                break;
            }
        }

        //5. Assert:Title contains “expectedInTitle”
        String actualTitle = Driver.getDriver().getTitle();
        Assert.assertTrue(actualTitle.contains(expectedInTitle));
    }

    /*
    This method accepts a String "expectedTitle" and Asserts if it is true
     */
    public static void verifyTitle(String expectedTitle) {

        Assert.assertEquals(Driver.getDriver().getTitle(), expectedTitle);

    }

    /**
     * This method will accept a String as expected value and verify actual URL CONTAINS the value.
     *
     * @param expectedInURL
     */
    public static void verifyURLContains(String expectedInURL) {
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains(expectedInURL));
    }


    /**
     * This method will accept a dropdown as a WebElement
     * and return all the options' text in a List of String.
     *
     * @param dropdownElement
     * @return List<String> actualOptionsAsString
     */
    public static List<String> dropdownOptionsAsString(WebElement dropdownElement) {
        Select select = new Select(dropdownElement);

        //List of all ACTUAL month <options> as a web element
        List<WebElement> actualOptionsAsWebElement = select.getOptions();

        //List of all ACTUAL month <options> as a string
        List<String> actualOptionsAsString = new ArrayList<>();

        for (WebElement each : actualOptionsAsWebElement) {
            actualOptionsAsString.add(each.getText());
        }

        return actualOptionsAsString;

    }


    /**
     * This method will accept a group radio buttons as a List of WebElement.
     * It will loop through the List, and click to the radio button with provided attributeValue
     *
     * @param radioButtons
     * @param attributeValue
     */
    public static void clickRadioButton(List<WebElement> radioButtons, String attributeValue) {

        for (WebElement each : radioButtons) {

            if (each.getAttribute("value").equalsIgnoreCase(attributeValue)) {
                each.click();
            }
        }
    }

    /**
     * Switches to new window by the exact title. Returns to original window if target title not found
     *
     * @param targetTitle
     */
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }


    /**
     * Switches to new window
     *
     *
     */

    public static void switchToNewWindow() {
        WebDriver driver = Driver.getDriver();
        String originalWindow = driver.getWindowHandle();

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                return;
            }
        }

        throw new RuntimeException("There is no new tab");
    }

    /**
     * Moves the mouse to given element
     *
     * @param element on which to hover
     */
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    /**
     * Simulates the drag and drop action on a web element using JavaScript.
     * This method calculates the center of the target element and performs
     * a drag and drop action to the specified offset.
     *
     * @param driver  the WebDriver instance
     * @param source  the WebElement to be dragged
     * @param xOffset the horizontal offset by which to drag the element
     * @param yOffset the vertical offset by which to drag the element
     */
    public static void dragAndDropElement(WebDriver driver, WebElement source, int xOffset, int yOffset) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "const target = arguments[0];" +
                        "const rect = target.getBoundingClientRect();" +
                        "const x = rect.left + (rect.width / 2);" +
                        "const y = rect.top + (rect.height / 2);" +
                        "const dataTransfer = new DataTransfer();" +
                        "const dragEvent = new DragEvent('dragstart', { bubbles: true, cancelable: true, dataTransfer });" +
                        "const dropEvent = new DragEvent('drop', { bubbles: true, cancelable: true, dataTransfer });" +
                        "target.dispatchEvent(dragEvent);" +
                        "target.dispatchEvent(new MouseEvent('mousemove', { clientX: x + arguments[1], clientY: y + arguments[2] }));" +
                        "target.dispatchEvent(dropEvent);" +
                        "target.dispatchEvent(new DragEvent('dragend', { bubbles: true, cancelable: true }));",
                source, xOffset, yOffset);
    }





    /**
     * Drags the given web element by the specified offset.
     *
     * @param driver2 the WebDriver instance
     * @param element the web element to drag
     * @param xOffset the horizontal offset
     * @param yOffset the vertical offset
     */
    public static void dragAndDropBy(WebDriver driver2, WebElement element, int xOffset, int yOffset) {
        Actions actions = new Actions(driver2);
        actions.dragAndDropBy(element, xOffset, yOffset).perform();
    }

    /**
     * This method simulates the action of clicking and holding a web element,
     * then dragging it down by a certain offset.
     *
     * @param driver the WebDriver instance
     * @param element the web element to be clicked and dragged
     * @param yOffset the vertical offset by which the element is to be moved. Positive values indicate downward movement.
     */
    public static void clickAndDragDown(WebDriver driver, WebElement element, int yOffset) {
        Actions actions = new Actions(driver);
        actions.clickAndHold(element)
                .moveByOffset(0, yOffset);
        try {
            // Wait for the drag-and-drop operation to complete
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actions.release()
                .perform();
    }



    /**
     * return a list of string from a list of elements
     *
     * @param list of webelements
     * @return list of string
     */
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    /**
     * Extracts text from list of elements matching the provided locator into new List<String>
     *
     * @param locator
     * @return list of strings
     */
    public static List<String> getElementsText(By locator) {

        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();

        for (WebElement el : elems) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    /**
     * Performs a pause
     *
     * @param seconds
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for the provided element to be visible on the page
     *
     * @param element
     * @param timeToWaitInSec
     * @return
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element matching the locator to be visible on the page
     *
     * @param locator
     * @param timeout
     * @return
     */
    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    /**
     * Sets the state of a checkbox. If the checkbox is already selected, it will be unchecked.
     * Sets the state of a checkbox. If the checkbox is already selected, it will be unchecked.
     * If the checkbox is not selected, it will be checked.
     *
     * If the checkbox is not selected, it will be checked.
     *
     * @param checkBox The checkbox WebElement to be set.
     */
    public static void setCheckBoxState (WebElement checkBox){
        if (checkBox.isSelected()){
            System.out.println("Checkbox is already selected. Unchecking now...");
            checkBox.click();
        }else {
            System.out.println("Checkbox is not selected. Checking it now...");
            checkBox.click();
        }

    }

    /**
     * Waits for provided element to be clickable
     *
     * @param timeout
     * @return
     */
    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for element matching the locator to be clickable
     *
     * @param locator
     * @param timeout
     * @return
     */
    public static WebElement waitForClickability(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * waits for backgrounds processes on the browser to complete
     *
     * @param timeOutInSeconds
     */
    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     *
     * @param by
     * @throws AssertionError if the element matching the provided locator is not found or not displayed
     */
    public static void verifyElementDisplayed(By by) {
        try {
            Assert.assertTrue("Element not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + by);

        }
    }

    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     *
     * @param by
     * @throws AssertionError the element matching the provided locator is displayed
     */
    public static void verifyElementNotDisplayed(By by) {
        try {
            Assert.assertFalse("Element should not be visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();

        }
    }

    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     *
     * @param element
     * @throws AssertionError the element matching the provided locator is displayed
     */
    public static void verifyElementNotDisplayed(WebElement element) {
        try {
            Assert.assertFalse("Element should not be visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("Element is not present, which is expected");

        }
    }


    /**
     * Compares two String values (expected and actual) and asserts whether they are equal.
     * If the values are not equal, an AssertionError is thrown with a message indicating
     * the expected and actual values do not match.
     *
     * @param expected The expected String value.
     * @param actual   The actual String value obtained during the test execution.
     * @throws AssertionError if the actual value does not match the expected value.
     */
    public static void verifyEquals(String expected, String actual) {
        Assert.assertEquals("The expected value does not match the actual value.",expected,actual );
    }



    /**
     * Compares two String values (expected and actual) and asserts whether they are equal.
     * If the values are not equal, an AssertionError is thrown with a message indicating
     * the expected and actual values do not match.
     *
     * @param expected The expected String value.
     * @param actual   The actual String value obtained during the test execution.
     * @throws AssertionError if the actual value does not match the expected value.
     */
    public static void verifyNotEquals(String expected, String actual) {
        Assert.assertNotEquals("The expected value still match the actual value.",expected,actual);
    }




    /**
     * Verifies whether the element is displayed on page
     *
     * @param element
     * @throws AssertionError if the element is not found or not displayed
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);

        }
    }

    /**
     * Controls the element is not visible
     *
     * @param element
     */
    public static void verifyElementIsNotDisplayed(WebElement element) {

        try {
            if (element != null && element.isDisplayed()) {
                Assert.fail("Element should not be visible : " + element);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("Element is not present or not visible, which is expected");
        }

        System.out.println("Related element is no visible, test passed");
    }


    /**
     * Waits for element to be not stale
     *
     * @param element
     */
    public static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            if (y == 1)
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException st) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (WebDriverException we) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    /**
     * Clicks on an element using JavaScript
     *
     * @param element
     */
    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }


    /**
     * Scrolls down to an element using JavaScript
     *
     * @param element
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scrolls down to an element using JavaScript
     *
     * @param element
     */
    public static void scrollToElementHorizontal(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'start'});", element);
    }

    /**
     * Performs double click action on an element
     *
     * @param element
     */
    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    /**
     * Changes the HTML attribute of a Web Element to the given value using JavaScript
     *
     * @param element
     * @param attributeName
     * @param attributeValue
     */
    public static void setAttribute(WebElement element, String attributeName, String attributeValue) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    /**
     * Highlighs an element by changing its background and border color
     *
     * @param element
     */
    public static void highlight(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        waitFor(1);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].removeAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    /**
     * Checks or unchecks given checkbox
     *
     * @param element
     * @param check
     */
    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    /**
     * attempts to click on provided element until given time runs out
     *
     * @param element
     * @param timeout
     */
    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param element
     */
    public static void executeJScommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript(command, element);

    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param command
     */
    public static void executeJScommand(String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript(command);

    }


    /**
     * This method will recover in case of exception after unsuccessful the click,
     * and will try to click on element again.
     *
     * @param by
     * @param attempts
     */
    public static void clickWithWait(By by, int attempts) {
        int counter = 0;
        //click on element as many as you specified in attempts parameter
        while (counter < attempts) {
            try {
                //selenium must look for element again
                clickWithJS(Driver.getDriver().findElement(by));
                //if click is successful - then break
                break;
            } catch (WebDriverException e) {
                //if click failed
                //print exception
                //print attempt
                e.printStackTrace();
                ++counter;
                //wait for 1 second, and try to click again
                waitFor(1);
            }
        }
    }

    /**
     * checks that an element is present on the DOM of a page.
     * This does not necessarily mean that the element is visible.
     *
     * @param by
     * @param time
     */
    public static void waitForPresenceOfElement(By by, int time) {
        new WebDriverWait(Driver.getDriver(), time).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * This method used for checking if a filter box empty or not
     */
    public boolean isInputFieldEmpty(WebElement inputField) {
        return inputField.getAttribute("value").isEmpty();
    }

    /**
     * Verifies whether the element is not clickable on the page.
     *
     * @param element The WebElement to check.
     * @throws AssertionError if the element is clickable.
     */
    public static void verifyElementNotClickable(WebElement element) {
        try {
            element.click();
            Assert.fail("Element is clickable: " + element);
        } catch (WebDriverException e) {
            System.out.println("Element is not clickable as expected: " + element);
        }
    }


}
