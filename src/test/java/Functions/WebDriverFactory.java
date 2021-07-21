package Functions;

import com.microsoft.edge.seleniumtools.EdgeDriver;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
	static String resourceFolder="src/test/java/Drivers/";
    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(WebDriverFactory.class);
    
	private static WebDriverFactory instance = null;
	    
    private WebDriverFactory() {    
    }
    
    /**
     * Singleton pattern
     * @return a single instance
     */
    public static WebDriverFactory getInstance() {
        if (instance == null) {
            instance = new WebDriverFactory();
        }
        return instance;
    }    
	
		
	 public static WebDriver createNewWebDriver(String browser, String headless){
		 WebDriver driver;

		 /******** The driver selected is Local: Firefox  ********/
		if ("FIREFOX".equalsIgnoreCase(browser)) {
			System.setProperty("webdriver.gecko.driver", resourceFolder+"/geckodriver.exe");
			if(headless.equalsIgnoreCase("SI")||headless.equalsIgnoreCase("YES")||headless.equalsIgnoreCase("TRUE")||headless.equalsIgnoreCase("FALSO")) {
				driver = new FirefoxDriver(new FirefoxOptions().addArguments("disable-gpu").addArguments("--headless"));
			}
			else{
				driver = new FirefoxDriver();
			}
		}
		 /******** The driver selected is Chrome  ********/
		 else if ("CHROME".equalsIgnoreCase(browser)) {
			System.setProperty("webdriver.chrome.driver", resourceFolder+"/chromedriver.exe");
			if(headless.equalsIgnoreCase("SI")||headless.equalsIgnoreCase("YES")||headless.equalsIgnoreCase("TRUE")||headless.equalsIgnoreCase("FALSO")) {
				driver = new ChromeDriver(new ChromeOptions().addArguments("disable-gpu").addArguments("--headless"));
			}
			else{
				driver = new ChromeDriver();
			}
		 }
		/******** The driver selected is Microsoft Edge ********/
		else if ("EDGE".equalsIgnoreCase(browser)) {
			System.setProperty("webdriver.edge.driver", resourceFolder+"/msedgedriver.exe");
			if(headless.equalsIgnoreCase("SI")||headless.equalsIgnoreCase("YES")||headless.equalsIgnoreCase("TRUE")||headless.equalsIgnoreCase("FALSO")) {
				driver = new EdgeDriver(new EdgeOptions().addArguments("disable-gpu").addArguments("--headless"));
			}
			else{
				driver = new EdgeDriver();
			}
		}
		 /******** The driver is not selected  ********/
		 else {
			 log.error("The Driver is not selected properly, invalid name: " + browser);
			 return null;
		 }
		 driver.manage().window().maximize();
		 return driver;

        }
	}
