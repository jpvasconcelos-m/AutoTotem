package autototem;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.List;
import java.util.function.Consumer;

public class AutomationRunner extends Task<Void> {

    private final AppConfig config;
    private final Consumer<String> logger;

    public AutomationRunner(AppConfig config, Consumer<String> logger) {
        this.config = config;
        this.logger = logger;
    }

    @Override
    protected Void call() throws Exception {
        TxtManager tm = new TxtManager();
        tm.overWriteTxt("RequestList.txt", "");

        log("Iniciando browser...");
        Browser.initDriver();

        try {
            WElementFinder wf = new WElementFinder();
            RequestMenu requestMenu = new RequestMenu();
            LoginInserter loginInserter = new LoginInserter();
            RequestCreator requestCreator = new RequestCreator();
            RequestNumberSaver requestNumberSaver = new RequestNumberSaver();
            RequestCatcher requestCatcher = new RequestCatcher();
            RequestFinisher requestFinisher = new RequestFinisher();

            log("Acessando CESU...");
            Browser.driver.get("https://legado.cesu.pe.gov.br/citsmart/pages/login/login.load");

            log("Realizando login como " + config.getUsername() + "...");
            loginInserter.insertLogin(config.getUsername(), config.getPassword());

            List<RequestTemplate> queue = config.getQueue();
            int total = queue.stream().mapToInt(t ->
                !t.getNameList().isEmpty() ? t.getNameList().size() : t.getQuantity()
            ).sum();
            int current = 0;

            for (RequestTemplate template : queue) {
                List<String> names = template.getNameList();
                if (!names.isEmpty()) {
                    // Modo lista: um chamado por nome, substituindo $nome nos campos
                    for (String nome : names) {
                        if (isCancelled()) {
                            log("Execução cancelada pelo usuário.");
                            return null;
                        }
                        current++;
                        RequestTemplate resolved = applySubstitution(template, nome);
                        log("[" + current + "/" + total + "] Criando: " + template.getName() + " → " + nome);
                        wf.hold();
                        requestMenu.reach();
                        wf.hold();
                        requestCreator.createRequest(resolved);
                        requestNumberSaver.SaveRequestNumbers();
                        wf.hold();
                        updateProgress(current, total);
                    }
                } else {
                    // Modo quantidade normal
                    for (int q = 0; q < template.getQuantity(); q++) {
                        if (isCancelled()) {
                            log("Execução cancelada pelo usuário.");
                            return null;
                        }
                        current++;
                        log("[" + current + "/" + total + "] Criando: " + template.getName());
                        wf.hold();
                        requestMenu.reach();
                        wf.hold();
                        requestCreator.createRequest(template);
                        requestNumberSaver.SaveRequestNumbers();
                        wf.hold();
                        updateProgress(current, total);
                    }
                }
            }

            log("Vinculando chamados...");
            requestCatcher.Catch();
            wf.hold();

            log("Finalizando chamados...");
            requestFinisher.Finish(config.getCauseDescription(), config.getSolutionDescription());

            log("Concluido! " + total + " chamado(s) criado(s).");

        } finally {
            if (Browser.driver != null) {
                try { Browser.driver.quit(); } catch (Exception ignored) {}
                Browser.driver = null;
            }
        }

        return null;
    }

    private void log(String message) {
        if (logger != null) {
            Platform.runLater(() -> logger.accept(message));
        }
    }

    /** Cria uma cópia do template com todas as ocorrências de $nome substituídas pelo valor fornecido. */
    private RequestTemplate applySubstitution(RequestTemplate t, String value) {
        RequestTemplate r = new RequestTemplate();
        r.setName(sub(t.getName(), value));
        r.setServiceSearchId(sub(t.getServiceSearchId(), value));
        r.setServiceTableText(sub(t.getServiceTableText(), value));
        r.setRequester(sub(t.getRequester(), value));
        r.setTag(sub(t.getTag(), value));
        r.setDescription(sub(t.getDescription(), value));
        r.setUnity(sub(t.getUnity(), value));
        r.setSector(sub(t.getSector(), value));
        r.setContact(sub(t.getContact(), value));
        r.setIp(sub(t.getIp(), value));
        r.setQuantity(1);
        return r;
    }

    private String sub(String text, String value) {
        return text == null ? null : text.replace("$nome", value);
    }
}
