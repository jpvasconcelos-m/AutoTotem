package autototem;

import org.openqa.selenium.WebElement;

public class RequestFinisher {

    static WElementFinder wf = new WElementFinder();





    static void fillRequestField(){
    TxtManager tm = new TxtManager();
    RequestCatcher rc = new RequestCatcher();
    WebElement RequestField = wf.findFieldXpath("//input[@id='chamados']");

    String Requests = rc.createStringFromTxt();
    RequestField.sendKeys(Requests);


    }

   static void selectCause(String xpath){

            String Cause = "software";
            WebElement CauseDropdown = wf.findFieldXpath(xpath);
            wf.hold();wf.hold();
            CauseDropdown.click();
            CauseDropdown.sendKeys(Cause);


    }

    static void causeDescription(){
        WebElement CauseDescriptionField = wf.findFieldXpath("//textarea[@id='detalhamentoCausa']");
        String Cause = "Configuração corriqueira de painéis e totens.";
        CauseDescriptionField.sendKeys(Cause);
    }
    static void selectSolution(String xpath){
        selectCause(xpath);
    }
    static void solutionDescription(){
        WebElement SolutionDescriptionField = wf.findFieldXpath("//textarea[@id='resposta']");
        String Solution = "Configuração corriqueira de painéis e totens.";
        SolutionDescriptionField.sendKeys(Solution);
    }




    void Finish(){

        WebElement FinishButton = wf.findFieldXpath(
                "/html[1]/body[1]/div[4]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[7]/div[1]/button[1]");

        Browser.driver.get("https://www.cesu.pe.gov.br/citsmart/jspEmbedded/43871/jsp_303_process.jsp");
        fillRequestField();
        selectCause("//select[@id='idCausaIncidente']");
        causeDescription();
        selectSolution("//select[@id='idCategoriaSolucao']");
        solutionDescription();

        FinishButton.click();


    }



}
