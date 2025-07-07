package autototem;

import java.io.*;

public class TxtManager {



     void createTxt(String filename){


        try {

              File file = new File("C:\\Users\\joao.mendes\\IdeaProjects\\AutoTotem\\src\\main\\java\\autototem", filename);

                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println("File Created!");
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
            System.out.println("Txt successful overwritten!");
        }
        catch(IOException e){

            e.printStackTrace();
            System.out.println("Fail Overwriting txt!");
        }

        }
        String readTxt(String filename){

         FileReader fr = null;
         BufferedReader br = null;
            try {
                 fr = new FileReader(filename);
                 br = new BufferedReader(fr);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

















}
