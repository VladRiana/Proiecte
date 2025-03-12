package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private String repoType;
    private String pacientFileName;
    private String programareFileName;

    private Settings() { }

    public String getRepoType() {
        return repoType;
    }

    public String getPacientFileName() {
        return pacientFileName;
    }

    public String getProgramareFileName() {
        return programareFileName;
    }

    private static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            Properties settings = new Properties();
            try {
                settings.load(new FileReader("C:\\Users\\Asus\\IdeaProjects\\demo\\settings.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Error loading settings file.", e);
            }
            instance = new Settings();
            instance.repoType = settings.getProperty("repo_type");
            instance.pacientFileName = settings.getProperty("pacient_file");
            instance.programareFileName = settings.getProperty("programare_file");
        }
        if (instance.repoType == null || instance.repoType.isEmpty()) {
            throw new RuntimeException("Repository type is not set in settings.properties!");
        }
        if (instance.pacientFileName == null || instance.pacientFileName.isEmpty()) {
            throw new RuntimeException("Pacient file name is not set in settings.properties!");
        }
        if (instance.programareFileName == null || instance.programareFileName.isEmpty()) {
            throw new RuntimeException("Programare file name is not set in settings.properties!");
        }
        return instance;
    }
}
