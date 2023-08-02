package autototem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WElementFinder {
    WebDriverWait wait = new WebDriverWait(Browser.driver, Duration.ofSeconds(30));

    WebElement findFieldXpath(String xpath)  {

        return wait.until(ExpectedConditions.elementToBeClickable((By.xpath(xpath))));

    }
    WebElement findFieldId(String id)  {

        return wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));

    }
    WebElement findFieldCss(String css)  {

        return wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));

    }

    void hold(){
        try{
            Thread.sleep(1500);}
        catch(InterruptedException e){
            throw new RuntimeException(e);
        }

    }
}
