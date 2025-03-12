//
// Created by Asus on 5/23/2024.
//

#ifndef SIMULARE_PARCOMETRU_VALIDATOREXCEPTION_H
#define SIMULARE_PARCOMETRU_VALIDATOREXCEPTION_H

#include <exception>
#include <iostream>
using namespace std;

class ValidatorException : public exception {
private:
    const char *message;
public:
    explicit ValidatorException(const char *m) : message(m) {}

    const char *what() const noexcept override {
        return message;
    }

    const char *getMessage() const {
        return message;
    }
};

#endif //SIMULARE_PARCOMETRU_VALIDATOREXCEPTION_H
