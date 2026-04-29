package frameworkFromScratch;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ChechOut extends Wait {

	WebDriver driver;

	public ChechOut(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	By firstN = By.id("first-name");
	By lastN = By.id("last-name");
	By post = By.id("postal-code");
	By cont = By.id("continue");
	By totalC = By.className("summary_subtotal_label");
	By taxPrice = By.className("summary_tax_label");
	By totalP = By.className("summary_total_label");
	By finish = By.id("finish");

	public void getUserDetails(String name, String last, String postal) {

		waitForElementClickable(firstN);

		driver.findElement(firstN).sendKeys(name);
		driver.findElement(lastN).sendKeys(last);
		driver.findElement(post).sendKeys(postal);
		driver.findElement(cont).click();
	}

	public double getItemTotalPrice() {
		return Double.parseDouble(driver.findElement(totalC).getText().replace("Item total: $", ""));
	}

	public double getTaxPrice() {
		return Double.parseDouble(driver.findElement(taxPrice).getText().replace("Tax: $", ""));
	}

	public double getFinalTotal() {
		return Double.parseDouble(driver.findElement(totalP).getText().replace("Total: $", ""));
	}

	
		public boolean validateTotal(double expectedItemTotal) {

		    double itemTotal = getItemTotalPrice();
		    double tax = getTaxPrice();
		    double finalTotal = getFinalTotal();

		    double calculated = itemTotal + tax;

		    return Math.abs(itemTotal - expectedItemTotal) < 0.01
		        && Math.abs(calculated - finalTotal) < 0.01;
		}
	

	public boolean finish() {

	WebElement check=	driver.findElement(finish);
		
		check.click();
		
		return driver.getCurrentUrl().contains("complete.html");
		
	}
}
