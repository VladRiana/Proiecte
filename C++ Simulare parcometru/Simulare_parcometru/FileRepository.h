//
// Created by Asus on 5/12/2024.
//

#ifndef SIMULARE_PARCOMETRU_FILEREPOSITORY_H
#define SIMULARE_PARCOMETRU_FILEREPOSITORY_H

#include "Repository.h"
#include "Parcare.h"
#include <fstream>
using namespace std;

class FileRepository : public Repository{
private:
    char *fileName;
public:
    FileRepository(const char *file);
    void saveToFile();
    ~FileRepository();
};

#endif //SIMULARE_PARCOMETRU_FILEREPOSITORY_H
