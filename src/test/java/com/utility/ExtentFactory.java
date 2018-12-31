package com.utility;

import java.io.File;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {
	
	public static ExtentReports getInstance() {
		  ExtentReports extent;
		  String path="./test-output/reports.html";
		  extent=new ExtentReports(path, true);
		  extent.addSystemInfo("Selenium Version", "3.5").addSystemInfo("Platform", "Windows 10")
		  .addSystemInfo("UserName", "Vipul").addSystemInfo("Environment", "Automation Testing");
		  
		  extent.loadConfig(new File(System.getProperty("user.dir")+ "//config.xml"));
		  
		  return extent;
		  
		 }

}
