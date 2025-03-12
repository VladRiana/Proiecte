//
// Created by Asus on 4/24/2024.
//

#include "Teste.h"
#include "Parcare.h"
#include "Repository.h"
#include "Service.h"
#include "ATM.h"
#include <cassert>
#include <iostream>
using namespace std;

void testParcare(){

    Parcare parcare1;
    assert(parcare1.getCod() == 1);
    assert(parcare1.getPret() == -1);

    Parcare parcare2(5, 100);
    assert(parcare2.getCod() == 5);
    assert(parcare2.getPret() == 100);

    Parcare parcare3(200);
    assert(parcare3.getCod() == 2);
    assert(parcare3.getPret() == 200);

    Parcare parcare4 = parcare2;
    assert(parcare4.getCod() == 5);
    assert(parcare4.getPret() == 100);

    parcare1.setCod(10);
    assert(parcare1.getCod() == 10);
    parcare1.setPret(50);
    assert(parcare1.getPret() == 50);

}

void testRepository(){
    Repository repo;

    Parcare p1(1, 50);
    Parcare p2(2, 100);
    Parcare p3(3, 150);

    repo.addElem(p1);
    repo.addElem(p2);
    assert(repo.getSize() == 2);

    Parcare retrieved_p = repo.getElem(0);
    assert(retrieved_p.getCod() == 1 && retrieved_p.getPret() == 50);

    repo.deleteElem(p1);
    assert(repo.getSize() == 1);

    Parcare new_p(2, 200);
    repo.updateElem(p2, new_p);
    assert(repo.getElem(0).getPret() == 200);

    vector<Parcare> all = repo.getAll();
    assert(all.size() == 1 && all[0].getCod() == 2 && all[0].getPret() == 200);

    Parcare p4(4, 200);
    assert(repo.findElem(p4) == -1);

}

void testService(){
    Repository repo;

    Service service(repo);

    service.addElem(50);
    assert(service.getAll().size() == 1);

    assert(service.findElem(3, 50) == 0);

    Parcare new_p(1, 100);
    service.updateElem(3, 50, new_p);
    assert(service.getAll()[0].getPret() == 100);

    service.removeElem(1, 100);
    assert(service.getAll().empty());

    assert(service.findElem(2, 150) == -1);

}

void testATM() {

    Colectie bancnote;
    bancnote.addElem(Pereche(10, 5));
    bancnote.addElem(Pereche(20, 3));

    Repository repo;
    Service service(repo);
    ATM atm(bancnote, service);

    assert(atm.confirmare_tranzactie(1, 100, 1, 50) == true);

    atm.adauga_bancnote(Pereche(10, 2));
    assert(atm.getBancnoteDisponibile().getSize() == 2);

    atm.elimina_bancnote(Pereche(10, 1), 1);
    assert(atm.getBancnoteDisponibile().getSize() == 2);

}
