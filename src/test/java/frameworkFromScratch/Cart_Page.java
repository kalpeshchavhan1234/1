package frameworkFromScratch;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Cart_Page extends Wait {

    WebDriver driver;

    public Cart_Page(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    By cartIcon = By.className("shopping_cart_link");
    By products = By.className("cart_item");
    By productName = By.className("inventory_item_name");
    By price = By.className("inventory_item_price");
    By check = By.id("checkout");

    public void clickCart() {
        driver.findElement(cartIcon).click();
    }

    public boolean validateProductAndPrice(Map<String, String> data) { 

        System.out.println("METHOD CALLED");

        waitForAllElement(products);

        List<WebElement> items = driver.findElements(products); 
        System.out.println("Items size : " + items.size()); 

        for (Map.Entry<String, String> entry : data.entrySet()) {

            String expectedName = entry.getKey();
            String expectedPrice = entry.getValue().replace("$", "").trim();

            boolean match = items.stream().anyMatch(item -> {

                String uiName = item.findElement(productName).getText().trim();
                String uiPrice = item.findElement(price).getText().replace("$", "").trim();

                System.out.println("UI: " + uiName + " - " + uiPrice);
                System.out.println("Expected: " + expectedName + " - " + expectedPrice);

                return uiName.equalsIgnoreCase(expectedName) 
                        && uiPrice.equals(expectedPrice);
            });

            if (!match) {
                System.out.println("❌ FAILED FOR: " + expectedName);
                return false;
            }
        }

        return true;
    }

    public void clickToCheckout() {
        waitForElementClickable(check);
        driver.findElement(check).click();
    }
}