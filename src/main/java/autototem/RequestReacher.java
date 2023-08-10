package autototem;

import org.bouncycastle.cert.ocsp.Req;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RequestReacher {

    void reach(){
       Browser.driver.get(
               "https://www.cesu.pe.gov.br/citsmart/pages/gerenciamentoServicos/gerenciamentoServicos.load");

    }


}

