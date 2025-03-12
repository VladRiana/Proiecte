//
// Created by Asus on 4/24/2024.
//

#include "Repository.h"
#include "Parcare.h"
#include "MyException.h"

Repository::Repository(){

}

Repository::~Repository(){

}

Parcare Repository::getElem(int pozitie){
    return this->parcari[pozitie];
}

void Repository::addElem(Parcare &p){
    parcari.push_back(p);
}

void Repository::deleteElem(Parcare &p){
    auto it = find(parcari.begin(), parcari.end(), p);
    if (it != parcari.end()) {
        parcari.erase(it);
    }
}

void Repository::updateElem(Parcare &p, Parcare &new_p){
    int index = findElem(p);
    if (index == -1) {
        throw MyException("Elementul nu a fost gasit pentru actualizare.");
    }
    parcari[index] = new_p;
}

vector<Parcare> Repository::getAll(){
    return parcari;
}

int Repository::getSize(){
    return parcari.size();
}

int Repository::findElem(Parcare &p){
    for(int i = 0; i < parcari.size(); i++){
        if((parcari[i]) == p){
            return i;
        }
    }
    return -1;
}