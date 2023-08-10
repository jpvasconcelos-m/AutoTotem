package autototem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtManager {



     void createTxt(String filename){


        try {

              File file = new File("/home/joaop/Downloads/aprendendospring(1)/AutoTotem/", filename + ".txt");
                if (!file.exists()) {
                    file.createNewFile();
                }

        }
        catch(IOException e){
            e.printStackTrace();

            }

    }

     void appendTxt(String filename, String text)   {
            try {
                FileWriter fw = new FileWriter(filename, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(text);
                bw.close();
                System.out.println("Txt successful appended!");
            }
            catch(IOException e){

                e.printStackTrace();
                System.out.println("Fail appending txt.");
            }

        }
         void overWriteTxt(String filename, String text){
        try{
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.close();
            System.out.println("Txt successful overwrite!");
        }
        catch(IOException e){

            e.printStackTrace();
            System.out.println("Fail Overwriting txt!");
        }

        }















}
