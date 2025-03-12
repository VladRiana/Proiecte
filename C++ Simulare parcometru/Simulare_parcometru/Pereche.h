//
// Created by Asus on 5/11/2024.
//

#ifndef SIMULARE_PARCOMETRU_PERECHE_H
#define SIMULARE_PARCOMETRU_PERECHE_H

struct Pereche{
    int element;
    int frecventa;

    Pereche(){
        this->element = 0;
        this->frecventa = 0;
    }

    Pereche(int elem, int frecventa){
        this->element = elem;
        this->frecventa= frecventa;
    }

    bool operator==(const Pereche& other) const{
        return this->element == other.element && this->frecventa == other.frecventa;
    }

    Pereche& operator=(const Pereche& other){
        if (this != &other) {
            this->element = other.element;
            this->frecventa = other.frecventa;
        }
        return *this;
    }

    bool operator!=(const Pereche& other) const{
        return !(*this == other);
    }

};

#endif //SIMULARE_PARCOMETRU_PERECHE_H
