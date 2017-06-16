package snehal;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class interactiveReport {

	public static void main(String[] args) throws Throwable {
		
		interactiveReport ir = new interactiveReport();
		
		// open chrome browser and enter url
		System.setProperty("webdriver.chrome.driver", "/home/rajendra/installation/chromedriver/chromedriver");
		WebDriver driver	=	new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("file:///home/rajendra/Downloads/DataTables/DataTables/test.html"); 
		
		//Action Download -------------
		
		//Select Download action and download csv file
		
		/*
		ir.setSelect(driver,"IvirActions", "Download");
		driver.switchTo().activeElement();
		Thread.sleep(3000);
		driver.findElement(By.className("CSV")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("okBtn")).click();
		Thread.sleep(5000);
		System.out.println("CSV file downloaded");
		
		//Select Download action and Download Excel file
		ir.setSelect(driver, "IvirActions", "Download");
		
		driver.switchTo().activeElement();
		Thread.sleep(3000);
		driver.findElement(By.className("EXCEL")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("okBtn")).click();
		Thread.sleep(3000);
		System.out.println("Excel file downloaded");
		
		//Select Download action and download PDF file
		ir.setSelect(driver, "IvirActions", "Download");
		driver.switchTo().activeElement();
		Thread.sleep(3000);
		driver.findElement(By.className("PDF")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("okBtn")).click();
		Thread.sleep(3000);
		System.out.println("PDF file downloaded");
		
		// Action columns ---- 
		//Select Action columns and uncheck show all checkbox and check only one serial no column is displaying.
		ir.setSelect(driver, "IvirActions", "Columns");
		driver.switchTo().activeElement();
		Thread.sleep(3000);
		driver.findElement(By.id("showAllColumns")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("okBtn")).click();
		Thread.sleep(5000);
		
		//show two columns
		ir.setSelect(driver, "IvirActions", "Columns");
		driver.switchTo().activeElement();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[data-index='2']")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("okBtn")).click();
		Thread.sleep(5000);
		
		//Show 3 columns
		ir.setSelect(driver, "IvirActions", "Columns");
		driver.switchTo().activeElement();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[data-index='3']")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("okBtn")).click();
		Thread.sleep(5000);
		
		//Show 4 columns
		ir.setSelect(driver, "IvirActions", "Columns");
		driver.switchTo().activeElement();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[data-index='4']")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("okBtn")).click();
		Thread.sleep(5000);
		
		//Show all Columns
		ir.setSelect(driver, "IvirActions", "Columns");
		driver.switchTo().activeElement();
		Thread.sleep(3000);
		driver.findElement(By.id("showAllColumns")).click();
		Thread.sleep(1000);
		driver.findElement(By.className("okBtn")).click();
		Thread.sleep(5000);
		*/
		
		//Select Chart action and display pi chart
		ir.setSelect(driver, "IvirActions", "Chart");
		driver.switchTo().activeElement();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("button[data-type='pie']")).click();
		driver.findElement(By.id("ivirChartName")).sendKeys("Pi chart");
		ir.setSelect(driver, "ivirChartCol", "Country");
		ir.setSelect(driver, "ivirChartVal", "Sr. No.");
		driver.findElement(By.className("okBtn")).click();
		driver.switchTo().defaultContent();
		
		Thread.sleep(2000);
		
		ir.setSelect(driver, "IvirActions", "Chart");
		driver.switchTo().activeElement();		
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("button[data-type='column']")).click();
		driver.findElement(By.id("ivirChartName")).sendKeys("column chart");
		ir.setSelect(driver, "ivirChartCol", "Country");
		ir.setSelect(driver, "ivirChartVal", "Sr. No.");
		driver.findElement(By.className("okBtn")).click();
		driver.switchTo().defaultContent();
		
		Thread.sleep(2000);
		
		ir.waitAndLoadElement(driver, "id", "ivirChartPills", null, false);
		
		WebElement ivirChartPills	=	driver.findElement(By.id("ivirChartPills"));
		List<WebElement> divList	=	ivirChartPills.findElements(By.tagName("div"));
		for (WebElement div:divList) {
			String divId = div.getAttribute("id");
			System.out.println("closing - "+divId);
			WebElement chartCloseDiv	=	driver.findElement(By.id(divId));
			List<WebElement> spanList	=	chartCloseDiv.findElements(By.tagName("span"));
			for (WebElement span:spanList) {
				if (span.getAttribute("class").equals("icon-arrows-remove pillRemove")) {
					span.click();
					Thread.sleep(2000);
				}
			}
		}

		
		

	}
	public void setSelect(WebDriver driver, String dropdownId,String visibletext) {
		Select allselect	=	new Select(driver.findElement(By.id(dropdownId)));
		allselect.selectByVisibleText(visibletext);
		
}
	public void waitAndLoadElement(WebDriver driver, String elementFindKey, String elementFindKeyVal, String elementVal, boolean click) {
		System.out.println("finding element by "+elementFindKey+" : "+elementFindKeyVal + " : "+ elementVal);
		
		WebDriverWait wait = new WebDriverWait(driver, 15);
		
		switch(elementFindKey) {
			case "id":
				if (click == true) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementFindKeyVal))).click();
				} else if (elementVal != null) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementFindKeyVal))).sendKeys(elementVal);
				} else {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementFindKeyVal)));
				}
				break;
			case "name":
				if (click == true) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name(elementFindKeyVal))).click();
				} else if (elementVal != null) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name(elementFindKeyVal))).sendKeys(elementVal);
				} else {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name(elementFindKeyVal)));
				}
				break;
			case "linktext":
				if (click == true) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(elementFindKeyVal))).click();
				} else if (elementVal != null) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(elementFindKeyVal))).sendKeys(elementVal);
				} else {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(elementFindKeyVal)));
				}
				break;
			case "class":
				if (click == true) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.className(elementFindKeyVal))).click();
				} else if (elementVal != null) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.className(elementFindKeyVal))).sendKeys(elementVal);
				} else {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.className(elementFindKeyVal)));
				}
				break;
			case "tag":
				if (click == true) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(elementFindKeyVal))).click();
				} else if (elementVal != null) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(elementFindKeyVal))).sendKeys(elementVal);
				} else {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(elementFindKeyVal)));
				}
				break;
		} 
	}

}
