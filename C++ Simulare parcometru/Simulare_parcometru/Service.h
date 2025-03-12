//
// Created by Asus on 5/11/2024.
//

#ifndef SIMULARE_PARCOMETRU_SERVICE_H
#define SIMULARE_PARCOMETRU_SERVICE_H

#include "Repository.h"
#include "Parcare.h"

class Service{
private:
    Repository elemRepo;
public:
    Service(Repository &repo);
    ~Service();

    void addElem(int pret);
    void removeElem(int cod, int pret);
    void updateElem(int cod, int pret,  Parcare &parcare_noua);
    int findElem(int cod, int pret);
    vector<Parcare> getAll();

    bool operator==(const Service& other) const {
        return elemRepo == other.elemRepo;
    }

};

#endif //SIMULARE_PARCOMETRU_SERVICE_H
