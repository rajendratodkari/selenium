package snehal;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SalesOrder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sales sales = new Sales();
		System.setProperty("webdriver.chrome.driver", "/home/rajendra/installation/chromedriver/chromedriver");
		WebDriver driver	=	new ChromeDriver();
		 driver.manage().window().maximize();
	        //Sign in Cloud 
		 	driver.get("http://14.192.17.25");
	        driver.findElement(By.id("signinm")).click();
	        String currentpass = "Agile@123";
	        
	        driver.findElement(By.xpath(".//*[@id='ContentPlaceHolder1_UserName']")).sendKeys("sneha.suh@gmail.com");
	        driver.findElement(By.xpath(".//*[@id='ContentPlaceHolder1_Password']")).sendKeys(currentpass);
	        driver.findElement(By.xpath(".//*[@id='ContentPlaceHolder1_LoginButton']")).click();
	        
	        //open business management app
	        List<WebElement> appList = driver.findElements(By.className("myLogo"));
	        Boolean bmao = false;
	        for(WebElement e : appList) {
	        	WebElement parent = e.findElement(By.xpath(".."));
	        	System.out.println(parent.getText());
	        	if (parent.getText().indexOf("Business Management Application")>-1 && parent.getText().indexOf("Owned")>-1){
	        		bmao = true;
	        		e.click();
	        	}
	        }
	        if (bmao == true) {
	        	WebElement leftPanel	=	driver.findElement(By.id("ContentPlaceHolder1_appFrame"));
	        	driver.switchTo().frame(leftPanel);
	        	
	        	sales.waitAndLoadElement(driver, "id", "moduleSelector", null, true);
	        	sales.waitAndLoadElement(driver, "id", "Head741", null, true);
	        	sales.waitAndLoadElement(driver, "linktext", "Manage Sales Orders", null, true);
	        	
	        	WebElement contentPanel =	driver.findElement(By.id("middle1"));
	        	driver.switchTo().frame(contentPanel);
	        	
	        	sales.waitAndLoadElement(driver, "id", "btn_newso", null, true);
	        	
	        	sales.waitAndLoadElement(driver, "id", "subledger000F1", null, true);
	        	sales.setSelect(driver, "subledger000F1");
	        	sales.setSelect(driver, "payterm000F1");
	        	sales.setSelect(driver, "salestaxtype000F1");
	        	
	        	sales.waitAndLoadElement(driver, "id", "img~itemcode001F2", null, true);
	        	sales.waitAndLoadElement(driver, "id", "tblPickData", null, true);
	        	
	        	//String itemNameId	=	sales.getRandomIdFromPickList(driver, "itemcode001F2");
	        	//System.out.println("itemNameId = "+itemNameId);
	        	//sales.waitAndLoadElement(driver, "id", "img~itemcode001F2", null, true);
	        	//sales.waitAndLoadElement(driver, "id", itemNameId, null, true);
	        	
	        	sales.setSelect(driver, "taxcategory001F2");
	        	sales.waitAndLoadElement(driver, "id", "soqty001F2", sales.getRandomNumber(1, 5)+"", false);
	        	sales.waitAndLoadElement(driver, "id", "fobrate001F2", sales.getRandomNumber(1, 20)+"", false);
	        	sales.waitAndLoadElement(driver, "class", "editLayoutFooter>button", null, true);
	        	
	        	
	        	
			}
	        
	      
	}
	
	public String getRandomIdFromPickList(WebDriver driver, String pickListId) {
		WebElement table	=	driver.findElement(By.cssSelector("table[data-pick='"+pickListId+"']"));
		//WebElement table	=	driver.findElement(By.xpath("//table[@data-pick='"+pickListId+"']"));
		
		ArrayList<String> ids	=	new ArrayList<>();
		List<WebElement> trList	=	table.findElements(By.tagName("tr"));
		for (WebElement tr:trList) {
			//	there is only one <td> in tr, hence no need to extract list of tds
			WebElement td	=	tr.findElement(By.tagName("td"));
			System.out.println("td = "+td.getAttribute("id"));
			ids.add(td.getAttribute("id"));
		}
		if (ids.isEmpty()) {
			return null;
		}
		int random	=	this.getRandomNumber(0, ids.size());
		return ids.get(random);
	}
	
	public int getRandomNumber(int low, int high) {
		Random random = new Random();
		return random.nextInt(high - low) + low;
	}
	
	public void setSelect(WebDriver driver, String dropdownId) {
		Select customer	=	new Select(driver.findElement(By.id(dropdownId)));
		List<WebElement> options = customer.getOptions();
		int random	=	this.getRandomNumber(1, options.size());	//	0 == --Select--, hence random number starts with 1
		System.out.println("Dropdown : "+dropdownId+" : size = "+options.size()+" : random = "+random);
		customer.selectByIndex(random);
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
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name(elementFindKeyVal)));
				}
				break;
			case "name":
				if (click == true) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name(elementFindKeyVal))).click();
				} else if (elementVal != null) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementFindKeyVal))).sendKeys(elementVal);
				} else {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name(elementFindKeyVal)));
				}
				break;
			case "linktext":
				if (click == true) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(elementFindKeyVal))).click();
				} else if (elementVal != null) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementFindKeyVal))).sendKeys(elementVal);
				} else {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name(elementFindKeyVal)));
				}
				break;
			case "class":
				if (click == true) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.className(elementFindKeyVal))).click();
				} else if (elementVal != null) {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.id(elementFindKeyVal))).sendKeys(elementVal);
				} else {
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name(elementFindKeyVal)));
				}
				break;
		} 
	}

}
