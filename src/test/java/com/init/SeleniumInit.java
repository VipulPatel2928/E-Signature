package com.init;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.internal.Utils;
import com.indexpage.GeneralIndexpage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.utility.ExtentFactory;
import com.verification.GeneralVerification;

public class SeleniumInit {

	public String suiteName = "";
	public String testName = "";
	/* Minimum requirement for test configur ation */
	protected String testUrl; // Test url
	protected String seleniumHub; // Selenium hub IP
	protected String seleniumHubPort; // Selenium hub port
	protected String targetBrowser; // Target browser
	protected static String test_data_folder_path = null;
	public static String currentWindowHandle = ""; // Get Current Window handle
	public static String browserName = "";
	public static String osName = "";
	public static String browserVersion = "";
	public static String browsernm = "";

	public GeneralIndexpage generalIndexpage;
	public GeneralVerification generalVerification;

	public String email = "FrameWork@demo.com";
	public String password = "FrameWork";

	protected static String screenshot_folder_path = null;
	public static String currentTest; // current running test

	protected static Logger logger = Logger.getLogger("testing");
	protected WebDriver driver;

	public static ExtentReports report;//for the extented report
	public static ExtentTest logger1; //for the extented report
	
	// Common Common = new Common(driver);

	/* Page's declaration */

	/**
	 * Fetches suite-configuration from XML suite file.
	 * 
	 * @param testContext
	 */

	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) {
		testUrl = testContext.getCurrentXmlTest().getParameter("selenium.url");
		// testUrl = TestData.getURL();
		/* System.out.println("======" + testUrl + "========="); */
		seleniumHub = testContext.getCurrentXmlTest().getParameter("selenium.host");
		seleniumHubPort = testContext.getCurrentXmlTest().getParameter("selenium.port");
		targetBrowser = testContext.getCurrentXmlTest().getParameter("selenium.browser");
		browsernm = targetBrowser;
	}

	/**
	 * WebDriver initialization
	 * 
	 * @return WebDriver object
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext testContext) throws IOException, InterruptedException {

		currentTest = method.getName(); // get Name of current test.
		URL remote_grid = new URL("http://" + seleniumHub + ":" + seleniumHubPort + "/wd/hub");

		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";

		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();

		DesiredCapabilities capability = null;
		
		
		
		
		
		//logger1.log(LogStatus.INFO, "Chrome Browser Started");
		
		
		if (targetBrowser == null || targetBrowser.contains("firefox")) {
			System.setProperty("webdriver.gecko.driver", "lib/geckodriver_0_21_0.exe");
			FirefoxProfile profile = new FirefoxProfile();
			if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
				// path = "/Users/Jignesh/developer/test-automation";
			} else {
				// path = "c:\\Downloads_new";
			}

			profile.setPreference("dom.max_chrome_script_run_time", "999");
			profile.setPreference("dom.max_script_run_time", "999");
			profile.setPreference("browser.download.folderList", 2);
			// profile.setPreference("browser.download.dir", path);
			profile.setPreference("browser.helperApps.neverAsk.openFile",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download,manager.focusWhenStarting", false);
			// profile.setPreference("browser.download.useDownloadDir",true);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.closeWhenDone", false);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);

			profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
			profile.setPreference("pdfjs.disabled", true);
			profile.setAcceptUntrustedCertificates(true);
			profile.setPreference("security.OCSP.enabled", 0);
			//profile.setEnableNativeEvents(false);
			profile.setPreference("network.http.use-cache", false);

			// added Dependancy to disable hardware acceleration.

			/*
			 * profile.setPreference("gfx.direct2d.disabled",true);
			 * profile.setPreference("layers.acceleration.disabled", true);
			 */

			profile.setPreference("gfx.direct2d.disabled", true);
			profile.setPreference("layers.acceleration.disabled", true);
			// profile.setPreference("webgl.force-enabled", true);
			// Proxy proxy = new Proxy().setHttpProxy("localhost:3129");

			// cap.setCapability(CapabilityType.PROXY, proxy);

			capability = DesiredCapabilities.firefox();
			// proxy code
			// capability.setCapability(CapabilityType.PROXY,proxy);
			capability.setJavascriptEnabled(true);
			browserName = capability.getBrowserName();
			osName = System.getProperty("os.name");
			browserVersion = capability.getVersion().toString();

			System.out.println("=========" + "firefox Driver " + "==========");
			FirefoxOptions options = new FirefoxOptions();
			options.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			driver = new FirefoxDriver(options);
			//driver = new RemoteWebDriver(remote_grid, capability);

		} else if (targetBrowser.contains("ie11")) {
			capability = DesiredCapabilities.internetExplorer();
			System.setProperty("webdriver.ie.driver",
					"lib/IEDriverServer.exe");

		/*	capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setJavascriptEnabled(true);
			//capability.setCapability("ignoreZoomSetting", true);
			capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			browserName = capability.getVersion();
			osName = capability.getPlatform().getCurrent().name();
			browserVersion = capability.getVersion();

			InternetExplorerOptions options = new InternetExplorerOptions();
			options.setCapability("", value);
			
			driver = new InternetExplorerDriver(capability);
			
			*/
			capability.setJavascriptEnabled(true);
				InternetExplorerOptions options=new InternetExplorerOptions();
			  // options.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, testUrl);
			   options.setCapability("javascriptEnabled", true);
			 //  options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			   options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			   
			   driver = new InternetExplorerDriver(options);
			   driver.manage().window().maximize();
		
			//driver = new RemoteWebDriver(remote_grid, capability);

		} else if (targetBrowser.contains("opera")) {
			capability = DesiredCapabilities.opera();
			System.setProperty("webdriver.opera.driver",
					"/operadriver.exe");

			capability.setJavascriptEnabled(true);
			browserName = capability.getVersion();
			osName = capability.getPlatform().getCurrent().name();
			browserVersion = capability.getVersion();

			driver = new OperaDriver(capability);

		} else if (targetBrowser.contains("chrome")) {
			
			ChromeOptions options = new ChromeOptions();
			capability = DesiredCapabilities.chrome();
			
			
			
			System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
			
			report=ExtentFactory.getInstance(); //for the extented report
			logger1=report.startTest("Test Started"); //for the extented report
			
			
			File chromeDriver;
			
			if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
				 chromeDriver = new File("/lib/chromedriver");
			} else {
				 chromeDriver = new File("/lib/chromedriver.exe");
			}
			
			
		/*	System.setProperty("webdriver.chrome.driver",
				     chromeDriver.getAbsolutePath());*/
		
			capability.setCapability(ChromeOptions.CAPABILITY, options);
			capability.setBrowserName("chrome");
			capability.setJavascriptEnabled(true);
			browserName = capability.getVersion();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
			options.addArguments("disable-geolocation");
		
			driver = new ChromeDriver();
			
			logger1.log(LogStatus.INFO, "Chrome Browser Started");
			//driver = new RemoteWebDriver(remote_grid, capability);
		} 
		//Added for mobile 
		
		else if (targetBrowser.contains("Android_Chrome")) {
			   
			   DesiredCapabilities cap= new DesiredCapabilities();
			   URL remote_grid1 = null ;
			   try {
			   remote_grid1 = new URL("http://" + "localhost" + ":" + "4723" + "/wd/hub");
			   } catch (MalformedURLException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			   }
			   cap=new DesiredCapabilities();
			   //File apkFile = new File("app/Notepad.apk");
			   //File apkFile = new File("app/Facebook.apk");
			  // File apkFile = new File("app/redBus Online Bus Ticket Booking Hotel Booking_v6.6.5_apkpure.com.apk");

			  // cap.setCapability("app",apkFile.getAbsolutePath());
			   cap.setCapability("platformVersion", "6.0");
			   cap.setCapability("platformName", "Android");
			   cap.setCapability("deviceName", "TA09407DYT");
			   cap.setCapability("browserName", "Chrome");
			   /*cap.setCapability("appPackage", "com.app.workpulse.task");
			   cap.setCapability("appActivity", "com.workpulse.task.SplashActivity");*/
			   cap.setCapability("autoGrantPermissions", true);
			   cap.setCapability("noReset", false);//change by vipul
			   cap.setCapability("newCommandTimeout", 600);
			   cap.setCapability("unicodeKeyboard", true);
			   cap.setCapability("resetKeyboard", true);

			   //androidDriver = Factory.createAndroidDriver(remote_grid, cap);
			  // driver = new AndroidDriver<MobileElement>(remote_grid1, cap);
			   driver = new RemoteWebDriver(remote_grid1, cap);
			   driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			   
			  }
		
		//Above function added for the mobile 
		else if (targetBrowser.contains("safari")) {

			// System.setProperty("webdriver.safari.driver","/Users/jesus/Desktop/SafariDriver.safariextz");
			// driver = new SafariDriver();
			SafariDriver profile = new SafariDriver();

			capability = DesiredCapabilities.safari();
			capability.setJavascriptEnabled(true);
			capability.setBrowserName("safari");
			browserName = capability.getBrowserName();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
			// capability.setCapability(SafariDriver.CLEAN_SESSION_CAPABILITY,
			// profile);
			this.driver = new SafariDriver(capability);
		}
		

		generalIndexpage = new GeneralIndexpage(driver);
		generalVerification = new GeneralVerification(driver);

		suiteName = testContext.getSuite().getName();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(testUrl);
		System.out.println("TestData URL :: " + testUrl);
		driver.manage().window().maximize(); // keep off for the mobile

		currentWindowHandle = driver.getWindowHandle();
		System.out.println("Current Window Handle ID : --->>" + currentWindowHandle);

		suiteName = testContext.getSuite().getName();
		System.out.println("Current Xml Suite is:---->" + suiteName);

	}

	/**
	 * Log For Failure Test Exception.
	 * 
	 * @param tests
	 */
	private void getShortException(IResultMap tests) {

		for (ITestResult result : tests.getAllResults()) {

			Throwable exception = result.getThrowable();
			List<String> msgs = Reporter.getOutput(result);
			boolean hasReporterOutput = msgs.size() > 0;
			boolean hasThrowable = exception != null;
			if (hasThrowable) {
				boolean wantsMinimalOutput = result.getStatus() == ITestResult.SUCCESS;
				if (hasReporterOutput) {
					log("<h3>" + (wantsMinimalOutput ? "Expected Exception" : "Failure Reason:") + "</h3>");
				}

				// Getting first line of the stack trace
				String str = Utils.stackTrace(exception, true)[0];
				Scanner scanner = new Scanner(str);
				String firstLine = scanner.nextLine();
				log(firstLine);
			}
		}
	}

	/**
	 * After Method
	 * 
	 * @param testResult
	 */

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {

		ITestContext ex = testResult.getTestContext();

		try {
			testName = testResult.getName();
			if (!testResult.isSuccess()) {

				/* Print test result to Jenkins Console */
				System.out.println();
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: " + testResult.getThrowable());
				System.out.println("\n");
				Reporter.setCurrentTestResult(testResult);

				/* Make a screenshot for test that failed */
				String screenshotName = Common.getCurrentTimeStampString() + testName;
				Reporter.log("<br> <b>Please look to the screenshot - </b>");
				Common.makeScreenshot(driver, screenshotName);
				// Reporter.log(testResult.getThrowable().getMessage());
				getShortException(ex.getFailedTests());
			} else {
				try {
					Common.pause(5);
					/*
					 * driver.findElement(
					 * By.xpath("//div[@class='container']//a[contains(.,'Logout')]" )) .click();
					 */
					Common.pause(5);
				} catch (Exception e) {
					log("<br></br> Not able to perform logout");
				}

				System.out.println("TEST PASSED - " + testName + "\n"); // Print
				// test
				// resule
				// to
				// Jenkins
				// Console
			}

			/*
			 * final File folder = new File("C:/Downloads_new"); File files[] =
			 * folder.listFiles();
			 * 
			 * if (files.length > 0) { for (File f : files) { if (f.delete()) { System.out
			 * .println("file deleted From Downloads_new folder"); } }
			 * 
			 * }
			 */

			System.out.println("here is test status--------------------" + testResult.getStatus());

			//driver.manage().deleteAllCookies();

			driver.close();
			driver.quit();

		} catch (Throwable throwable) {
			System.out.println("message from tear down" + throwable.getMessage());
		}
	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public static void log(String msg) {
		System.out.println(msg);
		Reporter.log("<br></br>" + msg);
	}

	public static void logList(ArrayList<String> msg) {
		System.out.println(msg);
		Reporter.log("<br></br>" + msg);
	}

	public static void testDescription(String msg) {
		System.out.println(msg);
		Reporter.log("<strong> <h4 style=\"color:DarkViolet\"> " + "Testcase Description: " + msg + "</h4> </strong>");
	}

	public static void testcaseId(String msg) {
		System.out.println(msg);
		Reporter.log("<strong> <h4 style=\"color:DarkViolet\"> " + "Test Case ID: " + msg + "</h4> </strong>");
	}

	public static void logverification(int i, String msg) {
		System.out.println(msg);
		Reporter.log("<br></br><b style=\"color:OrangeRed \"> Expected Result-" + i + ": </b><b>" + msg + "</b>");
	}

}


/*public class SeleniumInit {

}*/


/*public class SeleniumInit {

}*/
