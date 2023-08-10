package autototem;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser {

    String Cesu = "https://www.cesu.pe.gov.br/";
   static WebDriver driver = new ChromeDriver();//Open Browser.
    static WElementFinder wf = new WElementFinder();



     void accessAddress(String url){
        driver.get(url);
    }

   void closeBrowser(){
         driver.close();
   }

    public static void main(String[] args) throws Exception {

         int i = 0;
        Browser browser = new Browser();
        RequestReacher requestReacher = new RequestReacher();
        LoginInserter loginInserter = new LoginInserter();
        RequestCreator requestCreator = new RequestCreator();
        RequestNumberSaver requestNumberSaver = new RequestNumberSaver();

        browser.accessAddress("https://www.cesu.pe.gov.br/");
        loginInserter.insertLogin("joao.mendes@zerohum.com.br", "Joaopedro132@");


        while(i < 7) {

             wf.hold();
             requestReacher.reach();
             wf.hold();
             requestCreator.createRequest(i);
             requestNumberSaver.SaveRequestNumbers();
             i++;
             i=7;

         }
    }
}
