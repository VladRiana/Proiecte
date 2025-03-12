package com.example.demo;

import domain.Pacient;
import domain.Programare;
import domain.PacientConverter;
import domain.ProgramareConverter;
import exceptions.RepositoryException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.Repository;
import repository.SQLPacientRepository;
import repository.SQLProgramareRepository;
import repository.BinaryFileRepository;
import repository.TextFileRepository;
import service.PacientService;
import service.ProgramareService;
import ui.ConsoleUI;
import utils.Settings;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        VBox root = fxmlLoader.load();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Cabinet stomatologic");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws RepositoryException {
//        String repoType = Settings.getInstance().getRepoType();
//        Repository<Pacient> pacientRepository = createPacientRepository(repoType);
//        Repository<Programare> programareRepository = createProgramareRepository(repoType);
//
//        PacientService pacientService = new PacientService(pacientRepository);
//        ProgramareService programareService = new ProgramareService(programareRepository);
//
//        ConsoleUI consoleUI = new ConsoleUI(pacientService, programareService);
//        consoleUI.meniu();
        launch();
    }

    private static Repository<Pacient> createPacientRepository(String repoType) throws RepositoryException {
        String pacientFileName = Settings.getInstance().getPacientFileName();
        return switch (repoType) {
            case "memory" -> new Repository<>();
            case "binary" -> new BinaryFileRepository<>(pacientFileName);
            case "text" -> new TextFileRepository<>(pacientFileName, new PacientConverter());
            case "sql" -> new SQLPacientRepository();
            default -> {
                System.out.println("Repository invalid");
                yield null;
            }
        };
    }

    private static Repository<Programare> createProgramareRepository(String repoType) throws RepositoryException {
        String programareFileName = Settings.getInstance().getProgramareFileName();
        return switch (repoType) {
            case "memory" -> new Repository<>();
            case "binary" -> new BinaryFileRepository<>(programareFileName);
            case "text" -> new TextFileRepository<>(programareFileName, new ProgramareConverter());
            case "sql" -> new SQLProgramareRepository();
            default -> {
                System.out.println("Repository invalid");
                yield null;
            }
        };
    }
}