package autototem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RequestCatcher {

    static WElementFinder wf = new WElementFinder();
    static TxtManager tm = new TxtManager();
    static LastCharacterDestroyer ld = new LastCharacterDestroyer();

private void reach(){
    Browser.driver.get("https://legado.cesu.pe.gov.br/citsmart/jspEmbedded/59219/jsp_302_process.jsp");
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

    // Aceita o alerta "Capturas efetuadas com sucesso!" se aparecer
    try {
        new WebDriverWait(Browser.driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.alertIsPresent());
        Browser.driver.switchTo().alert().accept();
    } catch (Exception ignored) {}

    wf.hold();
}


}
