package autototem;

import org.openqa.selenium.WebElement;

import java.io.*;
import java.nio.Buffer;

class RequestNumberSaver {





    //C:\Users\joao.mendes\Desktop
   private String copyRequestNumber(){
        WElementFinder wf = new WElementFinder();
        WebElement RequestNumberLocation = wf.findFieldCss("div[id='divInsercao'] h3 b u");
        String RequestNumber = RequestNumberLocation.getText();
        System.out.println(RequestNumber);

        return RequestNumber;
    }

    void SaveRequestNumbers(){
        TxtManager tm = new TxtManager();
        String filename = "RequestList.txt";
        tm.createTxt(filename);
        tm.appendTxt(filename,copyRequestNumber() + ",");


    }




    public static void main(String[] args) {





    }
}
