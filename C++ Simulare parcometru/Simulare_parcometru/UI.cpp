//
// Created by Asus on 5/12/2024.
//

#include <limits>
#include "UI.h"
#include "ATM.h"
#include "Parcare.h"
#include "Colectie.h"
#include "Tranzactie.h"
#include "Pereche.h"
#include "MyException.h"
#include "Validator.h"
#include "ValidatorException.h"

UI::UI(ATM atm, Repository &repo, Validator val) : service(repo), atm(atm), validator(service){

}

UI::UI(Service &service, ATM atm, Repository &repo, Validator val) : service(repo), atm(atm), validator(service){

}


UI::~UI(){

}

void UI::addParcare(){
    cout<<"Posibilitatile de parcare sunt pentru: 1 ora - 6\n"
          "                                       2 ore - 9\n"
          "                                       toata ziua - 11\n";

    int pret;
    cout << "Introduceti pretul parcarii: " << endl;
    cin >> pret;
    try{
        validator.validatePret(pret);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    service.addElem(pret);
    cout << "Adaugare cu succes!" << endl;

}

void UI::deleteParcare(){
    int cod;
    cout << "Introduceti codul parcarii de sters: " << endl;
    cin >> cod;
    try{
        validator.validateCod(cod);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    int pret;
    cout << "Introduceti pretul parcarii de sters: " << endl;
    cin >> pret;
    try{
        validator.validatePret(pret);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    try{
        int index = service.findElem(cod, pret);
        if(index != -1){
            service.removeElem(cod, pret);
            cout << "Stergere cu succes!" << endl;
        }
        else{
            cout << "NU s-a putut sterge, pentru ca nu exista!" << endl;
        }
    }
    catch(MyException &myex){
        cout<<myex.getMessage()<<endl;
        return;
    }
}


void UI::updateParcare(){
    int cod;
    cout << "Introduceti codul parcarii de modificat: " << endl;
    cin >> cod;
    try{
        validator.validateCod(cod);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    int pret;
    cout << "Introduceti pretul parcarii de modificat: " << endl;
    cin >> pret;
    try{
        validator.validatePret(pret);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    int codNou;
    cout << "Introduceti noul cod al parcarii: " << endl;
    cin >> codNou;
    try{
        validator.validateCod(codNou);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    cout<<"Posibilitatile de parcare sunt pentru: 1 ora - 6\n"
          "                                       2 ore - 9\n"
          "                                       toata ziua - 11\n";

    int pretNou;
    cout << "Introduceti noul pret al parcarii: " << endl;
    cin >> pretNou;
    try{
        validator.validatePret(pretNou);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    try{
        if(service.findElem(cod, pret) != -1){
            Parcare parcareNoua(codNou, pretNou);
            service.updateElem(cod, pret, parcareNoua);
            cout << "Modificare cu succes!" << endl;
        }
        else
            cout << "NU s-a putut modifica, pentru ca nu exista!" << endl;
    }
    catch(MyException &myex){
        cout<<myex.getMessage()<<endl;
    }
}

void UI::findParcare(){
    int cod;
    cout << "Introduceti codul parcarii pe care doriti sa o cautati: " << endl;
    cin >> cod;
    try{
        validator.validateCod(cod);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    bool ok = false;
    vector<Parcare> parcari = service.getAll();
    for(auto parcare:parcari)
        if(parcare.getCod() == cod){
            ok = true;
            cout << parcare.getCod() << " " << parcare.getPret() << endl;
        }
    if(!ok)
        cout << "Nu exista aceasta parcare!"<< endl;
}

void UI::getAll(){
    vector<Parcare> parcari = service.getAll();
    for(auto parcare:parcari)
        cout << parcare.getCod() << " " << parcare.getPret() << endl;
}

void UI::showMenu() {
    bool ok = true;
    while(ok){
        cout << "-----------Meniu:-----------" << endl;
        cout << "1. Adauga parcare \n"
                "2. Afiseaza parcari \n"
                "3. Sterge parcare \n"
                "4. Modifica parcare \n"
                "5. Gaseste parcarea dupa cod \n"
                "6. Adauga bancnote \n"
                "7. Incearca sa platesti o parcare adaugata\n"
                "8. Afiseaza bancnote \n"
                "0. EXIT \n\n\n";
        int optiune;
        cout << "Alege o optiune: " << endl;
        cin >> optiune;
        if(!cin){
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            cout << "Optiunea trebuie sa fie de tip int!" << endl;
            continue;
        }
        switch(optiune){
            case 1: addParcare(); break;
            case 2: getAll(); break;
            case 3: deleteParcare(); break;
            case 4: updateParcare(); break;
            case 5: findParcare(); break;
            case 6: addMoneda(); break;
            case 7: addCerere(); break;
            case 8: showMonede(); break;
            case 0: ok = false; break;
            default: cout<<"Optiune invalida! Introduceti o optiune valida!"<<endl; break;
        }
    }
}

void UI::addMoneda() {
    int valoare, numar;
    cout << endl;
    cout << "Introduceti valoarea de adaugat: " << endl;
    cin >> valoare;
    try{
        validator.validateBancota(valoare);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    cout << "Introduceti numarul de bancnote de adaugat: " << endl;
    cin >>  numar;
    try{
        validator.validateValoare(numar);
    }
    catch (ValidatorException &vaex){
        cout << vaex.getMessage() << endl;
        return;
    }

    atm.adauga_bancnote(Pereche(valoare, numar));
    cout << "Bancnotele au fost adaugate.\n";
    cout << endl << endl << endl;
}

void UI::addCerere() {
    try {
        int idTranzactie, suma;
        cout << endl;
        cout << "Introduceti ID-ul tranzactiei: " << endl;
        cin >> idTranzactie;
        try {
            validator.validateCod(idTranzactie);
        }
        catch (ValidatorException &vaex) {
            cout << vaex.getMessage() << endl;
            return;
        }

        cout << "Introduceti suma cu care platiti: " << endl;
        cin >> suma;
        try {
            validator.validateSuma(suma);
        }
        catch (ValidatorException &vaex) {
            cout << vaex.getMessage() << endl;
            return;
        }

        int cod;
        cout << "Introduceti codul parcarii de platit: " << endl;
        cin >> cod;
        try {
            validator.validateCod(cod);
        }
        catch (ValidatorException &vaex) {
            cout << vaex.getMessage() << endl;
            return;
        }
        bool ok = false;
        vector<Parcare> parcari = service.getAll();
        for (auto parcare: parcari)
            if (parcare.getCod() == cod) {
                ok = true;
            }
        if (!ok) {
            cout << "Nu exista aceasta parcare!" << endl;
            return;
        }

        int pret;
        cout << "Introduceti pretul parcarii de platit: " << endl;
        cin >> pret;
        try{
            validator.validatePret(pret);
        }
        catch (ValidatorException &vaex){
            cout << vaex.getMessage() << endl;
            return;
        }

        bool confirmat = atm.confirmare_tranzactie(idTranzactie, suma, cod, pret);

        if(confirmat){
            cout << "Tranzactia a fost efectuata.\n";
        }
        else{
            cout << "Tranzactia a fost anulata sau a esuat.\n";
        }
    }
    catch(const invalid_argument &e){
        cout << "Eroare: " << e.what() << "\n";
    }
    cout << endl << endl << endl;
}


void UI::showMonede() {
    Colectie bancnote_disponibile = atm.getBancnoteDisponibile();
    cout << endl;
    cout << "Bancnotele disponibile in bancomat:\n";
    for (int i = 0; i < bancnote_disponibile.getSize(); i++) {
        Pereche p = bancnote_disponibile.getAt(i);
        cout << "Bancnote de " << p.element << " a cate " << p.frecventa << "\n";
    }
    cout << endl << endl << endl;
}



