//
// Created by Asus on 5/12/2024.
//

#ifndef SIMULARE_PARCOMETRU_UI_H
#define SIMULARE_PARCOMETRU_UI_H

#include "Service.h"
#include "ATM.h"
#include "Validator.h"

class UI{
private:
    Service service;
    ATM atm;
    Validator validator;
public:
    void addParcare();
    void deleteParcare();
    void updateParcare();
    void findParcare();
    void getAll();

    UI(ATM atm, Repository &repo, Validator validator);
    UI(Service &service, ATM atm, Repository &repo, Validator validator);
    ~UI();

    void showMenu();
    void addMoneda();
    void addCerere();
    void showMonede();
};

#endif //SIMULARE_PARCOMETRU_UI_H
