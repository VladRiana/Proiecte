//
// Created by Asus on 5/11/2024.
//

#include "Colectie.h"
#include "VectorDinamic.h"
#include "Pereche.h"
#include "MyException.h"
#include <stdexcept>
using namespace std;

Colectie::Colectie(VectorDinamic capacity){
    this->elements = capacity;
}

Colectie::Colectie(const Colectie &other){
    this->elements = other.elements;
}

Colectie::Colectie() {

}

Colectie::~Colectie(){

}

void Colectie::setElem(int pozitie, const Pereche &p){
    if(pozitie < 0 || pozitie >= elements.getSize()){
        throw MyException("Pozitie invalida");
    }
    elements.getElem(pozitie) = p;
}

void Colectie::setColectie(const VectorDinamic &new_elements){
    this->elements = new_elements;
}

void Colectie::addElem(Pereche pereche) {
    for(int i = 0; i < elements.getSize(); i++){
        Pereche& current = elements.getElem(i);
        if (current.element == pereche.element) {
            current.frecventa += pereche.frecventa;
            return;
        }
    }
    elements.push_back(pereche);
}

bool Colectie::remove(Pereche elem){
    int index = -1;
    for(int i = 0; i < elements.getSize(); i++){
        if(elements.getElem(i).element == elem.element){
            index = i;
            break;
        }
    }
    if(index == -1){
        return false;
    }
    if(elements.getElem(index).frecventa > 1){
        elements.getElem(index).frecventa--;
    }
    else{
        for(int i = index; i < elements.getSize() - 1; ++i){
            elements.setElem(i, elements.getElem(i + 1));
        }
        elements.setSize(elements.getSize() - 1);
    }
    return true;
}

bool Colectie::search(Pereche elem){
    for(int i = 0; i < elements.getSize(); i++){
        if (elements.getElem(i).element == elem.element) {
            return true;
        }
    }
    return false;
}

int Colectie::getSize() {
    return elements.getSize();
};

VectorDinamic Colectie::getElements() const{
    return elements;
}

int Colectie::getFrecventa(Pereche valoare) const{
    for(int i = 0; i < elements.getSize(); i++){
        if(elements.getElem(i) == valoare){
            return elements.getElem(i).frecventa;
        }
    }
    return 0;
}

Pereche Colectie::getAt(int position) const{
    if(position < 0 || position >= elements.getSize()){
        throw MyException("Pozitie invalida");
    }
    return elements.getElem(position);
}

int Colectie::nroccurrences(Pereche elem) const{
    for(int i = 0; i < elements.getSize(); i++){
        if(elements.getElem(i) == elem){
            return elements.getElem(i).frecventa;
        }
    }
    return 0;
}

ostream &operator<<(ostream &output, Colectie &colectie){
    output << "Colectie: ";
    for(int i = 0; i < colectie.getSize(); ++i){
        Pereche p = colectie.getAt(i);
        output << "(" << p.element << ", " << p.frecventa << ") ";
    }
    output << "\n";
    return output;
};

