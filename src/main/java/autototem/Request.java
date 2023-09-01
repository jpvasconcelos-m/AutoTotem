package autototem;

import org.bouncycastle.cert.ocsp.Req;

public class Request {

    private String Tag;
    private String Description;



     Request(String Tag, String Description){

         this.Tag = Tag;
         this.Description = Description;

     }


    public String getTag(){
         return Tag;
    }

    public String getDescription(){
         return Description;
    }
}
