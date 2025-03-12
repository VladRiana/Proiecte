//
// Created by Asus on 5/11/2024.
//

#ifndef SIMULARE_PARCOMETRU_VECTORDINAMIC_H
#define SIMULARE_PARCOMETRU_VECTORDINAMIC_H

#include "Pereche.h"

class VectorDinamic{
private:
    int capacity;
    int size;
    Pereche *elements;

    void resize(){
        if(size >= capacity){
            capacity *= 2;
            Pereche *new_elements = new Pereche[capacity];

            for(int i = 0; i < size; i++){
                new_elements[i] = elements[i];
            }
            delete [] elements;
            elements = new_elements;
        }
    }
public:
    VectorDinamic(int initial_capacity = 1000);

    ~VectorDinamic();

    int getSize() const;

    Pereche& getElem(int pozitie) const;


    void push_back(Pereche element);

    void addElem(int pozitie, Pereche e);

    void deleteElem(int pozitie);

    void updateElem(int pozitie, Pereche new_elem);

    void setElem(int i, Pereche &pereche);

    void setSize(int newSize);

    VectorDinamic &operator=(const VectorDinamic &other){
        if (this == &other){
            return *this;
        }
        if(elements != nullptr){
            delete [] elements;
        }
        capacity = other.capacity;
        size = other.size;
        elements = new Pereche[capacity];
        for(int i = 0; i < size; ++i){
            elements[i] = other.elements[i];
        }
        return *this;
    }

    bool operator==(const VectorDinamic &other) const {
        if (this->size != other.size) {
            return false;
        }
        for (int i = 0; i < this->size; ++i) {
            if (this->elements[i] != other.elements[i]) {
                return false;
            }
        }
        return true;
    }

};

#endif //SIMULARE_PARCOMETRU_VECTORDINAMIC_H
