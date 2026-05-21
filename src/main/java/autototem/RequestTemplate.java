package autototem;

import java.util.ArrayList;
import java.util.List;

public class RequestTemplate {

    private String name;
    private String serviceSearchId;
    private String serviceTableText;
    private String requester;
    private String tag;
    private String description;
    private String unity;
    private String sector;
    private String contact;
    private String ip;
    private int quantity;
    /** Lista de valores para substituir $nome nos campos. Se preenchida, ignora quantity. */
    private List<String> nameList = new ArrayList<>();

    public RequestTemplate() {}

    public RequestTemplate(String name, String serviceSearchId, String serviceTableText,
                           String requester, String tag, String description,
                           String unity, String sector, String contact, int quantity) {
        this.name = name;
        this.serviceSearchId = serviceSearchId;
        this.serviceTableText = serviceTableText;
        this.requester = requester;
        this.tag = tag;
        this.description = description;
        this.unity = unity;
        this.sector = sector;
        this.contact = contact;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getServiceSearchId() { return serviceSearchId; }
    public void setServiceSearchId(String serviceSearchId) { this.serviceSearchId = serviceSearchId; }

    public String getServiceTableText() { return serviceTableText; }
    public void setServiceTableText(String serviceTableText) { this.serviceTableText = serviceTableText; }

    public String getRequester() { return requester; }
    public void setRequester(String requester) { this.requester = requester; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUnity() { return unity; }
    public void setUnity(String unity) { this.unity = unity; }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public List<String> getNameList() { return nameList != null ? nameList : new ArrayList<>(); }
    public void setNameList(List<String> nameList) { this.nameList = nameList != null ? nameList : new ArrayList<>(); }

    @Override
    public String toString() {
        if (nameList != null && !nameList.isEmpty()) {
            return name + "  [lista: " + nameList.size() + " nomes]";
        }
        return name + "  [x" + quantity + "]";
    }
}
