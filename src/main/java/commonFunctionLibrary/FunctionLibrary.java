package commonFunctionLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;

public class FunctionLibrary {

			static WebDriver driver;
			
			public static WebDriver startBrowser() throws Exception{
				
				if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")){
					System.setProperty("webdriver.chrome.driver", "C:\\Users\\praveena\\Desktop\\PC Anumala\\Selenium\\StockAccountingHybrid_MVN\\Drivers\\chromedriver.exe");
				    driver=new ChromeDriver();
				    
				}else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")){
					System.setProperty("webdriver.gecko.driver", "C:\\Users\\praveena\\Desktop\\PC Anumala\\Selenium\\StockAccountingHybrid_MVN\\Drivers\\geckodriver.exe");
					driver=new FirefoxDriver();
					
					
				}else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("ie")){
					System.setProperty("webdriver.internetexplorer.driver", "C:\\Users\\praveena\\Desktop\\PC Anumala\\Selenium\\StockAccountingHybrid_MVN\\Drivers\\IEDriverServer.exe");
					driver=new InternetExplorerDriver();
				
				}
				
				return driver;
			}
			 
			public static void openApplication() throws Exception{
				driver.get(PropertyFileUtil.getValueForKey("url")); 
				driver.manage().window().maximize();
				
			}
			
			public static void waitForElement(String locatortype,String locatorvalue, String waittime){
				
				WebDriverWait mywait=new WebDriverWait(driver, Integer.parseInt(waittime));
				
				if(locatortype.equalsIgnoreCase("id")){
					mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
				}else if(locatortype.equalsIgnoreCase("name")){
					mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
				}else if(locatortype.equalsIgnoreCase("xpath")){
					mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
				}else
				{
					System.out.println("unable to locate for waitForElement method");
				}
			}
			
		    public static void typeAction(String locatortype,String locatorvalue,String testdata){
				
				if(locatortype.equalsIgnoreCase("id")){
					driver.findElement(By.id(locatorvalue)).clear();
				    driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
				}
				else if(locatortype.equalsIgnoreCase("name")){
					driver.findElement(By.name(locatorvalue)).clear();
					driver.findElement(By.name(locatorvalue)).sendKeys(testdata);	
				}else if(locatortype.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorvalue)).clear();
					driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);	
				}else
				{
					System.out.println("Locator not matching for typeAction method");
				}
					
			}
			
			public static void clickAction(String locatortype,String locatorvalue){
				if(locatortype.equalsIgnoreCase("id")){
					driver.findElement(By.id(locatorvalue)).click();  
				}
				else if(locatortype.equalsIgnoreCase("name")){
					driver.findElement(By.name(locatorvalue)).click();	
				}
				else if(locatortype.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorvalue)).click();	
				}else
				{
					System.out.println("Locator not matching for clickAction method");
				}
			}
			
			public static void closeBrowser(){
				driver.close();
			}
			public static String generateDate(){
				Date date=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_hh_mm_ss");
				return sdf.format(date);
			}
			
			public static void captureData(WebDriver driver, String locatortype, String locatorvalue) throws Exception{
				Thread.sleep(5000);
				String supplierdata="";
				
				if(locatortype.equalsIgnoreCase("id")){
					
					supplierdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
				}
				else if(locatortype.equalsIgnoreCase("xpath")){
					supplierdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
				}
				
				FileWriter fw=new FileWriter("C:\\Users\\praveena\\Desktop\\PC Anumala\\Selenium\\StockAccountingHybrid_MVN\\CaptureData\\suppnumber.txt");
				BufferedWriter bw =new BufferedWriter(fw);
				bw.write(supplierdata);
				bw.flush();
				bw.close();
				
			}
		
			public static void tableValidation(WebDriver driver, String column) throws Exception{
				
				FileReader fr=new FileReader("C:\\Users\\praveena\\Desktop\\PC Anumala\\Selenium\\StockAccountingHybrid_MVN\\CaptureData\\suppnumber.txt");
				BufferedReader br =new BufferedReader(fr);
				
				String Exp_data = br.readLine();
				
				if(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).isDisplayed()){
					driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
					driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(Exp_data);
					driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search"))).click();
				}else{
					
					driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Click-searchpanel"))).click();
					driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
					driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(Exp_data);
					driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("click-search"))).click();
					
				}
				
				WebElement table = driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("sup-table")));
				List<WebElement>rows = table.findElements(By.tagName("tr"));
				
				for(int i=1;i<=rows.size();i++){
					
					String act_data = driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
					
					Assert.assertEquals(Exp_data,act_data , "table validation failed");
					break;
					
				}
				
			}
	
}
