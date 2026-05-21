package autototem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class ConfigManager {

    private static final String CONFIG_PATH = "config.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static AppConfig load() {
        File file = new File(CONFIG_PATH);
        if (!file.exists()) {
            return defaultConfig();
        }
        try (Reader reader = new FileReader(file)) {
            AppConfig config = GSON.fromJson(reader, AppConfig.class);
            return config != null ? config : defaultConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return defaultConfig();
        }
    }

    public static void save(AppConfig config) {
        try (Writer writer = new FileWriter(CONFIG_PATH)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static AppConfig defaultConfig() {
        AppConfig config = new AppConfig();
        config.setUsername("joao.mendes@zerohum.com.br");
        config.setPassword("");
        config.getQueue().add(new RequestTemplate(
            "Totem do balcão de informações",
            "6056",
            "SES (HGV) > Impressora e Digitalização > Configura",
            "Rodolfo de Oliveira Barros",
            "1021546",
            "Configuração do totem do balcão de informações.",
            "HGV", "Sala da informática", "845772", 1
        ));
        return config;
    }
}
