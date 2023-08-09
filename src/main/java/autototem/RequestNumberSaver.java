package autototem;

import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class RequestNumberSaver {
    WElementFinder wf = new WElementFinder();

    //C:\Users\joao.mendes\Desktop
    void copyRequestNumber(){
        WebElement RequestNumberLocation = wf.findFieldCss("div[id='divInsercao'] h3 b u");
        String RequestNumber = RequestNumberLocation.getText();
        System.out.println(RequestNumber);

    }
    static void createTxt(String text,String path){
        try{
            File file = new File(path);

            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(text);
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    void sendToTxt (){

    }

    public static void main(String[] args) {
        createTxt("teste", "user.home");
        whenWriteStringUsingBufferedWritter_thenCorrect();

    }

    static void whenWriteStringUsingBufferedWritter_thenCorrect()
            {
        String str = "Hello";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Request"));
            writer.write(str);


            writer.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
