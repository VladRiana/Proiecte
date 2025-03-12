//
// Created by Asus on 4/24/2024.
//

#ifndef SIMULARE_PARCOMETRU_PARCARE_H
#define SIMULARE_PARCOMETRU_PARCARE_H

#include <iostream>
#include <string>
#include <cstring>
#include <ostream>
using namespace std;

class Parcare {
private:
    static int next_cod;
    int cod;
    int pret;
public:
    Parcare();
    Parcare(int cod, int pret);
    Parcare(int pret);
    Parcare(const Parcare &other);
    ~Parcare();

    int getCod() const;
    int getPret() const;

    void setCod(int cod);
    void setPret(int pret);

    bool operator == (const Parcare &other) const{
        return cod == other.cod && pret == other.pret;
    }

    Parcare operator = (const Parcare &p){
        cod = p.cod;
        pret = p.pret;

        return *this;
    }

    friend ostream &operator<<(ostream &output, const Parcare &other);

};

ostream &operator<<(ostream &output, Parcare &other);

#endif //SIMULARE_PARCOMETRU_PARCARE_H
