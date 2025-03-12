//
// Created by Asus on 5/11/2024.
//

#ifndef SIMULARE_PARCOMETRU_COLECTIE_H
#define SIMULARE_PARCOMETRU_COLECTIE_H

#include "VectorDinamic.h"
#include <iostream>
using namespace std;
#include "Pereche.h"

class Colectie{
private:
    VectorDinamic elements;
public:
    Colectie(VectorDinamic capacity);

    Colectie(const Colectie& other);

    Colectie();

    ~Colectie();

    void setElem(int pozitie, const Pereche &p);

    void setColectie(const VectorDinamic &new_elements);

    void addElem(Pereche pereche);

    bool remove(Pereche elem);

    bool search(Pereche elem);

    int getSize();

    VectorDinamic getElements() const;

    int getFrecventa(Pereche valoare) const;

    Pereche getAt(int position) const;

    int nroccurrences(Pereche elem) const;

    Colectie &operator=(const Colectie& other){
        if(this != &other){
            elements = other.elements;
        }
        return *this;
    }

    bool operator==(const Colectie &other) const{
        if(this->elements.getSize() != other.elements.getSize()){
            return false;
        }
        for(int i = 0; i < this->elements.getSize(); i++){
            Pereche p1 = this->elements.getElem(i);
            Pereche p2 = other.elements.getElem(i);
            if (!(p1 == p2)){
                return false;
            }
        }
        return true;
    }

    friend ostream &operator<<(ostream &output, Colectie &colectie);

};

ostream &operator << (ostream &output, Colectie &colectie);

#endif //SIMULARE_PARCOMETRU_COLECTIE_H
