package autototem;

public class LastCharacterDestroyer {

   String  RemoveLastCharacter(String str){
       StringBuilder sb = new StringBuilder(str);


        StringBuilder result = sb.deleteCharAt(str.length()-1);
        str = result.toString();
       System.out.println(str);
       return str;
   }




}
