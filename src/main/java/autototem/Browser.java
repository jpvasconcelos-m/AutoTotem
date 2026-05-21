package autototem;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browser {

    static WebDriver driver;

    static void initDriver() {
        driver = new ChromeDriver();
    }

    void accessAddress(String url) {
        driver.get(url);
    }

    void closeBrowser() {
        driver.close();
    }

}
