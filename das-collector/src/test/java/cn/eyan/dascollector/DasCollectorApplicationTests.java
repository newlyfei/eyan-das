package cn.eyan.dascollector;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DasCollectorApplicationTests {

	@Test
	public void contextLoads() {
	}

//	@Ignore("need chrome driver")
	@Test
	public void testSelenium() throws IOException, InterruptedException {
//		System.getProperties().setProperty("webdriver.chrome.driver",
//				"D:\\workspace\\lyf-space\\eyan-das\\");
		WebDriver webDriver = new ChromeDriver();
		webDriver.get("https://list.gome.com.cn/cat10000070.html?intcmp=sy-1000051970_0");
		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		System.out.println(webElement.getAttribute("outerHTML"));
		webDriver.close();

//		File file = new File("D:\\workspace\\lyf-space\\eyan-das\\chromedriver.exe"); //chromediriver的指定目录
//		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(file).usingAnyFreePort().build();
//		service.start();
//		WebDriver dr = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
//		dr.get("http://www.baidu.com"); //打开首页
//		System.out.println(dr.getPageSource());
//		Thread.sleep(3000);
	}

}
