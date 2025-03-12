//
// Created by Asus on 4/24/2024.
//

#ifndef SIMULARE_PARCOMETRU_REPOSITORY_H
#define SIMULARE_PARCOMETRU_REPOSITORY_H

#include <vector>
#include <algorithm>
#include "Parcare.h"

class Repository{
private:
    vector<Parcare> parcari;
public:
    Repository();
    ~Repository();

    Parcare getElem(int pozitie);
    void addElem(Parcare &p);
    void deleteElem(Parcare &p);
    void updateElem(Parcare &p, Parcare &new_p);
    vector<Parcare> getAll();
    int getSize();
    int findElem(Parcare &p);

    bool operator==(const Repository &other) const{
        return parcari == other.parcari;
    }
};

#endif //SIMULARE_PARCOMETRU_REPOSITORY_H
