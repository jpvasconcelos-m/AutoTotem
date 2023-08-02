package autototem;

import org.openqa.selenium.WebElement;

public class LoginInserter {

    static String Login;
    static String Password;
    WebElement LoginField;
    WebElement PasswordField;









    void insertLogin(String Login, String Password){
        WElementFinder ww = new WElementFinder();
        LoginField = ww.findFieldXpath("//input[@id='user']");
        PasswordField = ww.findFieldXpath("//input[@id='senha']");
        try{
            Thread.sleep(2000);}
        catch(InterruptedException e){
            throw new RuntimeException(e);
        }
        LoginField.sendKeys(Login);
        PasswordField.sendKeys(Password);
        WebElement EnterButton  = ww.findFieldXpath(
                "//button[normalize-space()='Entrar']");
        EnterButton.click();

    }


}
