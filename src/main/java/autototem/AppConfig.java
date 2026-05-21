package autototem;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {

    private String username;
    private String password;
    private String causeDescription;
    private String solutionDescription;
    private List<RequestTemplate> queue;

    public AppConfig() {
        this.username = "";
        this.password = "";
        this.causeDescription = "Configuração corriqueira de painéis e totens.";
        this.solutionDescription = "Configuração corriqueira de painéis e totens.";
        this.queue = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCauseDescription() { return causeDescription != null ? causeDescription : ""; }
    public void setCauseDescription(String causeDescription) { this.causeDescription = causeDescription; }

    public String getSolutionDescription() { return solutionDescription != null ? solutionDescription : ""; }
    public void setSolutionDescription(String solutionDescription) { this.solutionDescription = solutionDescription; }

    public List<RequestTemplate> getQueue() { return queue; }
    public void setQueue(List<RequestTemplate> queue) { this.queue = queue; }
}
