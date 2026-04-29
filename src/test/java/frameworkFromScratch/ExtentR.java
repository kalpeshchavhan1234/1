package frameworkFromScratch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentR {

    private static ExtentReports extent;

    public static ExtentReports getReport() {

        if (extent == null) {

            File file = new File(System.getProperty("user.dir") + "/MyRF/R.html");

            ExtentSparkReporter spark = new ExtentSparkReporter(file);
            spark.config().setDocumentTitle("MyReport");
            spark.config().setReportName("My report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }

        return extent;
    }

    public static String takeScreenS(WebDriver driver, String testName) throws IOException { 

        if (driver == null) {
            System.out.println("Driver NULL - screenshot skipped");
            return "";
        }

        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        String path = System.getProperty("user.dir")
                + "/MyRF/failed/"
                + testName + "_"
                + System.currentTimeMillis()
                + ".png";

        File dest = new File(path);
        dest.getParentFile().mkdirs();

        FileUtils.copyFile(src, dest); 

        return path;
    }
}