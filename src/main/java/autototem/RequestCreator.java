package autototem;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import javax.enterprise.inject.New;

public class RequestCreator {

WElementFinder wf = new WElementFinder();
String iframe1Xpath = "//iframe[@id='frameNovaSolicitacao']";
String iframe2Xpath = "//div[@id='fieldDescricao']//div[@class='controls']//div[@class='controls']//iframe[@class='wysihtml5-sandbox'];";

void openRequestTab(){
    WebElement NewRequestButton = wf.findFieldXpath(
            "//span[@class='btn btn-icon btn-primary']");
    NewRequestButton.click();
}
    //div[@id='fieldDescricao']//div[@class='controls']//div[@class='controls']//iframe[@class='wysihtml5-sandbox'];
void switchToIframe(String xpath){
    WebElement iframe = Browser.driver.findElement(By.xpath(xpath));
    Browser.driver.switchTo().frame(iframe);
}
void selectContract(){
    String Contract = "ses";
    WebElement ContractDropdown = wf.findFieldId("idContrato");
    wf.hold();wf.hold();
    ContractDropdown.click();
    ContractDropdown.sendKeys(Contract);

}
void changeToTab2(){
        WebElement Tab2Button = wf.findFieldXpath("//a[@id='tab2']");
        Tab2Button.click();
}
void searchPerson(){
        WebElement PersonField = wf.findFieldXpath("//input[@id='solicitante']");
        String Person = "Rodolfo de Oliveira Barros";
        PersonField.sendKeys(Person);
        wf.hold();wf.hold();
        PersonField.sendKeys(Keys.DOWN);
        wf.hold(); wf.hold();
        PersonField.sendKeys(Keys.ENTER);
    }

 void changeToTab3(){
    WebElement Tab3Button = wf.findFieldXpath("//a[@id='tab3']");
    Tab3Button.click();
 }
 String DescriptionStringer(int i){
     String[] Descricoes = {"Configuração do totem do balcão de informações"
     ,"Configuração do painel da ortopedia","Configuração do painel da marcação de consultas"
     ,"Configuração do totem da marcação de consultas.","Configuração do totem do primeiro andar",
     "Configuração do painel da clínica médica","Configuração do painel da clínica urológica"
     };
     return Descricoes[i];
 }
 void serviceSelect(int i){
    WebElement ActivityButton = wf.findFieldXpath(
            "//label[@id='lblPesquisarServicoBusca']//i[contains(text(),'search')]");
 ActivityButton.click();
    WebElement ActivityField = wf.findFieldXpath(
            "//input[@id='filtroTableServicos']");
    String ServiceId = "6056";
    ActivityField.sendKeys(ServiceId);
    WebElement ActivityButton2 = wf.findFieldXpath("//td[contains(text(),'SES (HGV) > Impressora e Digitalização > Configura')]");
            ActivityButton2.click();
    wf.hold();
    WebElement DescriptionField = wf.findFieldXpath("//div[@id='fieldDescricao']//div[@class='controls']//div[@class='controls']//iframe[@class='wysihtml5-sandbox']");//div[@class='controls']//iframe[@class='wysihtml5-sandbox']");
    DescriptionField.click();
    String Description = DescriptionStringer(i);
    DescriptionField.sendKeys("a     " + );
}
void createRequest(){

    openRequestTab();
    switchToIframe(iframe1Xpath);
    selectContract();
    changeToTab2();
    searchPerson();
    changeToTab3();
    serviceSelect();


}





}
