//
// Created by Asus on 5/23/2024.
//

#ifndef SIMULARE_PARCOMETRU_MYEXCEPTION_H
#define SIMULARE_PARCOMETRU_MYEXCEPTION_H

#include <exception>
using namespace std;

class MyException : public exception {
private:
    const char* message;
public:
    MyException(const char* m) : message(m){
    }
    const char* getMessage(){
        return message;
    }
};

#endif //SIMULARE_PARCOMETRU_MYEXCEPTION_H
