//
// Created by Asus on 5/12/2024.
//

#include "FileRepository.h"

FileRepository::FileRepository(const char *file) : Repository(){
    fileName = new char[strlen(file) + 1];
    strcpy(fileName, file);

    ifstream f(fileName);
    if(!f.is_open()){
        cerr << "Eroare: Nu s-a putut deschide fisierul: " << fileName << endl;
        delete[] fileName;
        return;
    }

    int cod, pret;
    while(f >> cod >> pret){
        Parcare parcare(cod, pret);
        this -> addElem(parcare);
    }

    f.close();
}

void FileRepository::saveToFile(){
    ofstream fout(fileName);
    if(!fout.is_open()){
        cerr << "Eroare: Nu s-a putut deschide fisierul: " << fileName << endl;
        return;
    }

    for (int i = 0; i < this -> getSize(); i++) {
        fout << getElem(i);
        fout << endl;
    }

    fout.close();
}

FileRepository::~FileRepository(){
    delete[] fileName;
}
