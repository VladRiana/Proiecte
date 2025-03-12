package ui;

import domain.Pacient;
import domain.Programare;
import exceptions.DuplicateIDException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import service.PacientService;
import service.ProgramareService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner = new Scanner(System.in);
    private PacientService pacientService;
    private ProgramareService programareService;
    public ConsoleUI(PacientService pacientService, ProgramareService programareService) {
        this.pacientService = pacientService;
        this.programareService = programareService;
    }

    public void meniu() throws RepositoryException {
        int optiune;
        do{
            optiune = afiseazaMeniu();
            try {
                switch (optiune) {
                    case 1: addPacient(); break;
                    case 2: addProgramare(); break;
                    case 3: getALLPacienti(); break;
                    case 4: getAllProgramari(); break;
                    case 5: removePacient(); break;
                    case 6: removeProgramare(); break;
                    case 7: updatePacient(); break;
                    case 8: updateProgramare(); break;
                    case 9: getPacientById(); break;
                    case 10: getProgramareById(); break;
                    case 11: raportProgramariPePacient(); break;
                    case 12: raportProgramariPeLuna(); break;
                    case 13: raportZileDeLaUltimaProgramare(); break;
                    case 0: System.out.println("Ati iesit din program!"); break;
                    default:
                        System.out.println("Optiune invalida! Introduceti o noua optiune!");
                        break;
                }
            }
            catch (RepositoryException | DuplicateIDException | ObjectNotFoundException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Alte erori: " + e.getMessage());
            }
        } while(optiune!=0);
    }

    private int afiseazaMeniu(){
        System.out.println("\n--- Meniu ---");
        System.out.println("1. Adauga pacient");
        System.out.println("2. Adauga programare");
        System.out.println("3. Afiseaza toti pacientii");
        System.out.println("4. Afiseaza toate programarile");
        System.out.println("5. Sterge pacient");
        System.out.println("6. Sterge programare");
        System.out.println("7. Modifica pacient");
        System.out.println("8. Modifica programare");
        System.out.println("9. Cauta pacient dupa un ID dat");
        System.out.println("10. Cauta programare dupa un ID dat");
        System.out.println("11. Raport programari pe pacient");
        System.out.println("12. Raport programari pe luna");
        System.out.println("13. Raport zile de la ultima programare");
        System.out.println("0. Iesire");
        System.out.print("Alegeti o optiune: ");
        return scanner.nextInt();
    }

    private void addPacient() throws RepositoryException {
        System.out.println("Id pacient: ");
        int id = scanner.nextInt();
        if (id <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }
        scanner.nextLine();
        System.out.println("Nume pacient: ");
        String nume = scanner.nextLine();
        if (nume.isEmpty()) {
            System.out.println("Numele nu poate fi gol.");
            return;
        }
        System.out.println("Prenume pacient: ");
        String prenume = scanner.nextLine();
        if (prenume.isEmpty()) {
            System.out.println("Prenumele nu poate fi gol.");
            return;
        }
        int varsta = 0;
        System.out.println("Varsta pacient: ");
        try {
            varsta = Integer.parseInt(scanner.nextLine());
            if (varsta <= 0 || varsta > 120) {
                System.out.println("Varsta trebuie sa fie intre 1 și 120.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Varsta trebuie sa fie un numar intreg.");
            return;
        }

        Pacient pacient = new Pacient(id, nume, prenume, varsta);
        pacientService.addPacient(pacient);
        System.out.println("Pacientul: " + pacient + " a fost adaugat cu succes!");
    }

    private void addProgramare() throws RepositoryException {
        System.out.println("Id programare: ");
        int id = scanner.nextInt();
        if (id <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }

        System.out.println("Id pacient: ");
        int idPacient = scanner.nextInt();
        if (idPacient <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }
        scanner.nextLine();
        Pacient pacient = pacientService.getPacientById(idPacient);

        System.out.println("Data programarii (dd-MM-yyyy HH:mm): ");
        String dataProgramarii = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date data = null;
        try {
            data = sdf.parse(dataProgramarii);
        }
        catch (Exception e) {
            System.out.println("Formatul datei este invalid!");
            return;
        }

        System.out.println("Scopul programarii: ");
        String scopulProgramarii = scanner.nextLine();
        if (scopulProgramarii.isEmpty()) {
            System.out.println("Scopul programarii nu poate fi gol.");
            return;
        }

        Programare programare = new Programare(id, pacient, data, scopulProgramarii);
        if(programareService.addProgramare(programare)){
            System.out.println("Programarea: " + programare + " a fost adaugata cu succes!");
        }
        else{
            System.out.println("Programea nu a putut fi adaugata din cauza unei suprapuneri!");
        }
    }

    private void getALLPacienti() {
        System.out.println("Pacientii sunt: ");
        pacientService.getAllPacienti().forEach(System.out::println);
    }

    private void getAllProgramari() {
        System.out.println("Programarile sunt: ");
        programareService.getAllProgramari().forEach(System.out::println);
    }

    private void removePacient() throws RepositoryException {
        System.out.println("Introduceti id-ul pacientului de sters: ");
        int id = scanner.nextInt();
        if (id <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }
        pacientService.removePacient(id);
        System.out.println("Pacientul a fost sters cu succes!");
    }

    private void removeProgramare() throws RepositoryException {
        System.out.println("Introduceti id-ul programarii de sters: ");
        int id = scanner.nextInt();
        if (id <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }
        programareService.removeProgramare(id);
        System.out.println("Programarea a fost stearsa cu succes!");
    }

    private void updatePacient() throws RepositoryException {
        System.out.println("Introduceti id-ul pacientului de modificat: ");
        int id = scanner.nextInt();
        if (id <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }
        scanner.nextLine();
        System.out.println("Nume nou: ");
        String nume = scanner.nextLine();
        if (nume.isEmpty()) {
            System.out.println("Numele nu poate fi gol.");
            return;
        }
        System.out.println("Prenume nou: ");
        String prenume = scanner.nextLine();
        if (prenume.isEmpty()) {
            System.out.println("Prenumele nu poate fi gol.");
            return;
        }
        int varsta = 0;
        System.out.println("Varsta noua: ");
        try {
            varsta = Integer.parseInt(scanner.nextLine());
            if (varsta <= 0 || varsta > 120) {
                System.out.println("Varsta trebuie sa fie intre 1 și 120.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Varsta trebuie sa fie un numar intreg.");
            return;
        }

        Pacient pacientNou = new Pacient(id, nume, prenume, varsta);
        pacientService.updatePacient(pacientNou);
        System.out.println("Pacientul: " + pacientNou + " a fost modificat cu succes!");
    }

    private void updateProgramare() throws RepositoryException {
        System.out.println("Introduceti id-ul programarii de modificat: ");
        int id = scanner.nextInt();
        if (id <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }

        System.out.println("Id pacient: ");
        int idPacient = scanner.nextInt();
        if (idPacient <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }
        scanner.nextLine();
        Pacient pacient = pacientService.getPacientById(idPacient);

        System.out.println("Data noua a programarii (dd-MM-yyyy HH:mm): ");
        String dataProgramarii = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date data = null;
        try {
            data = sdf.parse(dataProgramarii);
        }
        catch (Exception e) {
            System.out.println("Formatul datei este invalid!");
            return;
        }

        System.out.println("Scopul nou al programarii: ");
        String scopulProgramarii = scanner.nextLine();
        if (scopulProgramarii.isEmpty()) {
            System.out.println("Scopul programarii nu poate fi gol.");
            return;
        }

        Programare programareNoua = new Programare(id, pacient, data, scopulProgramarii);
        if(programareService.updateProgramare(programareNoua)){
            System.out.println("Programarea: " + programareNoua + " a fost modificata cu succes!");
        }
        else{
            System.out.println("Programea nu a putut fi modificata din cauza unei suprapuneri!");
        }
    }

    private void getPacientById() throws RepositoryException {
        System.out.println("Introduceti id-ul pacientului de cautat: ");
        int id = scanner.nextInt();
        if (id <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }
        Pacient pacient = pacientService.getPacientById(id);
        System.out.println("Pacientul cautat este: " + pacient);
    }

    private void getProgramareById() throws RepositoryException {
        System.out.println("Introduceti id-ul programarii de cautat: ");
        int id = scanner.nextInt();
        if (id <= 0) {
            System.out.println("Id-ul trebuie sa fie un numar pozitiv.");
            return;
        }
        Programare programare = programareService.getProgramareById(id);
        System.out.println("Programarea cautata este: " + programare);
    }

    private void raportProgramariPePacient() {
        System.out.println("\n--- Raportul programarilor pe pacient ---");
        programareService.raportProgramariPePacient();
    }

    private void raportProgramariPeLuna() {
        System.out.println("\n--- Raportul programarilor pe luni ---");
        programareService.raportProgramariPeLuna();
    }

    private void raportZileDeLaUltimaProgramare() {
        System.out.println("\n--- Raportul zilelor de la ultima programare ---");
        programareService.raportZileDeLaUltimaProgramare();
    }
}
