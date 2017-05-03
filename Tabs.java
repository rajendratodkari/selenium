package automationFramework;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

import com.google.common.base.Predicate;
import com.sun.glass.events.KeyEvent;

public class Tabs {

	private static WebDriver driver;
	/**
	 * @param args
	 * @throws AWTException 
	 */
	public static void main(String[] args) throws AWTException {
		Tabs tabs = new Tabs();
    	System.setProperty("webdriver.gecko.driver","//home/rajendra/installation/chromedriver");
    	driver = new FirefoxDriver();
    	
    	 //First tab - agile
    	String baseUrl	=	"http://www.agilecloud.biz";
    	String emailUrl = 	"https://www.google.com/gmail/";
    	
        driver.get(baseUrl);
        
        
        String agileTab	=	driver.getWindowHandle();
        System.out.println("agileTab = "+agileTab.toString()+", "+driver.getTitle());

        //second tab - gmail login 
        
        tabs.openTab(emailUrl);
        
        tabs.switchWindow();
        
        String gmailTab	=	driver.getWindowHandle();
        System.out.println("gmailTab = "+gmailTab.toString()+", "+driver.getCurrentUrl());
        
        boolean isNewUI = driver.findElements(By.id("identifierId")).size() > 0;
        boolean isOldUI = driver.findElements(By.id("Email")).size() > 0;
        
        if (isOldUI) {
        	tabs.waitForGmailLoad("id", "Email");
        	
	        driver.findElement(By.id("Email")).sendKeys("sneha.suh@gmail.com");
	        driver.findElement(By.name("signIn")).click();
	        
	        tabs.waitForGmailLoad("name", "Passwd");
	       
	        driver.findElement(By.name("Passwd")).sendKeys("snehraj@2011");
	        //signIn button click will not work
	        driver.findElement(By.name("Passwd")).submit(); 
        }
        if (isNewUI) {
        	tabs.waitForGmailLoad("id", "identifierId");
        
        	System.out.println("New UI");
        	driver.findElement(By.id("identifierId")).sendKeys("sneha.suh@gmail.com");
        	driver.findElement(By.id("identifierNext")).click();
        	
        	tabs.waitForGmailLoad("name", "password");
        	driver.findElement(By.name("password")).sendKeys("snehraj@2011");
        	
        	driver.findElement(By.id("passwordNext")).click();
        	
        }
	}
	
	

	public void trigger(String script, WebElement element) {
	    ((JavascriptExecutor)driver).executeScript(script, element);
	}
	
	public Object trigger(String script) {
	    return ((JavascriptExecutor)driver).executeScript(script);
	}
	
	public void openTab(String url) {
	    String script = "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='.';d.body.appendChild(a);return a";
	    Object element = trigger(String.format(script, url));
	    if (element instanceof WebElement) {
	        WebElement anchor = (WebElement) element; 
	        anchor.click();
	        trigger("var a=arguments[0];a.parentNode.removeChild(a);", anchor);
	    } else {
	        throw new JavaScriptException(element, "Unable to open tab", 1);
	    }       
	}
	
	public void switchWindow() throws NoSuchWindowException, NoSuchWindowException {
	    Set<String> handles = driver.getWindowHandles();
	    String current = driver.getWindowHandle();
	    System.out.println("current = "+current.toString()+", "+driver.getCurrentUrl());
	    handles.remove(current);
	    String newTab = handles.iterator().next();
	    System.out.println("newTab = "+newTab.toString()+", "+driver.getCurrentUrl());
	    driver.switchTo().window(newTab);
	    System.out.println("*newTab = "+newTab.toString()+", "+driver.getCurrentUrl());
	}

	void waitForGmailLoad(String locatorKey, String locatorValue) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		//wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Email"))).click();
		switch(locatorKey) {
			case "id":
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue))).click();
				break;
			case "name":
				wait.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue))).click();
				break;
		}
	}
}
