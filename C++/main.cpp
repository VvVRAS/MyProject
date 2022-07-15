#include <iostream>
#include <fstream>
#include "angajat.h"
#include <string>
#include <string.h>

using namespace std;


int main() {
	cout << string(50, '\n');

	Angajat angj[10];
	
	for(int i=0; i<10; i++) {
        	angj[i].afisare();
	}

	fstream fin("Ang.txt", fstream::in | fstream::out);
	//fin.open("Ang.txt", fstream::in | fstream::out);
	/*if (!fin)
	{
		cout << " Fisier inexistent \n Creez baza de date cu angajati.\n";
		fin.open("Ang.txt");
	}*/

	char nume[10];
	char prenume[18];
	int varsta;
	char adresa[99];
	char departament[20];
	char functie[20];
	double salariu=0.0;
	int concediu=0;
    	int selector = 1;
    	int i = 0;
    	int cache=0;
	string line;
	string stergnume;

    	
    	while (fin >> nume >> prenume >> varsta >> adresa >> departament >> functie >> salariu >> concediu) //afisare
    			{
    				
    				angj[i].setnm(nume);
    				angj[i].setprn(prenume);
    				angj[i].setvrt(varsta);
    				angj[i].setadrs(adresa);
    				angj[i].setdep(departament);
    				angj[i].setfct(functie);
        			angj[i].setslr(salariu);
        			angj[i].setcon(concediu);
        			i++;
    			}
    	cache=i;
    	
		cout << "\n---------------------------------------------------------------------\n";
		cout << "1. Adăugare angajat nou\n";
		cout << "2. Ştergere angajat cu toate informaţiile corespunzătoare acestuia.\n";
		cout << "3. Afişare angajaţi\n";
		cout << "4. Afişare angajaţi cu zile de concediu rămase .\n";
		cout << "5. Clear screen\n";
		cout << "0. Iesire\n";
		cout << "---------------------------------------------------------------------\n";
		cout << "$: ";
		cin >> selector;
		cout << "---------------------------------------------------------------------\n";

		switch(selector) {
    		
    		case 1: //ADAUGARE ANGAJAT
    				    			
    			cout<<"Nume: ";
			cin>>nume;
			cout<<"Prenume: ";
			cin>>prenume;
			cout<<"Varsta: ";
			cin>>varsta;
			cout<<"Adresa: ";
			cin>>adresa;
			cout<<"Departament: ";
			cin>>departament;
			cout<<"Functie: ";
			cin>>functie;
			cout<<"Salariu: ";
			cin>>salariu;
			cout<<"Concediu: ";
			cin>>concediu;
    			cout<< "------------------------------------------------------------"<<endl;

    			angj[i].setnm(nume);
    			angj[i].setprn(prenume);
    			angj[i].setvrt(varsta);
    			angj[i].setadrs(adresa);
    			angj[i].setdep(departament);
    			angj[i].setfct(functie);
        		angj[i].setslr(salariu);
        		angj[i].setcon(concediu);

    			for (int j = 0; j<i; j++) {
				fin << "\n" << angj[j].getnm() << " " << angj[j].getprn() << " " << angj[j].getvrt() << " " << angj[j].getadrs() << " " << angj[j].getdep() << " " << angj[j].getfct() << " " << angj[j].getslr() << " " << angj[j].getcon() << endl;
				cout << angj[j].getnm() << " " << angj[j].getprn() << " " << angj[j].getvrt() << " " << angj[j].getadrs() << " " << angj[j].getdep() << " " << angj[j].getfct() << " " << angj[j].getslr() << " " << angj[j].getcon() << endl;
			}
			cout<<nume<<" "<<prenume<<" "<<varsta<<" "<<adresa<<" "<<departament<<" "<<functie<<" "<<salariu<<" "<<concediu<<endl;	
				
    		break;
    		
    		case 2:  	//Sterge nume angajat
    			for (int j = 0; j<i; j++) {
				cout << angj[j].getnm() << " " << angj[j].getprn() << " " << angj[j].getvrt() << " " << angj[j].getadrs() << " " << angj[j].getdep() << " " << angj[j].getfct() << " " << angj[j].getslr() << " " << angj[j].getcon() << endl;
			}
    			cout << "Scrieti numele pe care doriti sa-l stergeti:" << endl;
			getline(cin >> ws, stergnume);
    		break;

    		case 3:  //AFISARE 

       			//cout<<nume<<"\t"<<prenume<<"\t"<<varsta<<"\t"<<adresa<<"\t"<<departament<<"\t"<<functie<<"\t"<<salariu<<"\t"<<concediu<<endl;	

    			for (int j = 0; j<cache; j++) {
    			cout << "---------------------------------------------------------------------\n";
				cout << angj[j].getnm() << "\t" << angj[j].getprn() << "\t" << angj[j].getvrt() << "\t" << angj[j].getadrs() << "\t" << angj[j].getdep() << "\t" << angj[j].getfct() << "\t" << angj[j].getslr() << "\t" << angj[j].getcon() << endl;
			}
    			
    			cout << "---------------------------------------------------------------------\n";
			for (i = 0; i < 10; i++) {
				angj[i].afisare();
			}	

		break;	//afisare---------------------------------------------------
		
		case 4:
			cout<<"Nume\tPrenume\tZile_concediu"<<endl;
			for (int j = 0; j<cache; j++) {
				cout<<angj[j].getnm()<<"\t"<<angj[j].getprn()<<"\t"<<angj[j].getcon()<<endl;
			}
    		break;
    		
		case 5:		//Curatare ecrean
			cout << string(50, '\n');
		break;

		case 0:		//Iesire
			cout << string(50, '\n');
			selector = 99;
			exit;
		break;

		defaul:
			cout << "Obtiune inexistenta!!!";
		break;

	fin.close();
	return 0;
}}
