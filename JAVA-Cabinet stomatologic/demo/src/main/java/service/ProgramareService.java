package service;

import domain.Pacient;
import domain.Programare;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import repository.Repository;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ProgramareService {
    private Repository<Programare> programareRepository;

    public ProgramareService(Repository<Programare> programareRepository) {
        this.programareRepository = programareRepository;
    }

    public boolean seSuprapune(Programare programareNoua) {
        Collection<Programare> programari = programareRepository.getAll();

        long durata = 60*60*1000;   // 60 minute in milisecunde
        long inceputProgramareNoua = programareNoua.getData().getTime();
        long sfarsitProgramareNoua = inceputProgramareNoua + durata;

        for(Programare programare : programari) {
            long inceputProgramare = programare.getData().getTime();
            long sfarsitProgramare = inceputProgramare + durata;

            if( (inceputProgramareNoua < sfarsitProgramare) && (inceputProgramare < sfarsitProgramareNoua) ) {
                return true;
            }
        }
        return false;
    }

    public boolean addProgramare(Programare programare) throws RepositoryException {
        if(!seSuprapune(programare)) {
            programareRepository.add(programare);
            return true;
        }
        return false;
    }

    public void removeProgramare(int id) throws RepositoryException {
        programareRepository.remove(id);
    }

    public boolean updateProgramare(Programare programare) throws RepositoryException {
        if(!seSuprapune(programare)) {
            programareRepository.update(programare);
            return true;
        }
        return false;
    }

    public Programare getProgramareById(int id) throws RepositoryException {
        Programare programare = programareRepository.findById(id);
        if(programare == null) {
            throw new ObjectNotFoundException("Nu exista nicio programare cu acest id!");
        }
        return programare;
    }

    public Collection<Programare> getAllProgramari() {
        Collection<Programare> programari = programareRepository.getAll();
        if(programari.isEmpty()) {
            System.out.println("Nu exista nicio programare!");
        }
        return programari;
    }

    public Map<Pacient, Long> raportProgramariPePacient() {
        Map<Pacient, Long> numarProgramariPePacient = programareRepository.getAll().stream()
                .collect(Collectors.groupingBy(Programare::getPacient, Collectors.counting()));

        numarProgramariPePacient.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> System.out.println("Pacient: " + entry.getKey().getNume() + " " + entry.getKey().getPrenume() + ", Programari: " + entry.getValue()));
        return numarProgramariPePacient;
    }

    public Map<Integer, Long> raportProgramariPeLuna() {
        Map<Integer, Long> programariPeLuna = programareRepository.getAll().stream()
                .collect(Collectors.groupingBy(programare -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(programare.getData());
                    return calendar.get(Calendar.MONTH) + 1;
                }, Collectors.counting()));

        programariPeLuna.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> System.out.println("Luna: " + entry.getKey() + ", Programari: " + entry.getValue()));
        return programariPeLuna;
    }

    public Map<Pacient, Optional<Programare>> raportZileDeLaUltimaProgramare() {
        Map<Pacient, Optional<Programare>> ultimaProgramarePerPacient = programareRepository.getAll().stream()
                .collect(Collectors.groupingBy(Programare::getPacient,
                        Collectors.maxBy(Comparator.comparing(Programare::getData))));

        ultimaProgramarePerPacient.entrySet().stream()
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
                    System.out.println("Pacient: " + entry.getKey().getNume() + " " + entry.getKey().getPrenume() +
                            ", Ultima programare: " + sdf.format(entry.getValue().getKey()) + ", Zile trecute: " + entry.getValue().getValue());
                });
        return ultimaProgramarePerPacient;
    }
}
