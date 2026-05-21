package autototem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private AppConfig config;
    private ObservableList<RequestTemplate> queueItems;
    private ListView<RequestTemplate> queueListView;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextArea causeDescField;
    private TextArea solutionDescField;
    private TextArea logArea;
    private Button executeButton;
    private Button stopButton;
    private ProgressBar progressBar;
    private AutomationRunner currentTask;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        config = ConfigManager.load();
        queueItems = FXCollections.observableArrayList(config.getQueue());

        primaryStage.setTitle("AutoTotem — CESU Automatizador");
        primaryStage.setScene(buildScene(primaryStage));
        primaryStage.setMinWidth(760);
        primaryStage.setMinHeight(580);
        primaryStage.setOnCloseRequest(e -> {
            saveConfig();
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    private Scene buildScene(Stage stage) {
        VBox root = new VBox(14);
        root.setPadding(new Insets(16));
        root.setStyle("-fx-background-color: #1a1a2e;");

        HBox topSection = new HBox(16);
        VBox loginPanel = buildLoginPanel();
        VBox queuePanel = buildQueuePanel(stage);
        HBox.setHgrow(queuePanel, Priority.ALWAYS);
        topSection.getChildren().addAll(loginPanel, queuePanel);

        HBox executeSection = new HBox(12);
        executeSection.setAlignment(Pos.CENTER_LEFT);
        executeSection.setPadding(new Insets(2, 0, 2, 0));

        executeButton = new Button("▶  EXECUTAR FILA");
        executeButton.setStyle(
            "-fx-background-color: #00c853; -fx-text-fill: #000;" +
            "-fx-font-size: 14px; -fx-font-weight: bold;" +
            "-fx-padding: 10 28 10 28; -fx-background-radius: 6; -fx-cursor: hand;");
        executeButton.setOnAction(e -> startAutomation());

        stopButton = new Button("■  PARAR");
        stopButton.setStyle(
            "-fx-background-color: #d50000; -fx-text-fill: white;" +
            "-fx-font-size: 14px; -fx-font-weight: bold;" +
            "-fx-padding: 10 28 10 28; -fx-background-radius: 6; -fx-cursor: hand;");
        stopButton.setDisable(true);
        stopButton.setOnAction(e -> stopAutomation());

        progressBar = new ProgressBar(0);
        progressBar.setPrefHeight(28);
        HBox.setHgrow(progressBar, Priority.ALWAYS);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.setStyle("-fx-accent: #00c853;");

        executeSection.getChildren().addAll(executeButton, stopButton, progressBar);

        VBox logSection = new VBox(6);
        VBox.setVgrow(logSection, Priority.ALWAYS);
        Label logTitle = label("Log de Execução", true);
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(200);
        VBox.setVgrow(logArea, Priority.ALWAYS);
        logArea.setStyle(
            "-fx-control-inner-background: #0d1117;" +
            "-fx-text-fill: #7ee787;" +
            "-fx-font-family: 'Consolas', monospace;" +
            "-fx-font-size: 12px;");
        logSection.getChildren().addAll(logTitle, logArea);

        VBox.setVgrow(logSection, Priority.ALWAYS);
        root.getChildren().addAll(topSection, executeSection, logSection);
        return new Scene(root, 800, 640);
    }

    private VBox buildLoginPanel() {
        VBox panel = new VBox(8);
        panel.setPadding(new Insets(14));
        panel.setPrefWidth(250);
        panel.setMinWidth(200);
        panel.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8;");

        usernameField = styledTextField(config.getUsername());
        passwordField = new PasswordField();
        passwordField.setText(config.getPassword());
        passwordField.setStyle(fieldStyle());

        causeDescField = new TextArea(config.getCauseDescription());
        causeDescField.setPrefRowCount(3);
        causeDescField.setWrapText(true);
        causeDescField.setStyle(
            "-fx-control-inner-background: #0d1117; -fx-text-fill: #c9d1d9;" +
            "-fx-border-color: #2a2a5e; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 11px;");

        solutionDescField = new TextArea(config.getSolutionDescription());
        solutionDescField.setPrefRowCount(3);
        solutionDescField.setWrapText(true);
        solutionDescField.setStyle(
            "-fx-control-inner-background: #0d1117; -fx-text-fill: #c9d1d9;" +
            "-fx-border-color: #2a2a5e; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 11px;");

        panel.getChildren().addAll(
            label("Login CESU", true),
            label("Usuário:", false), usernameField,
            label("Senha:", false), passwordField,
            label("Descrição da Causa:", false), causeDescField,
            label("Descrição da Solução:", false), solutionDescField
        );
        return panel;
    }

    private VBox buildQueuePanel(Stage stage) {
        VBox panel = new VBox(8);
        panel.setPadding(new Insets(14));
        panel.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8;");
        HBox.setHgrow(panel, Priority.ALWAYS);

        queueListView = new ListView<>(queueItems);
        queueListView.setPrefHeight(200);
        VBox.setVgrow(queueListView, Priority.ALWAYS);
        queueListView.setStyle(
            "-fx-control-inner-background: #0d1117;" +
            "-fx-border-color: #2a2a5e; -fx-border-radius: 4;");
        queueListView.setCellFactory(lv -> new ListCell<RequestTemplate>() {
            @Override
            protected void updateItem(RequestTemplate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: #0d1117; -fx-text-fill: #c9d1d9;");
                } else {
                    setText(item.getName() + "   [x" + item.getQuantity() + "]");
                    setStyle("-fx-background-color: #0d1117; -fx-text-fill: #c9d1d9; -fx-padding: 6 8;");
                }
            }
        });

        Button addBtn    = actionButton("+ Adicionar", "#1565c0");
        Button editBtn   = actionButton("✎ Editar",    "#37474f");
        Button removeBtn = actionButton("✕ Remover",   "#b71c1c");
        Button upBtn     = actionButton("▲", "#37474f");
        Button downBtn   = actionButton("▼", "#37474f");

        addBtn.setOnAction(e -> showTemplateDialog(stage, null));
        editBtn.setOnAction(e -> {
            RequestTemplate sel = queueListView.getSelectionModel().getSelectedItem();
            if (sel != null) showTemplateDialog(stage, sel);
        });
        removeBtn.setOnAction(e -> {
            RequestTemplate sel = queueListView.getSelectionModel().getSelectedItem();
            if (sel == null) return;
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Remover \"" + sel.getName() + "\" da fila?",
                ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText(null);
            confirm.setTitle("Confirmar remoção");
            confirm.showAndWait().ifPresent(bt -> {
                if (bt == ButtonType.YES) queueItems.remove(sel);
            });
        });
        upBtn.setOnAction(e -> moveItem(-1));
        downBtn.setOnAction(e -> moveItem(1));

        HBox btns = new HBox(6, addBtn, editBtn, removeBtn, upBtn, downBtn);
        btns.setAlignment(Pos.CENTER_LEFT);

        panel.getChildren().addAll(label("Fila de Chamados", true), queueListView, btns);
        return panel;
    }

    private void moveItem(int direction) {
        int idx = queueListView.getSelectionModel().getSelectedIndex();
        int newIdx = idx + direction;
        if (idx < 0 || newIdx < 0 || newIdx >= queueItems.size()) return;
        RequestTemplate item = queueItems.remove(idx);
        queueItems.add(newIdx, item);
        queueListView.getSelectionModel().select(newIdx);
    }

    private void showTemplateDialog(Stage owner, RequestTemplate existing) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle(existing == null ? "Adicionar Chamado" : "Editar Chamado");
        dialog.setResizable(false);

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: #1a1a2e;");

        String[] fieldLabels = {
            "Nome do chamado:", "ID do serviço:",
            "Texto do serviço na tabela:", "Solicitante:",
            "Tag / Patrimônio:", "Unidade:", "Setor:", "Contato:", "IP do Computador:"
        };
        String[] defaults = {
            "", "6056",
            "SES (HGV) > Impressora e Digitalização > Configura",
            "Rodolfo de Oliveira Barros",
            "", "HGV", "Sala da informática", "845772", ""
        };

        TextField[] fields = new TextField[fieldLabels.length];
        for (int i = 0; i < fieldLabels.length; i++) {
            grid.add(label(fieldLabels[i], false), 0, i);
            fields[i] = styledTextField(existing != null ? getTemplateField(existing, i) : defaults[i]);
            fields[i].setMinWidth(340);
            grid.add(fields[i], 1, i);
        }

        int descRow = fieldLabels.length;
        grid.add(label("Descrição:", false), 0, descRow);
        TextArea descArea = new TextArea(existing != null ? existing.getDescription() : "");
        descArea.setPrefRowCount(3);
        descArea.setWrapText(true);
        descArea.setMinWidth(340);
        descArea.setStyle(
            "-fx-control-inner-background: #0d1117; -fx-text-fill: #c9d1d9;" +
            "-fx-border-color: #2a2a5e; -fx-border-radius: 4; -fx-background-radius: 4;");
        grid.add(descArea, 1, descRow);

        int qtyRow = descRow + 1;
        grid.add(label("Quantidade:", false), 0, qtyRow);
        Spinner<Integer> spinner = new Spinner<>(1, 999, existing != null ? existing.getQuantity() : 1);
        spinner.setEditable(true);
        spinner.setStyle("-fx-background-color: #0d1117;");
        grid.add(spinner, 1, qtyRow);

        int namesRow = qtyRow + 1;
        Label namesLabel = label("Lista de nomes (um por linha):", false);
        grid.add(namesLabel, 0, namesRow);
        TextArea namesArea = new TextArea(
            existing != null ? String.join("\n", existing.getNameList()) : "");
        namesArea.setPrefRowCount(4);
        namesArea.setWrapText(false);
        namesArea.setMinWidth(340);
        namesArea.setStyle(
            "-fx-control-inner-background: #0d1117; -fx-text-fill: #c9d1d9;" +
            "-fx-border-color: #2a2a5e; -fx-border-radius: 4; -fx-background-radius: 4;");
        namesArea.setPromptText("João Silva\nMaria Santos\n...");
        grid.add(namesArea, 1, namesRow);

        // Ao preencher a lista, o spinner de quantidade fica irrelevante
        namesArea.textProperty().addListener((obs, oldV, newV) ->
            spinner.setDisable(!newV.isBlank())
        );
        if (existing != null && !existing.getNameList().isEmpty()) {
            spinner.setDisable(true);
        }

        Label namesHint = label("Se preenchida, cria 1 chamado por nome ignorando a quantidade acima.", false);
        namesHint.setStyle("-fx-text-fill: #556070; -fx-font-size: 11px; -fx-wrap-text: true;");
        grid.add(namesHint, 1, namesRow + 1);

        Button saveBtn   = actionButton("Salvar",    "#1565c0");
        Button cancelBtn = actionButton("Cancelar",  "#37474f");
        cancelBtn.setOnAction(e -> dialog.close());
        saveBtn.setOnAction(e -> {
            if (fields[0].getText().isBlank()) {
                showError(dialog, "O nome do chamado é obrigatório.");
                return;
            }
            RequestTemplate t = existing != null ? existing : new RequestTemplate();
            t.setName(fields[0].getText());
            t.setServiceSearchId(fields[1].getText());
            t.setServiceTableText(fields[2].getText());
            t.setRequester(fields[3].getText());
            t.setTag(fields[4].getText());
            t.setUnity(fields[5].getText());
            t.setSector(fields[6].getText());
            t.setContact(fields[7].getText());
            t.setIp(fields[8].getText());
            t.setDescription(descArea.getText());
            t.setQuantity(spinner.getValue());

            // Salva lista de nomes (filtra linhas em branco)
            List<String> names = namesArea.getText().lines()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(java.util.stream.Collectors.toList());
            t.setNameList(names);

            if (existing == null) queueItems.add(t);
            else queueListView.refresh();
            dialog.close();
        });

        HBox btnRow = new HBox(10, saveBtn, cancelBtn);
        btnRow.setAlignment(Pos.CENTER_RIGHT);
        btnRow.setPadding(new Insets(8, 0, 0, 0));
        grid.add(btnRow, 0, namesRow + 2, 2, 1);

        dialog.setScene(new Scene(grid));
        dialog.show();
    }

    private String getTemplateField(RequestTemplate t, int index) {
        if (index == 0) return t.getName();
        if (index == 1) return t.getServiceSearchId();
        if (index == 2) return t.getServiceTableText();
        if (index == 3) return t.getRequester();
        if (index == 4) return t.getTag();
        if (index == 5) return t.getUnity();
        if (index == 6) return t.getSector();
        if (index == 7) return t.getContact();
        if (index == 8) return t.getIp() != null ? t.getIp() : "";
        return "";
    }

    private void startAutomation() {
        if (queueItems.isEmpty()) {
            showError(null, "A fila está vazia. Adicione pelo menos um chamado.");
            return;
        }
        saveConfig();

        AppConfig runConfig = new AppConfig();
        runConfig.setUsername(usernameField.getText());
        runConfig.setPassword(passwordField.getText());
        runConfig.setCauseDescription(causeDescField.getText());
        runConfig.setSolutionDescription(solutionDescField.getText());
        runConfig.setQueue(new ArrayList<>(queueItems));

        logArea.clear();
        logArea.appendText("Iniciando automação...\n");
        executeButton.setDisable(true);
        stopButton.setDisable(false);
        progressBar.setProgress(0);

        currentTask = new AutomationRunner(runConfig, msg -> {
            logArea.appendText(msg + "\n");
            logArea.setScrollTop(Double.MAX_VALUE);
        });

        currentTask.progressProperty().addListener((obs, oldVal, newVal) ->
            progressBar.setProgress(newVal.doubleValue())
        );
        currentTask.setOnSucceeded(e -> onAutomationDone());
        currentTask.setOnFailed(e -> {
            onAutomationDone();
            Throwable ex = currentTask.getException();
            String msg = ex != null ? ex.getMessage() : "Erro desconhecido";
            logArea.appendText("ERRO: " + msg + "\n");
        });
        currentTask.setOnCancelled(e -> {
            onAutomationDone();
            logArea.appendText("Automação cancelada.\n");
        });

        Thread thread = new Thread(currentTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void onAutomationDone() {
        executeButton.setDisable(false);
        stopButton.setDisable(true);
    }

    private void stopAutomation() {
        if (currentTask != null) {
            currentTask.cancel(true);
            logArea.appendText("Parando automação...\n");
        }
    }

    private void saveConfig() {
        config.setUsername(usernameField.getText());
        config.setPassword(passwordField.getText());
        config.setCauseDescription(causeDescField.getText());
        config.setSolutionDescription(solutionDescField.getText());
        config.setQueue(new ArrayList<>(queueItems));
        ConfigManager.save(config);
    }

    private void showError(Stage owner, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.initOwner(owner);
        alert.setHeaderText(null);
        alert.setTitle("Erro");
        alert.showAndWait();
    }

    private Label label(String text, boolean bold) {
        Label lbl = new Label(text);
        lbl.setStyle(bold
            ? "-fx-text-fill: #58a6ff; -fx-font-size: 13px; -fx-font-weight: bold;"
            : "-fx-text-fill: #8b949e; -fx-font-size: 12px;");
        return lbl;
    }

    private TextField styledTextField(String text) {
        TextField tf = new TextField(text);
        tf.setStyle(fieldStyle());
        return tf;
    }

    private String fieldStyle() {
        return "-fx-background-color: #0d1117; -fx-text-fill: #c9d1d9;" +
               "-fx-border-color: #2a2a5e; -fx-border-radius: 4; -fx-background-radius: 4;";
    }

    private Button actionButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: " + color + "; -fx-text-fill: white;" +
            "-fx-font-weight: bold; -fx-padding: 6 14 6 14;" +
            "-fx-background-radius: 4; -fx-cursor: hand;");
        return btn;
    }
}
