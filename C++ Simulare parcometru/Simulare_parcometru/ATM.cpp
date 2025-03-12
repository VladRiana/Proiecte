//
// Created by Asus on 5/11/2024.
//

#include "ATM.h"
#include "Tranzactie.h"
#include "Service.h"
#include "MyException.h"

ATM::ATM(const Colectie& bancnote, const Service &service) : service(service){
    bancnote_disponibile = bancnote;
}

ATM::ATM(const ATM &other, const Service &service) : service(service){
    bancnote_disponibile = other.bancnote_disponibile;
}

ATM::~ATM() {

}

bool ATM::confirmare_tranzactie(int idTranzactie, int suma, int cod, int pret) {
    if (suma < pret) {
        throw MyException("Suma introdusa este insuficienta pentru a plati parcarea.");
    }

    int suma_ramasa = suma - pret;
    Colectie detalii_bancnote;

    for (int i = 0; i < bancnote_disponibile.getSize() && suma_ramasa > 0; i++) {
        Pereche bancnota = bancnote_disponibile.getAt(i);
        int valoare = bancnota.element;
        int numar_disponibil = bancnota.frecventa;

        int numar_folosite = min(suma_ramasa / valoare, numar_disponibil);

        if (numar_folosite > 0) {
            detalii_bancnote.addElem(Pereche(valoare, numar_folosite));
            suma_ramasa -= numar_folosite * valoare;
        }
    }

    if (suma_ramasa > 0) {
        throw MyException("Nu sunt suficiente bancnote pentru rest.");
    }

    for (int i = 0; i < detalii_bancnote.getSize(); i++) {
        Pereche b = detalii_bancnote.getAt(i);
        for (int j = 0; j < b.frecventa; j++) {
            bancnote_disponibile.remove(b);
        }
    }

    cout << "Restul dat este: " << suma - pret << "\n" << endl;
    cout << "Bancnotele disponibile dupa tranzactie:\n";
    for (int i = 0; i < bancnote_disponibile.getSize(); i++) {
        Pereche p = bancnote_disponibile.getAt(i);
        cout << "Valoare: " << p.element << ", Frecventa: " << p.frecventa << "\n";
    }

    cout << endl << endl;
    return true;
}

void ATM::adauga_bancnote(const Pereche& p){
    bancnote_disponibile.addElem(p);
}

void ATM::elimina_bancnote(Pereche valoare, int numar){
    for(int i = 0; i < numar; i++){
        bancnote_disponibile.remove(valoare);
    }
}

Colectie ATM::getBancnoteDisponibile() const {
    return bancnote_disponibile;
}

void ATM::setBancnoteDisponibile(const Colectie& noi_bancnote){
    bancnote_disponibile = noi_bancnote;
}

vector<Tranzactie> ATM::getTranzactii() const{
    return tranzactii;
}
