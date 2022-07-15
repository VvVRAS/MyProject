class Angajat
{
	char nm[10]; 					//nume
	char prn[18];					//prenume
	int vrt;					//varsta
	char adrs[50];					//adresa
	char dep[20];					//departament
	char fct[20];					//functie
	double slr;					//salariu
	int con;					//concediu
	
public:
	Angajat();
	Angajat(char *nm, char *prn, int vrt, char *adrs, char *dep, char *fct, double slr, int con);
	
	char *getnm();
	char *getprn();
	int getvrt();
	char *getadrs();
	char *getdep();
	char *getfct();
	double getslr();
	int getcon();
	void afisare();
	void setnm(char *n);
	void setprn(char *p);
	void setvrt(int v);
	void setadrs(char *a);
	void setdep(char *d);
	void setfct(char *f);
	void setslr(double s);
	void setcon(int c);
	void sterge(int i);

	~Angajat();	
};
