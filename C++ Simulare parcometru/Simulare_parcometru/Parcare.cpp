//
// Created by Asus on 5/11/2024.
//

#include "Parcare.h"

int Parcare::next_cod = 1;

Parcare::Parcare(){
    this->cod = next_cod++;
    this->pret = -1;
}

Parcare::Parcare(int cod, int pret){
    this->cod = cod;
    this->pret = pret;
}

Parcare::Parcare(int pret){
    this->cod = next_cod++;
    this->pret = pret;
}

Parcare::Parcare(const Parcare &other){
    this->cod = other.cod;
    this->pret = other.pret;
}

Parcare::~Parcare(){

}

int Parcare::getCod() const{
    return this->cod;
}

int Parcare::getPret() const{
    return this->pret;
}

void Parcare::setCod(int cod){
    this->cod = cod;
}

void Parcare::setPret(int pret){
    this->pret = pret;
}

ostream &operator<<(ostream &output, const Parcare &other) {
    output << other.getCod() << " " << other.getPret();
    return output;
}
