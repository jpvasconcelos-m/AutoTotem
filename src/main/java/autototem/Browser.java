package autototem;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Scanner;

public class Browser {

    String Cesu = "https://www.cesu.pe.gov.br/";
   static WebDriver driver = new ChromeDriver();//Open Browser.
    static WElementFinder wf = new WElementFinder();
    static TxtManager tm = new TxtManager();



     void accessAddress(String url){
        driver.get(url);
    }

   void closeBrowser(){
         driver.close();
   }

    public static void main(String[] args) throws Exception {
        // Scanner sc = new Scanner(System.in);
        //System.out.println("Digite a quantidade de chamados do ciclo ");
        tm.overWriteTxt("RequestList.txt","");


        // int j = sc.nextInt();
         int i = 0;
        Browser browser = new Browser();
        RequestMenu requestMenu = new RequestMenu();
        LoginInserter loginInserter = new LoginInserter();
        RequestCreator requestCreator = new RequestCreator();
        RequestNumberSaver requestNumberSaver = new RequestNumberSaver();
        RequestCatcher requestCatcher = new RequestCatcher();
        RequestFinisher requestFinisher = new RequestFinisher();

        browser.accessAddress("https://www.cesu.pe.gov.br/");
        loginInserter.insertLogin("joao.mendes@zerohum.com.br", "Joaopedro132@");


        while(i < 6) {

             wf.hold();
             requestMenu.reach();
             wf.hold();
             requestCreator.createRequest(i);
             requestNumberSaver.SaveRequestNumbers();
             wf.hold();

             i++;


         }
        requestCatcher.Catch();
        wf.hold();
        requestFinisher.Finish();
    }
}
