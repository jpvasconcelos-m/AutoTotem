package autototem;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class RequestCreator {

    WElementFinder wf = new WElementFinder();
    String iframe1Xpath = "//iframe[@id='frameNovaSolicitacao']";
    String iframe2Xpath = "//iframe[@id='fraInformacoesComplementares']";






    void openRequestTab() {
        wf.findFieldXpath("//span[@class='btn btn-icon btn-primary']").click();
    }
    void switchToIframe(String xpath) {
        WebElement iframe = wf.findFieldXpath(xpath);
        Browser.driver.switchTo().frame(iframe);
    }
    void selectContract() {
        WebElement contractDropdown = wf.findFieldId("idContrato");
        wf.hold(); wf.hold();
        contractDropdown.click();
        contractDropdown.sendKeys("ses");
    }

    void changeToTab2() {
        wf.findFieldXpath("//a[@id='tab2']").click();
    }
void searchPerson(String person) {
        WebElement personField = wf.findFieldXpath("//input[@id='solicitante']");
        personField.sendKeys(person);
        wf.hold(); wf.hold();
        personField.sendKeys(Keys.DOWN);
        wf.hold(); wf.hold();
        personField.sendKeys(Keys.ENTER);
    }

    void changeToTab3() {
        wf.findFieldXpath("//a[@id='tab3']").click();
    }
    void fillAdditionalInformation(RequestTemplate template) {
        System.out.println("[DEBUG] Preenchendo campos em iframe2...");
        wf.findFieldXpath("//input[@id='campoDyn_3855']").sendKeys(template.getSector());
        System.out.println("[DEBUG] 3855 ok");
        wf.findFieldXpath("//input[@id='campoDyn_3856']").sendKeys(template.getContact());
        System.out.println("[DEBUG] 3856 ok");
        wf.findFieldXpath("//input[@id='campoDyn_3857']").sendKeys(template.getIp() != null ? template.getIp() : "");
        System.out.println("[DEBUG] fillAdditionalInformation concluido");
    }

    void serviceSelect(RequestTemplate template) {
        wf.findFieldXpath("//label[@id='lblPesquisarServicoBusca']//i[contains(text(),'search')]").click();
        WebElement serviceField = wf.findFieldXpath("//input[@id='filtroTableServicos']");
        serviceField.sendKeys(template.getServiceSearchId());
        wf.findFieldXpath("//td[contains(text(),'" + template.getServiceTableText() + "')]").click();
        wf.hold();
        WebElement descField = wf.findFieldXpath(
            "//div[@id='fieldDescricao']//div[@class='controls']//div[@class='controls']//iframe[@class='wysihtml5-sandbox']");
        descField.click();
        descField.sendKeys("a     " + template.getDescription());
    }

    void createRequest(RequestTemplate template) {
        System.out.println("[DEBUG] createRequest iniciado");
        openRequestTab();
        System.out.println("[DEBUG] Entrando em iframe1...");
        switchToIframe(iframe1Xpath);
        selectContract();
        changeToTab2();
        searchPerson(template.getRequester());
        changeToTab3();
        serviceSelect(template);
        System.out.println("[DEBUG] serviceSelect concluido, entrando no iframe2...");
        wf.hold();
        switchToIframe(iframe2Xpath);
        System.out.println("[DEBUG] Dentro de iframe2");
        fillAdditionalInformation(template);
        System.out.println("[DEBUG] Voltando ao frame pai e clicando Gravar...");
        Browser.driver.switchTo().parentFrame();
        wf.findFieldXpath("//button[@id='btnGravar']").click();
        System.out.println("[DEBUG] createRequest concluido");
    }

}
