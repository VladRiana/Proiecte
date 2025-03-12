//
// Created by Asus on 5/11/2024.
//

#include "Tranzactie.h"
#include "MyException.h"
#include <vector>
#include <algorithm>
#include <stdexcept>
#include <iostream>
using namespace std;

void Tranzactie::calculeaza_bancnote(Colectie& bancnote_disponibile){
    int suma_ramasa = suma;

    for(int i = 0; i < bancnote_disponibile.getSize() && suma_ramasa > 0; i++){
        Pereche bancnota = bancnote_disponibile.getAt(i);

        int valoare = bancnota.element;
        int numar_disponibil = bancnota.frecventa;
        int numar_folosite = min(suma_ramasa / valoare, numar_disponibil);

        if(numar_folosite > 0){
            detalii_bancnote.addElem(Pereche(valoare, numar_folosite));
            suma_ramasa -= numar_folosite * valoare;
        }
    }
    if (suma_ramasa > 0)
        throw MyException("Nu sunt suficiente bancnote pentru a plati suma ceruta.");
}

int Tranzactie::getId() const{
    return idTranzactie;
}

int Tranzactie::getSuma() const{
    return suma;
}

Colectie Tranzactie::getDetaliiBancnote() const{
    return detalii_bancnote;
}

void Tranzactie::setId(int id){
    this->idTranzactie = id;
}

void Tranzactie::setSuma(int suma_noua){
    this->suma = suma_noua;
}

void Tranzactie::setDetaliiBancnote(const Colectie& noi_bancnote){
    this->detalii_bancnote = noi_bancnote;
}

void Tranzactie::adaugaTranzactieInVector(Tranzactie tranzactie, vector<Tranzactie> &tranzactii){
    tranzactii.push_back(tranzactie);
}
