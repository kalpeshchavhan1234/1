package frameworkFromScratch;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility1 implements ITestListener {

	ExtentReports extent = ExtentR.getReport();
	ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest t = extent.createTest(result.getMethod().getMethodName());
		test.set(t);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

	    ExtentTest t = test.get();

	    if (t == null) {
	        t = extent.createTest(result.getMethod().getMethodName());
	        test.set(t);
	    }

	    t.fail(result.getThrowable()); 

	    try {
	        Object currentClass = result.getInstance();
	        WebDriver driver = ((BC) currentClass).getD();

	        if (driver != null) {
	            String path = ExtentR.takeScreenS(driver, result.getMethod().getMethodName());
	            t.addScreenCaptureFromPath(path);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	
	
	public static List<HashMap<String, Object>> readJson(String filePath) throws IOException {

	    String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));

	    ObjectMapper mapper = new ObjectMapper();

	    return mapper.readValue(jsonContent,
	            new TypeReference<List<HashMap<String, Object>>>() {});
	}
}