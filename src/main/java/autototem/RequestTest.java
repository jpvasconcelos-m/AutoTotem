package autototem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RequestTest {
    static Request r1 = new Request("1021546","Configuração do totem do balcão de informações.");
    static Request r2 = new Request("1020905","Configuração do painel da ortopedia.");
    static Request r3 = new Request("1021551","Configuração do painel da marcação de consultas.");
    static Request r4 = new Request("1021021","Configuração do totem da marcação de consultas.");
    static Request r5 = new Request("1021268","Configuração do totem do primeiro andar.");
    static Request r6 = new Request("1020932","Configuração do painel da clínica médica.");
    static Request r7 = new Request("1020901","Configuração do painel da clínica urológica.");
    public static void main(String[] args) {

        List<Request> l = new ArrayList<>(Arrays.asList(r1,r2,r3,r4,r5,r6,r7));
        System.out.println(l.get(2).getTag());


    }
}
