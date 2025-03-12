package com.example.demo;

import domain.Pacient;
import domain.PacientConverter;
import domain.Programare;
import domain.ProgramareConverter;
import exceptions.RepositoryException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import repository.*;
import service.PacientService;
import service.ProgramareService;
import utils.Settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class HelloController {

    @FXML
    private TableView<Pacient> pacientTable;
    @FXML
    private TableColumn<Pacient, Integer> idPacientColumn;
    @FXML
    private TableColumn<Pacient, String> numePacientColumn;
    @FXML
    private TableColumn<Pacient, String> prenumePacientColumn;
    @FXML
    private TableColumn<Pacient, Integer> varstaPacientColumn;

    @FXML
    private TableView<Programare> programareTable;
    @FXML
    private TableColumn<Programare, Integer> idProgramareColumn;
    @FXML
    private TableColumn<Programare, String> pacientProgramareColumn;
    @FXML
    private TableColumn<Programare, String> dataProgramareColumn;
    @FXML
    private TableColumn<Programare, String> scopProgramareColumn;

    @FXML
    private TextField idPacientField;
    @FXML
    private TextField numePacientField;
    @FXML
    private TextField prenumePacientField;
    @FXML
    private TextField varstaPacientField;

    @FXML
    private TextField idProgramareField;
    @FXML
    private TextField pacientProgramareField;
    @FXML
    private TextField dataProgramareField;
    @FXML
    private TextField scopProgramareField;

    @FXML
    private ListView<String> raportListView;

    private PacientService pacientService;
    private ProgramareService programareService;

    private ObservableList<Pacient> pacientObservableList;
    private ObservableList<Programare> programareObservableList;
    private ObservableList<String> raportObservableList;


    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public HelloController() {

    }

    @FXML
    public void initialize() throws RepositoryException {
        String repoType = Settings.getInstance().getRepoType();
        String pacientFileName = Settings.getInstance().getPacientFileName();
        String programareFileName = Settings.getInstance().getProgramareFileName();

        Repository<Pacient> pacientRepository;
        Repository<Programare> programareRepository;

        switch (repoType) {
            case "memory":
                pacientRepository = new Repository<>();
                programareRepository = new Repository<>();
                break;
            case "binary":
                pacientRepository = new BinaryFileRepository<>(pacientFileName);
                programareRepository = new BinaryFileRepository<>(programareFileName);
                break;
            case "text":
                PacientConverter pacientConverter = new PacientConverter();
                ProgramareConverter programareConverter = new ProgramareConverter();
                pacientRepository = new TextFileRepository<>(pacientFileName, pacientConverter);
                programareRepository = new TextFileRepository<>(programareFileName, programareConverter);
                break;
            case "sql":
                pacientRepository = new SQLPacientRepository();
                programareRepository = new SQLProgramareRepository();
                break;
            default:
                System.out.println("Repository invalid");
                return;
        }

        this.pacientService = new PacientService(pacientRepository);
        this.programareService = new ProgramareService(programareRepository);

        pacientObservableList = FXCollections.observableArrayList(pacientService.getAllPacienti());
        programareObservableList = FXCollections.observableArrayList(programareService.getAllProgramari());

        raportObservableList = FXCollections.observableArrayList();
        raportListView.setItems(raportObservableList);

        idPacientColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject()
        );
        numePacientColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNume())
        );
        prenumePacientColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPrenume())
        );
        varstaPacientColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getVarsta()).asObject()
        );

        if (!pacientTable.getColumns().contains(idPacientColumn)) {
            pacientTable.getColumns().addAll(idPacientColumn, numePacientColumn, prenumePacientColumn, varstaPacientColumn);
        }

        idProgramareColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject()
        );

        pacientProgramareColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPacient().getId()))
        );

        dataProgramareColumn.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getData();
            String formattedDate = sdf.format(date);
            return new SimpleStringProperty(formattedDate);
        });

        scopProgramareColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getScopul_programarii())
        );

        if (!programareTable.getColumns().contains(idProgramareColumn)) {
            programareTable.getColumns().addAll(idProgramareColumn, pacientProgramareColumn, dataProgramareColumn, scopProgramareColumn);
        }

        pacientTable.setItems(pacientObservableList);
        programareTable.setItems(programareObservableList);

    }

    @FXML
    public void adaugaPacient() {
        try {
            int id = Integer.parseInt(idPacientField.getText());
            String nume = numePacientField.getText();
            String prenume = prenumePacientField.getText();
            int varsta = Integer.parseInt(varstaPacientField.getText());

            try {
                Pacient existingPacient = pacientService.getPacientById(id);
                if (existingPacient != null) {
                    showError("Exista deja un pacient cu acest ID.");
                    return;
                }
            } catch (exceptions.ObjectNotFoundException e) {

            }

            Pacient pacient = new Pacient(id, nume, prenume, varsta);
            pacientService.addPacient(pacient);
            refreshPacientTable();

        } catch (NumberFormatException | RepositoryException e) {
            showError("ID-ul si/sau varsta trebuie sa fie numere intregi!");
        }
    }

    @FXML
    public void stergePacient() throws RepositoryException {
        try {
            int id = Integer.parseInt(idPacientField.getText());

            try {
                Pacient pacient = pacientService.getPacientById(id);
                if (pacient != null) {
                    pacientService.removePacient(id);
                    refreshPacientTable();
                    refreshProgramareTable();
                    return;
                }
            } catch (exceptions.ObjectNotFoundException e) {
                showError("Nu exista niciun pacient cu acest ID.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("ID-ul trebuie sa fie un numar intreg!");
        }
    }

    @FXML
    public void actualizeazaPacient() throws RepositoryException {
        try {
            int id = Integer.parseInt(idPacientField.getText());
            String nume = numePacientField.getText();
            String prenume = prenumePacientField.getText();
            int varsta = Integer.parseInt(varstaPacientField.getText());

            try {
                Pacient pacient = pacientService.getPacientById(id);
                if (pacient != null) {
                    pacient = new Pacient(id, nume, prenume, varsta);
                    pacientService.updatePacient(pacient);
                    refreshPacientTable();
                    refreshProgramareTable();
                    return;
                }
            } catch (exceptions.ObjectNotFoundException e) {
                showError("Nu exista niciun pacient cu acest ID.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("ID-ul si/sau varsta trebuie sa fie numere intregi!");
        }
    }

    @FXML
    public void adaugaProgramare() throws RepositoryException, ParseException {
        try {
            int id = Integer.parseInt(idProgramareField.getText());
            int idPacient = Integer.parseInt(pacientProgramareField.getText());

            try {
                Programare existingProgramare = programareService.getProgramareById(id);
                if (existingProgramare != null) {
                    showError("Exista deja o programare cu acest ID.");
                    return;
                }
            } catch (exceptions.ObjectNotFoundException e) {

            }

            Pacient pacient;
            try {
                pacient = pacientService.getPacientById(idPacient);
            } catch (exceptions.ObjectNotFoundException e) {
                showError(e.getMessage());
                return;
            }

            if (!isValidDateFormat(dataProgramareField.getText(), "dd-MM-yyyy HH:mm")) {
                showError("Data trebuie sa fie in formatul dd-MM-yyyy HH:mm!");
                return;
            }

            Date data = sdf.parse(dataProgramareField.getText());
            String scop = scopProgramareField.getText();

            Programare programare = new Programare(id, pacient, data, scop);
            if(programareService.addProgramare(programare)){
                refreshProgramareTable();
            }
            else{
                showError("Programea nu a putut fi adaugata din cauza unei suprapuneri!");
            }
        } catch (NumberFormatException e) {
            showError("ID-ul pacientului si/sau ID-ul programarii trebuie sa fie numere intregi!");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void stergeProgramare() throws RepositoryException {
        try {
            int id = Integer.parseInt(idProgramareField.getText());

            try {
                Programare programare = programareService.getProgramareById(id);
                if (programare != null) {
                    programareService.removeProgramare(id);
                    refreshProgramareTable();
                    return;
                }
            } catch (exceptions.ObjectNotFoundException e) {
                showError("Nu exista nicio programare cu acest ID.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("ID-ul trebuie sa fie un numar intreg!");
        }
    }

    @FXML
    public void actualizeazaProgramare() throws RepositoryException, ParseException {
        try {
            String idProgramareText = idProgramareField.getText();
            String pacientProgramareText = pacientProgramareField.getText();

            int id = Integer.parseInt(idProgramareText);
            int idPacient = Integer.parseInt(pacientProgramareText);

            Programare programare;
            try {
                programare = programareService.getProgramareById(id);
            } catch (exceptions.ObjectNotFoundException e) {
                showError(e.getMessage());
                return;
            }

            Pacient pacient;
            try {
                pacient = pacientService.getPacientById(idPacient);
            } catch (exceptions.ObjectNotFoundException e) {
                showError(e.getMessage());
                return;
            }

            if (!isValidDateFormat(dataProgramareField.getText(), "dd-MM-yyyy HH:mm")) {
                showError("Data trebuie sa fie in formatul dd-MM-yyyy HH:mm!");
                return;
            }

            Date data = sdf.parse(dataProgramareField.getText());
            String scop = scopProgramareField.getText();

            programare = new Programare(id, pacient, data, scop);
            if(programareService.updateProgramare(programare)){
                refreshProgramareTable();
            }
            else{
                showError("Programea nu a putut fi adaugata din cauza unei suprapuneri!");
            }
        } catch (NumberFormatException e) {
            showError("ID-ul pacientului si/sau ID-ul programarii trebuie sa fie numere intregi!");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void refreshPacientTable() {
        pacientObservableList.setAll(pacientService.getAllPacienti());
        pacientTable.refresh();
    }

    @FXML
    private void refreshProgramareTable() {
        programareObservableList.setAll(programareService.getAllProgramari());
        programareTable.refresh();
    }

    private boolean isValidDateFormat(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @FXML
    public void raportProgramariPePacient() {
        try {
            Map<Pacient, Long> raport = programareService.raportProgramariPePacient();
            raportObservableList.clear();

            raport.entrySet().stream()
                    .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                    .forEach(entry -> raportObservableList.add("Pacient: " + entry.getKey().getNume() + " " + entry.getKey().getPrenume() + ", Programări: " + entry.getValue()));
        } catch (Exception e) {
            showError("A aparut o eroare la generarea raportului: " + e.getMessage());
        }
    }

    @FXML
    public void raportProgramariPeLuna() {
        try {
            Map<Integer, Long> raport = programareService.raportProgramariPeLuna();
            raportObservableList.clear();

            raport.entrySet().stream()
                    .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                    .forEach(entry -> raportObservableList.add("Luna: " + entry.getKey() + ", Programări: " + entry.getValue()));
        } catch (Exception e) {
            showError("A aparut o eroare la generarea raportului: " + e.getMessage());
        }
    }

    @FXML
    public void raportZileDeLaUltimaProgramare() {
        try {
            Map<Pacient, Optional<Programare>> raport = programareService.raportZileDeLaUltimaProgramare();
            raportObservableList.clear();

            raport.entrySet().stream()
                    .map(entry -> {
                        Pacient pacient = entry.getKey();
                        Optional<Programare> ultimaProgramareOpt = entry.getValue();
                        if (ultimaProgramareOpt.isPresent()) {
                            Programare ultimaProgramare = ultimaProgramareOpt.get();
                            long zileDeLaUltimaProgramare = (new Date().getTime() - ultimaProgramare.getData().getTime()) / (1000 * 60 * 60 * 24);
                            return new AbstractMap.SimpleEntry<>(pacient, new AbstractMap.SimpleEntry<>(ultimaProgramare.getData(), zileDeLaUltimaProgramare));
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .sorted((entry1, entry2) -> Long.compare(entry2.getValue().getValue(), entry1.getValue().getValue()))
                    .forEach(entry -> {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        raportObservableList.add("Pacient: " + entry.getKey().getNume() + " " + entry.getKey().getPrenume() +
                                ", Ultima programare: " + sdf.format(entry.getValue().getKey()) + ", Zile trecute: " + entry.getValue().getValue());
                    });
        } catch (Exception e) {
            showError("A aparut o eroare la generarea raportului: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
