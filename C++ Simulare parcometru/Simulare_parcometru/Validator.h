//
// Created by Asus on 5/23/2024.
//

#ifndef SIMULARE_PARCOMETRU_VALIDATOR_H
#define SIMULARE_PARCOMETRU_VALIDATOR_H

#include "ValidatorException.h"
#include "Service.h"
#include <iostream>
#include <cctype>
#include <set>
#include <limits>

using namespace std;

class Validator{
private:
    Service service;
public:
    static void validateCod(int cod){
        if (!cin) {
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            throw ValidatorException("Codul trebuie sa fie de tip int!");
        }
        if (cod <= 0)
            throw ValidatorException("Codul trebuie sa fie strict mai mare decat 0!");
    }
    static void validatePret(int pret){
        if(!cin){
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            throw ValidatorException("Pretul trebuie sa fie de tip int!");
        }
        if(pret!=6 && pret!=9 && pret!=11)
            throw ValidatorException("Pretul trebuie sa fie 6, 9 sau 11!");
    }
    static void validateSuma(int suma){
        if(!cin){
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            throw ValidatorException("Suma trebuie sa fie de tip int!");
        }
        if(suma <= 0)
            throw ValidatorException("Suma trebuie sa fie strict mai mare decat 0!");
    }
    static void validateBancota(int banc){
        if (!cin) {
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            throw ValidatorException("Banconta trebuie sa fie de tip int!");
        }
        if (banc < 1)
            throw ValidatorException("Bancnota trebuie sa fie mai mare decat 1!");
        set<int> bancnote = {1, 5, 10, 20, 50, 100, 200, 500};
        if (bancnote.find(banc) == bancnote.end()) {
            throw ValidatorException("Bancnota trebuie sa fie una din valorile: 1, 5, 10, 20, 50, 100, 200, 500!");
        }
    }
    static void validateValoare(int val){
        if (!cin) {
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            throw ValidatorException("Valoarea trebuie sa fie de tip int!");
        }
        if (val < 1)
            throw ValidatorException("Valoarea trebuie sa fie mai mare decat 1!");
    }
    Validator(Service &service1) : service(service1){}
};



#endif //SIMULARE_PARCOMETRU_VALIDATOR_H
