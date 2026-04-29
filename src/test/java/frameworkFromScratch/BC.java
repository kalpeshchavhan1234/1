package frameworkFromScratch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BC {
	
	ThreadLocal<WebDriver> dd = new ThreadLocal<>(); 

	public WebDriver getD() {
		return dd.get();
	}

	public void setDriver(WebDriver d) {
		dd.set(d);
	}
	public void removeDriver() { 
		dd.remove();
	}

	public void getBrowser() throws IOException {
WebDriver driver = null;

		ChromeOptions op = new ChromeOptions();

		HashMap<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);
		op.addArguments("--start-maximized");
		op.addArguments("--disable-gpu");
		op.addArguments("--no-sandbox");
		op.addArguments("--disable-dev-shm-usage");


		op.setExperimentalOption("prefs", prefs);
		Properties prop = new Properties();

		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\myFile.properties");

		prop.load(file);
		String ss = prop.getProperty("browser");
		if (ss.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(op);

		} else if (ss.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

		}

		dd.set(driver);

		getD().get("https://www.saucedemo.com");
		getD().manage().window().maximize();
		getD().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
}