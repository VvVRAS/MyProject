#include <iostream>
#include "angajat.h"
#include <string.h>

using namespace std;

Angajat::Angajat() {
	strcpy(nm, "nume");
	strcpy(prn, "prenume");
	strcpy(adrs, "adresa");
	strcpy(dep, "departament");
	strcpy(fct, "functie");
	slr = 0.0;
	con = 0;
	vrt = 0;
}

Angajat::Angajat(char *nm, char *prn, int vrt, char *adrs, char *dep, char *fct, double slr, int con) 
{
 	strcpy(this->nm,nm);
 	strcpy(this->prn,prn);
	strcpy(this->adrs,adrs);
	strcpy(this->dep,dep);
	strcpy(this->fct,fct);
    	this->vrt = vrt;
    	this->slr = slr;
    	this->con = con;
}

void Angajat::afisare() {
	//cout << this->nm << " " << this->prn << " " << this->slr << " " << this->con << endl;
}

void Angajat::setnm(char *n) {
	//this->nm = new char[strlen(n) + 1];
	strcpy(this->nm, n);
}

void Angajat::setprn(char *p) {
	//this->prn = new char[strlen(p) + 1];
	strcpy(this->prn, p);
}

void Angajat::setvrt(int v) {
	this->vrt = v;
}

void Angajat::setadrs(char *a) {
	strcpy(this->adrs, a);
}

void Angajat::setdep(char *d) {
	strcpy(this->dep, d);
}

void Angajat::setfct(char *f) {
	strcpy(this->fct, f);
}
	
void Angajat::setslr(double s) {
	this->slr = s;
}

void Angajat::setcon(int c) {
	this->con = c;
}

void Angajat::sterge(int i) {

}

char* Angajat::getnm() {
	return this->nm;
}

char* Angajat::getprn() {
	return this->prn;
}

double Angajat::getslr() {
	return this->slr;
}

int Angajat::getcon() {
	return this->con;
}

int Angajat::getvrt() {
	return this->vrt;
}

char* Angajat::getadrs() {
	return this->adrs;
}

char* Angajat::getdep() {
	return this->dep;
}

char* Angajat::getfct() {
	return this->fct;
}
Angajat::~Angajat() {
}

