//
// Created by Asus on 5/11/2024.
//

#ifndef SIMULARE_PARCOMETRU_ATM_H
#define SIMULARE_PARCOMETRU_ATM_H

#include "Colectie.h"
#include "Tranzactie.h"
#include "Service.h"
#include <vector>
#include <iostream>
using namespace std;

class ATM{

private:
    Colectie bancnote_disponibile;
    Colectie detaliiBancnote;
    vector<Tranzactie>tranzactii;
    Service service;

public:

    ATM(const Colectie &bancnote, const Service &service);

    ATM(const ATM &other, const Service &service);

    ~ATM();

    bool confirmare_tranzactie(int idTranzactie, int suma, int cod, int pret);

    void adauga_bancnote(const Pereche& p);

    void elimina_bancnote(Pereche valoare, int numar);

    Colectie getBancnoteDisponibile() const;

    void setBancnoteDisponibile(const Colectie& noi_bancnote);

    vector<Tranzactie> getTranzactii() const;

    ATM& operator=(const ATM& other){
        if(this != &other){
            bancnote_disponibile = other.bancnote_disponibile;
            service = other.service;
        }
        return *this;
    }

    bool operator==(const ATM& other) const{
        return bancnote_disponibile == other.bancnote_disponibile && service == other.service;
    }

};

#endif //SIMULARE_PARCOMETRU_ATM_H
