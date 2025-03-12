//
// Created by Asus on 5/11/2024.
//

#ifndef SIMULARE_PARCOMETRU_TRANZACTIE_H
#define SIMULARE_PARCOMETRU_TRANZACTIE_H

#include "Colectie.h"
#include "Pereche.h"
#include <vector>

class Tranzactie{

private:
    int idTranzactie;
    int suma;
    Colectie detalii_bancnote;

public:

    Tranzactie(int id, int suma, Colectie &bancnote_disponibile)
            : idTranzactie(id), suma(suma), detalii_bancnote() {
        calculeaza_bancnote(bancnote_disponibile);
    }

    ~Tranzactie() = default;

    void calculeaza_bancnote( Colectie& bancnote_disponibile);

    int getId() const;

    int getSuma() const;

    Colectie getDetaliiBancnote() const;

    void setId(int id);

    void setSuma(int suma_noua);

    void setDetaliiBancnote(const Colectie& noi_bancnote);

    void adaugaTranzactieInVector(Tranzactie tranzactie, vector<Tranzactie> &tranzactii);

};

#endif //SIMULARE_PARCOMETRU_TRANZACTIE_H
