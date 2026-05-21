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

    static void causeDescription(String text){
        WebElement CauseDescriptionField = wf.findFieldXpath("//textarea[@id='detalhamentoCausa']");
        CauseDescriptionField.sendKeys(text);
    }
    static void selectSolution(String xpath){
        selectCause(xpath);
    }
    static void solutionDescription(String text){
        WebElement SolutionDescriptionField = wf.findFieldXpath("//textarea[@id='resposta']");
        SolutionDescriptionField.sendKeys(text);
    }




    void Finish(String causeDesc, String solutionDesc){

        Browser.driver.get("https://legado.cesu.pe.gov.br/citsmart/jspEmbedded/59219/jsp_303_process.jsp");

        WebElement FinishButton = wf.findFieldXpath(
                "/html[1]/body[1]/div[4]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[7]/div[1]/button[1]");

        fillRequestField();
        selectCause("//select[@id='idCausaIncidente']");
        causeDescription(causeDesc);
        selectSolution("//select[@id='idCategoriaSolucao']");
        solutionDescription(solutionDesc);

        FinishButton.click();


    }



}
