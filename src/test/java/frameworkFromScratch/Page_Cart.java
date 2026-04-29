package frameworkFromScratch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Page_Cart extends Wait {

	WebDriver driver;

	public Page_Cart(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	By products = By.className("inventory_item");
	By productName = By.className("inventory_item_name");
	By addToCart = By.className("btn_inventory");
	By cartBadge = By.className("shopping_cart_badge");
	By price = By.className("inventory_item_price");

	public boolean isProductPresent(Map<String, String> data) {

		waitForAllElement(products);
		List<WebElement> items = driver.findElements(products);

		for (String name : data.keySet()) {

			boolean found = items.stream()
					.anyMatch(e -> e.findElement(productName).getText().trim().equalsIgnoreCase(name));

			if (!found)
				return false;
		}
		return true;
	}

	public void addProducts(Map<String, String> data) throws InterruptedException {

		waitForAllElement(products);
		List<WebElement> items = driver.findElements(products);

		for (String name : data.keySet()) {

			for (WebElement item : items) {

				String uiName = item.findElement(productName).getText().trim();
				Thread.sleep(2000);
				if (uiName.equalsIgnoreCase(name)) {
					Thread.sleep(2000);
					item.findElement(addToCart).click();
					break;
				}
			}
		}
	}

	public double getExpectedTotal(Map<String, String> data) {

		double total = 0;

		for (String priceVal : data.values()) {
			total += Double.parseDouble(priceVal.replace("$", ""));
		}

		return total;
	}

	public int getCartCount() {
		waitForElement(cartBadge);
		return Integer.parseInt(driver.findElement(cartBadge).getText());
	}
}