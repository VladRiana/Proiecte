#include "UI.h"
#include "Service.h"
#include "Parcare.h"
#include "Pereche.h"
#include "Tranzactie.h"
#include "ATM.h"
#include "Colectie.h"
#include "FileRepository.h"
#include "Teste.h"
#include "MyException.h"
#include "ValidatorException.h"
#include "Validator.h"
#include <iostream>
using namespace std;

int main() {
    try{
        testParcare();
        testRepository();
        testService();
        testATM();

        cout << "Toate testele functioneaza cu succes!" << endl << endl << endl;

    }
    catch(MyException &myex){
        cout<<"Erori la efectuarea testelor: "<<myex.getMessage()<<endl;
    }
    catch (ValidatorException &vaex){
        cout << "Erori la efectuarea testelor: " << vaex.getMessage() << endl;
    }
    catch(...){
        cout<<"Alte exceptii... "<<endl;
    }

    try{
        Repository repo;
        FileRepository frepo("parcari.txt");
        Service service(frepo);
        Validator validator(service);
        UI ui(service, ATM(Colectie(), service), frepo, validator);

        Colectie bancnote_initiale;

        ATM atm(bancnote_initiale, service);

        ui.showMenu();
    }
    catch (ValidatorException &vaex){
        cout << "Erori la validarea datelor de intrare: " << vaex.getMessage() << endl;
    }
    catch(MyException &ex){
        cout<<"!!!Atentie: "<<ex.getMessage()<<endl;
    }
    catch(...){
        cout<<"Alte probleme... "<<endl;
    }
    return 0;
}
