package autototem;

import org.openqa.selenium.WebElement;

public class RequestCatcher {

    static WElementFinder wf = new WElementFinder();
    static TxtManager tm = new TxtManager();
    static LastCharacterDestroyer ld = new LastCharacterDestroyer();

private void reach(){
    Browser.driver.get("https://www.cesu.pe.gov.br/citsmart/jspEmbedded/43871/jsp_302_process.jsp");
}

String createStringFromTxt(){
    String Requests = tm.readTxt("RequestList.txt");
    Requests = ld.RemoveLastCharacter(Requests);
    return Requests;
}


void Catch(){
    reach();
    WebElement CatcherField = wf.findFieldXpath("//input[@id='chamados']");
    String Requests = createStringFromTxt();
    CatcherField.sendKeys(Requests);

    WebElement CatchButton = wf.findFieldXpath("//button[@id='btnGravar']");
   CatchButton.click();
}


}
