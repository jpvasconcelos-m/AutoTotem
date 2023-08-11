package autototem;

import org.openqa.selenium.WebElement;

public class RHIdLogger {

    static WElementFinder wf = new WElementFinder();


   static void openUrl(){
        Browser.driver.get("https://www.rhid.com.br/v2/#/login");
    }

    static void closeBroswer(){
       Browser.driver.close();
    }

    static void fillLogin(){

        String Login = "";
        WebElement LoginField = wf.findFieldXpath("//input[@id='email']");
        Login = "JOAOPEDRO@zeroum.com.br";
        LoginField.sendKeys(Login);

    }
    static void fillPassword(){

       WebElement PasswordField = wf.findFieldXpath("//input[@id='password']");
       String Password = "123456";
       PasswordField.sendKeys(Password);
    }
    static void clickButton(){
       WebElement LoginButton = wf.findFieldXpath("//button[@id='m_login_signin_submit']");
       LoginButton.click();
    }

    static void markPoint(){
       WebElement RegisterButton = wf.findFieldXpath("//button[@class='col-md-6 btn btn-primary ng-binding']");
       RegisterButton.click();
    }

    static void RHidAuto(){
        fillLogin();
        fillPassword();
        clickButton();
        markPoint();

    }
    static void selectPlace(){
       WebElement RegisterPlace = wf.findFieldXpath("//button[@class='col-md-12 btn btn-primary ng-binding']");
       RegisterPlace.click();
    }

    public static void main(String[] args) {
       openUrl();
       RHidAuto();
       selectPlace();


        try {
            Thread.sleep(3600000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        openUrl();
        fillPassword();
        clickButton();
        markPoint();
        selectPlace();

    }
}
