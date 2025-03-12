cmake_minimum_required(VERSION 3.27)
project(Simulare_parcometru)

set(CMAKE_CXX_STANDARD 17)

add_executable(Simulare_parcometru main.cpp
        Parcare.h
        Repository.cpp
        Repository.h
        Teste.cpp
        Teste.h
        ATM.cpp
        ATM.h
        Colectie.cpp
        Colectie.h
        Pereche.h
        Tranzactie.cpp
        Tranzactie.h
        VectorDinamic.cpp
        VectorDinamic.h
        Parcare.cpp
        Service.cpp
        Service.h
        UI.cpp
        UI.h
        FileRepository.cpp
        FileRepository.h
        MyException.h
        Validator.h
        ValidatorException.h)
