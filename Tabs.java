
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
    	System.setProperty("webdriver.gecko.driver","/home/rajendra/installation/selenium/geckodriver");
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
        
        tabs.waitForGmailLoad("id", "Email");
        
        String gmailTab	=	driver.getWindowHandle();
        System.out.println("gmailTab = "+gmailTab.toString()+", "+driver.getCurrentUrl());
        
        driver.findElement(By.id("Email")).sendKeys("sneha.suh@gmail.com");
        driver.findElement(By.name("signIn")).click();
        
        tabs.waitForGmailLoad("name", "Passwd");
        
        driver.findElement(By.name("Passwd")).sendKeys("snehalgane");
        //signIn button click will not work
        driver.findElement(By.name("Passwd")).submit(); 
         
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
