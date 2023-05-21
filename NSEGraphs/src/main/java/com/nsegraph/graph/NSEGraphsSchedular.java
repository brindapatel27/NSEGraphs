package com.nsegraph.graph;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;

@Component
public class NSEGraphsSchedular {

	// Cron expression for 05:00 PM daily
	@Scheduled(cron = "0 0 17 * * ?") 
    public void takeScreenshot() throws IOException, InterruptedException {
        WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(options);

		driver.get("https://www.traderscockpit.com/?pageView=live-nse-advance-decline-ratio-chart");

		WebElement ratioChartDiv = driver.findElement(By.id("ratioChartDiv"));
		captureScreenshot(ratioChartDiv, "IntradayVsNiftyChart", ".png");

		Thread.sleep(2000);

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0, 500)");

		WebElement chartDiv = driver.findElement(By.id("chartdiv"));
		captureScreenshot(chartDiv, "NiftyAdvanceDeclineRatio", ".png");

		driver.quit();
    }
    
    private void captureScreenshot(WebElement ratioChartDiv, String fileName, String extension)
			throws IOException {

		// Take the screenshot and store as file format
		File scrFile = ratioChartDiv.getScreenshotAs(OutputType.FILE);

		// Open the current date and time
		String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());

		// Copy the screenshot on the desire location with different name using current
		// date and time
		FileUtils.copyFile(scrFile, new File("C:/shots/" + fileName + " " + timestamp + extension));
	}
}