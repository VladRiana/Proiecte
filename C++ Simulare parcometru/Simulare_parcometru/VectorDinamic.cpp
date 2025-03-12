//
// Created by Asus on 5/11/2024.
//

#include "VectorDinamic.h"
#include <stdexcept>
using namespace std;

VectorDinamic::VectorDinamic(int initial_capacity){
    capacity = initial_capacity;
    size = 0;
    elements = new Pereche[initial_capacity];
}

VectorDinamic::~VectorDinamic() {

}

int VectorDinamic::getSize() const {
    return size;
}

Pereche& VectorDinamic::getElem(int pozitie) const {
    if (pozitie < 0 || pozitie >= size) {
        throw out_of_range("Pozitie invalida!");
    }
    return elements[pozitie];
}



void VectorDinamic::push_back(Pereche element){
    if (size >= capacity)
        resize();
    elements[size++] = element;
}

void VectorDinamic::addElem(int pozitie, Pereche e) {
    if(pozitie < 0 || pozitie > size){
        throw out_of_range("Pozitie invalida!");
    }
    if(size >= capacity){
        resize();
    }
    for(int i = size; i > pozitie; i--){
        elements[i] = elements[i - 1];
    }
    elements[pozitie] = e;
    size++;
}

void VectorDinamic::deleteElem(int pozitie) {
    if(pozitie < 0 || pozitie >= size){
        throw out_of_range("Pozitie invalida!");
    }
    for(int i = pozitie; i < size - 1; i++){
        elements[i] = elements[i + 1];
    }
    size--;
}

void VectorDinamic::updateElem(int pozitie, Pereche new_elem){
    if(pozitie < 0 || pozitie >= size){
        throw out_of_range("Pozitie invalida!");
    }
    elements[pozitie] = new_elem;
}

void VectorDinamic::setElem(int pozitie, Pereche &pereche){
    if(pozitie < 0 || pozitie >= size){
        throw out_of_range("Pozitie invalida!");
    }
    elements[pozitie] = pereche;
}

void VectorDinamic::setSize(int newSize) {
    if(newSize <= capacity){
        size = newSize;
    }
    else{
        throw out_of_range("Noua dimensiune depaseste capacitatea vectorului.");
    }
}

