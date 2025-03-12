//
// Created by Asus on 5/11/2024.
//

#include "Service.h"
#include "MyException.h"
using namespace std;

Service::Service(Repository &repo) : elemRepo(repo){

}


Service::~Service(){

}

void Service::addElem(int pret) {
    Parcare parcare(pret);
    elemRepo.addElem(parcare);
}

void Service::removeElem(int cod, int pret){
    Parcare parcare(cod, pret);
    int index = elemRepo.findElem(parcare);
    if(index == -1){
        throw MyException("Nu exista parcarea data");
    }
    elemRepo.deleteElem(parcare);
}


void Service::updateElem(int cod, int pret,  Parcare &parcare_noua){
    Parcare parcare(cod, pret);
    int index = elemRepo.findElem(parcare);
    if(index == -1){
        throw MyException("Nu exista parcarea data");
    }
    elemRepo.updateElem(parcare, parcare_noua);
}


int Service::findElem(int cod, int pret){
    Parcare parcare(cod, pret);
    return elemRepo.findElem(parcare);
}


vector<Parcare> Service::getAll(){
    return elemRepo.getAll();
}

