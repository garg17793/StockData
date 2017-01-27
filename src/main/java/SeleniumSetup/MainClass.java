package SeleniumSetup;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MainClass {
	WebDriver driver = null;

	public static void main(String[] args) {
		MainClass obj = new MainClass();
		obj.setUp();
		obj.getAllCompanies();
	}

	private void setUp() {

		DesiredCapabilities capability = null;
		String url = "http://www.moneycontrol.com/stocks/marketinfo/marketcap/nse/";
		String workingDir = System.getProperty("user.dir");
		workingDir += "/lib/WebDrivers/";

		System.setProperty("webdriver.chrome.driver", workingDir + "chromedriver");

		capability = DesiredCapabilities.chrome();
		capability.setBrowserName("chrome");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(url);
	}

	private void getAllCompanies() {
		driver.manage().timeouts().implicitlyWait(16, TimeUnit.SECONDS);
		File file = new File("CompaniesName");
		PrintWriter writer = null;
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			writer = new PrintWriter(file);
		} catch (Exception e) {
		}

		// JavascriptExecutor jse = (JavascritExecutor) driver;
		// jse.executeScript("window.scrollBy(0,+500)", "");

		for (int i = 1; i <= 114; i++) {
			WebElement leftMenu = driver.findElement(By.className("lftmenu"));
			List<WebElement> lis = leftMenu.findElements(By.tagName("li"));
			// System.out.println(lis.get(i).getText()+"====================================");
			//writer.write(lis.get(i).getText() + "====================================\n");
			lis.get(i).click();
			driver.manage().timeouts().implicitlyWait(16, TimeUnit.SECONDS);
			WebElement table = driver.findElement(By.className("tbldata14"));
			List<WebElement> companiesName = table.findElements(By.className("bl_12"));
			for (WebElement webElement : companiesName) {
				// System.out.println(webElement.getText());
				writer.write(webElement.getText()+"\n");
			}
			writer.flush();
			// break;
		}
	}

}
