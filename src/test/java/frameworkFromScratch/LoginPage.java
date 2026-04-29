package frameworkFromScratch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;

	}

	// @FindBy
	By userName = By.id("user-name");
	By pass = By.id("password");
	By login = By.id("login-button");
	By error = By.tagName("h3");

	public void loginApp(String Uname, String pas) {
		driver.findElement(userName).sendKeys(Uname);
		driver.findElement(pass).sendKeys(pas);
		driver.findElement(login).click();

	}

	public String isLoginSucces() {

		return driver.getCurrentUrl();
	}

	public String checkAppN(String Uname, String pas) {

		driver.findElement(login).click();
		return driver.findElement(error).getText(); 

	}

	public String checkError(String Uname, String pas) {
		
		driver.findElement(userName).sendKeys(Uname);
		driver.findElement(pass).sendKeys(pas);
		driver.findElement(login).click();

		return driver.findElement(error).getText();
	}
}