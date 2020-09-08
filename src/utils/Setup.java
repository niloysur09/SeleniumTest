package utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Setup {
	
	public static WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public static ExtentTest test;
	public static boolean logStatus = true;
		
	
		@BeforeTest
		 public void extentReportSetup() {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
			//location of the extent report
			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/results/AutomationReport_" + timeStamp + ".html");
			extent = new ExtentReports();  //create object of ExtentReports
			extent.attachReporter(htmlReporter);
			htmlReporter.config().setDocumentTitle("Automation"); // Tittle of Report
			htmlReporter.config().setReportName("Test Automation Report"); // Name of the report
			htmlReporter.config().setTheme(Theme.DARK);//Default Theme of Report
			// General information releated to application
			extent.setSystemInfo("Application Name", "Calculator Test");
		  }
		
		@Parameters({ "browser", "url" })
		@BeforeClass
		public void driverSetUp(String browser, String url) throws Exception {
			//Initialize the driver
			if (browser.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();
			}
			else if (browser.equalsIgnoreCase("IE")) {
				driver = new InternetExplorerDriver();
			}
			driver.manage().window().maximize();
			//Open Application
			driver.get(url);
			
	    }
		
		 @AfterMethod
		  public void getResult(ITestResult result) throws Exception
			{
				if(result.getStatus() == ITestResult.FAILURE || logStatus == false)
				{
					//MarkupHelper is used to display the output in different colors
					test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
	
				}
				else if(result.getStatus() == ITestResult.SKIP){
					test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE)); 
				} 
				else if(result.getStatus() == ITestResult.SUCCESS)
				{
					test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
				}
				driver.close();
			}
		
		@AfterClass
	    public void driverClose() {
		  driver.quit();
	    }
		
		@AfterTest
		public void endReport() {
			extent.flush();
		}

}
