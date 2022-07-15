program  part2
implicit none
!ieee_arithmatic
!M masa molara mol
!R constanta generala a gazelor
!m masa kg
!n nr de moli n=N/NAvogadroa, N/M,V/Vm
!niu nr de moli de gaz (cantitatea de substanta)
! niu=m/miu=N/Na
!N nr de molecule(particule) din acea subst
!---------------------------------
!N-nr de moli
!-----------------------------Declaratii-------------------------------
real:: epsilon					!compresia
integer:: teta					!unghiul RA [1-720]
integer:: i					!element pentru numarare
real:: rad 					!radian
real:: pi=3.1415				!constanta PI
real:: temp_aer				!temperatura aerului
real:: nr_rot 					!numarul de rotatii
real:: D					!diametrul pistonului??????????
real:: s					!suprafata
real:: wpm					!viteza media a pistonului
real:: R_manivela				!raza manivelei
real:: L					!lungimea bielei
real:: lamda					!???????????????????????????
real:: H					!altitudinea de zbor
real:: nr_cil					!numarul de cilindrii
real:: Vs					!volumul camerei de ardere
real:: cil					!cilindreea
real:: v2					!volumul camerei de ardere
real:: sup_p					!aria pistonului
real:: temp_q					!temperatura peretilor chiulasei
real:: temp_p					!temperatura pistonului
real:: temp_cil					!temperatura peretilor cilindrilor
real:: delta_T					!variatia de temperatura
real:: temp_adm					!temperatura cu care se incheie admisia
real:: pres_atm					!presiunea atmosferei
real:: pres_sup		!????????????????	!??pc=pres_h*1.4 presiunea de comprimare
real:: pres_adm					!presiunea cu care se inchide admisia
real:: vol_adm					!volumul la PME (admisie)
real, dimension(1000):: vol_cil(1000)		!volumul cilindrului in fucntie de unghiul RA 
real, dimension(1000):: aria_cil(1000)		!aria cilindrului in functie de unghiul RA

!Parametrii fluidului de lucru:
real, dimension(100):: alfaa(100), alfab(100)
real, dimension(100):: psit(100)
real:: alfa, alfaCA, Nr_moli_O2
real:: nr_moli_N2, nr_moli_am_Lo, Lo		!nr total de moliai amestecului de combustibil
real:: nr_moli_am_L, nr_moli_am		!nr de moli de aer care in mod real exista in amestec

!Gaze de ardere"
real:: nr_moli_CO2 !moliCO2/molicb  
real:: nr_moli_H2O !moliH2O/molicb
real:: masa_molara_aer
real:: masa_molara_cb
real:: masa_molara_o2
real:: masa_molara_n2
real:: nr_moli_Lexc
real:: nr_moli_ga
real:: r_aer
real:: r_o2
real:: r_cb
real:: r_n2

!Masa molara a amestecului proaspat:
real:: masa_molara_am
real:: R_ct_am

!Gaze de ardere
real:: masa_molara_co2
real:: masa_molara_h2o
real:: masa_molara_lexc
real:: r_co2, r_h2o
real:: r_lexc

!Masa molara a gazelor
real:: masa_molara_ga
real:: r_ct_ga

!Masa de amestec proaspat
!real*8, dimension(1,100):: t(100)		!temperatura proba
!integer:: t
!integer, allocatable:: t
integer, dimension(1,100):: t(100)
real:: V1 					!volumul la pme
real:: hq					!hq
real:: etav 					!randamentul V
real:: Masa_mol_am_p			!masa amestecului proaspat
real:: f					!
real:: R_q					!raza chiulasei
real:: S_q					!aria chiulasei
real:: rho_c					!rho c
real*8, dimension(1,100):: cal_1v_aer(100)
real*8, dimension(1,100):: cal_2v_aer(100)
real*8, dimension(1,100):: Cal_v_aer(100)
real*8, dimension(1,100):: cal_sp_v_aer(100)
!real*8, dimension(1,100):: cal_sp_v_cb(100)
real*8:: cal_sp_v_cb
real*8, dimension(1,100):: cal_sp_v_ame(100)
real*8, dimension(1,100):: cal_v_am(100)
real:: m_aer
real:: m_cb

!Comprimarea
real:: nr_comp, ia, as, teta_ia, teta_as
!Evolutia comprimarii
real:: delta_teta
real, dimension(1,100):: teta_c(100)
real,dimension(1,100):: t_comp(100)
real,dimension(1,100):: pres_c(100)!(teta(i),t_comp(i))

!temperatura medie
real::temp_medie_c
real,dimension(1,100):: alfa_1(100)
real,dimension(1,100):: alfa_c(100)
real:: alfa_m

!Caldura transferata prin convectie
real::A_1, aria_cil_m, a, q_r, pres_medie
real, dimension(100):: p_medie(100)

!Temperatura la inchiderea supapei de admisie:
real::vol_ia, k, temp_ia

!Presiunea la inchiderea supapei de admisie
real:: pres_ia 

!Lucrul mecanic
real,dimension(1,100)::luc_mec(100)
real,dimension(1,100):: cal_sp_v_am(100)
real,dimension(1,100):: f_comp(100)
real,dimension(1,100):: put_c(100)

!ARDEREA---------------------------------------------------------------

real:: f_ard, psi_t_ard, p_ci

!Numarul de puncte in care se face arderea
real::na

!Viteza de ardere:"
real:: W_ard

!Distanta pe care trebuie sa o parcurga frontul de ardere
real:: l_ard

!Durata arderii:
real::delta_teta_ard


!---------------------------Comenzi_sistem-----------------------------
call system("clear")
!----------------------------Instructiuni------------------------------
epsilon=6.3
rad=pi/180
temp_aer=258.9 !k
nr_rot=2750 !rot/min
d=0.105 !m
s=0.13 !m
wpm=nr_rot*s/30 !m/s
r_manivela=s/2 !m
lamda=0.25
L=r_manivela/lamda !m
nr_cil=9
vs=(pi*D**2)/4*s
 cil=nr_cil*vs
V2=Vs/(epsilon-1)
sup_p=pi*d**2/4
temp_q=500
temp_cil=500
temp_p=800
delta_t=20
temp_adm=temp_aer+delta_t
pres_atm=0.619*10**5 !PA
pres_sup=pres_atm*1.4
pres_adm=0.93*pres_sup
vol_adm=v2*epsilon

print*, "wpm=", wpm, "cil=", cil, "r_manivela=", r_manivela, "L=", l, "Vs=", vs
print*, "V2=", V2, "sup_p", sup_p, "temp_adm", temp_adm, "pres_sup", pres_sup
print*, "pres_adm", pres_adm, "vol_adm", vol_adm

do teta=1,720
	vol_cil(teta)=v2+(pi*d**2)/4*r_manivela*(1+lamda/2-cos(teta*rad)-lamda/2*cos(teta*rad))
	aria_cil(teta)=pi*D*r_manivela*((1+lamda/2)-cos(teta*rad)-lamda/2*cos(2*teta*rad)) !imprecizie de 0.47%
	print*, "vol_cil", vol_cil(teta), "aria_cil", aria_cil(teta)
end do

Print*, "Parametrii fluidului de lucru:"
Print*, "-consideram combustibilul C8H18:"
Print*, "C8H18 + 12.5O2 = 8CO2+ 9H2O"
print*, "-cantitatea teoretica de aer"

!Construiesc ALphaA si AlphaB
alfaa(1)=0
do i=1,11
	if (i==1) then
		!alfaa(i)=alfaa(0)     
		print *, 'alfaa', alfaa(i)
	else
		alfaa(i)=alfaa(i-1)+0.1
	print *, 'alfaa', alfaa(i) 
    	endif
enddo

alfab(1)=1
do i=1,2
	if (i==1) then     
		!alfab(i)=alfab(0)
		print *, 'alfab', alfab(i)
	else
		alfab(i)=alfab(i-1)+1	
    		print *, "alfab", alfab(i)	       
    	endif
enddo
 
print *, ' '
do i=1,11   
    	psit(i)=1.333*alfaa(i)-0.3333   
    	print *, 'ξ psit=', psit(i) 
enddo
!grafic cu epst(i):alfaA, 1:alfaB

alfa=0.9
Lo=14.4
alfaCA=(1+alfa*Lo-psit(10))/(psit(10)*Lo)
print*, "alfaCA", alfaca
nr_moli_o2=12.5 !moli_O2/moli_cb
nr_moli_N2=(79*nr_moli_o2)/21
print*, "nr_moli_n2", nr_moli_n2

print*, "Numarul total de moli ai amestecului combustibil:"
nr_moli_am_lo=nr_moli_o2+nr_moli_N2
print*, "nr_moli_am_Lo", nr_moli_am_Lo
print*, "Numarul total de moli de aer care in mod real exista in amestec:"
nr_moli_am_L=alfaCA*nr_moli_am_Lo
print*, "nr_moli_am_L", nr_moli_am_L
nr_moli_am=nr_moli_am_L+1
print*, "nr_moli_am", nr_moli_am
print*, "gaza de ardere"
nr_moli_CO2=8 !moliCO2/molicb
nr_moli_H2O=9 !moliH2O/molicb
masa_molara_aer=28.9
masa_molara_cb=114
masa_molara_o2=32
masa_molara_n2=28
nr_moli_Lexc=(alfaCA-1)*nr_moli_am_Lo
nr_moli_ga=nr_moli_co2+nr_moli_H2o+nr_moli_lexc+nr_moli_n2
r_aer=nr_moli_am_l/nr_moli_am
r_o2=nr_moli_o2/nr_moli_am
r_cb=1/nr_moli_am
r_n2=nr_moli_n2/nr_moli_am
print*, "nr_moli_Lexc", nr_moli_Lexc
print*, "nr_moli_ga", nr_moli_ga
print*, "r_aer", r_aer
print*, "r_o2", r_o2 
print*, "r_cb", r_cb
print*, "r_n2", r_n2

print*, "Masa molara a amestecului proaspat:"
masa_molara_am=r_aer*masa_molara_aer+r_cb*masa_molara_cb
R_ct_am=8315/masa_molara_am

print*, "masa_molara_am", masa_molara_am
print*, "R_ct_am", R_ct_am

print*, "Gazele de ardere"
masa_molara_co2=48
masa_molara_h2o=18
masa_molara_lexc=28.9
r_co2=nr_moli_co2/nr_moli_ga
r_h2o=nr_moli_h2o/nr_moli_ga
r_lexc=nr_moli_lexc/nr_moli_ga
r_n2=nr_moli_n2/nr_moli_ga
print*, "r_co2", r_co2
print*, "r_h2o", r_h2o
print*, "r_lexc", r_lexc
print*, "r_n2", r_n2

print*, "Masa molara a gazelor"
masa_molara_ga=r_co2*masa_molara_co2+r_n2*masa_molara_n2+r_h2o*masa_molara_h2o+r_lexc*masa_molara_lexc
print*, "masa_molara_ga", masa_molara_ga
R_ct_ga=8315/masa_molara_ga
print*, "r_ct_ga", r_ct_ga

print*, "Masa de amestec proaspat"
V1=Vol_adm !volumul la pme
rho_c=1.152!reverif
etav=0.995 !reverif
Masa_mol_am_p=V1*rho_c*etaV
print*, 'M_am_p', Masa_mol_am_p

print*, 'Calcul suprafetei chiuloase:'
hq=0.0364
f=v2-(pi*hq)/(6)*(hq*hq+3*d*d/4)
!hq=root(f,hq) !"m"
R_q=(((D*D)/4)+hq*hq)/(2*hq)
S_q=2*pi*R_q*hq
print*, "Suprafata chiulasei", s_q, "Raza chiulasei", r_q
print*, 'Variatiile dalcurilor specifice la volum constant cu temperatura pentru gazele care intra in amestec sunt:'
!#####################################################################################################
t(1)=20
cal_sp_v_cb=alfa*1000

print*, 'Cvaer(t)'
do i=1,60

	if ( i == 1) then
		t(i)=t(1)
	else
		t(i)=t(i-1)+50
		print*, "t", t(i)
	endif

	Cal_1v_aer(i)=20.574+6.226*0.001*t(i)-8.415*0.0000001*t(i)**2 !-3 -7
	print*, "Cal_1v_aer(i)"
	write(*,4) Cal_1v_aer(i)
	cal_2v_aer(i)=25.54+13.942*0.0001*t(i) !-4
	print*, "cal_2v_aer(i)"
	write(*,4) cal_2v_aer(i)
	print*, 'Cal_v_aer(t)'
	if ( t(i) < 1500 ) then
		Cal_v_aer(i)=Cal_1v_aer(i)
		write(*,4) Cal_v_aer(i)
	else
		Cal_v_aer(i)=cal_2v_aer(i)
		write(*,4) Cal_v_aer(i)
	endif
	
!end do

!print*, 'cal_sp_v_aer(i)' !-----------------------------------------------------------------------------------
!do i=1,60 
	cal_sp_v_aer(i)=(Cal_v_aer(i)*1000)/m_aer
	
	!write(*,4) cal_sp_v_aer(i)
!enddo

!do i=1,60
	!cal_sp_v_cb(i)=alfa*1000 
	!print*, 'c_sp_v_cb'
	!write(*,4) cal_sp_v_cb(i)
!enddo

!############################################################################



print*, "----------------------------aici------------------------------------"
!print*, 'cal_sp_v_ame', i
!write(*,4) cal_sp_v_ame(i)
!print*, "----------------------------aici------------------------------------"
r_aer=(r_aer*masa_molara_aer)/masa_molara_am
m_cb=(r_cb*masa_molara_cb)/masa_molara_am
m_aer=(r_aer*masa_molara_aer)/masa_molara_am
print*, "masa_aer", m_aer!, r_aer, masa_molara_aer, masa_molara_am
print*, "masa_cb", m_cb

print*, 'cal_sp_v_ame'
!do i=1,60	
	cal_sp_v_ame(i)=m_aer*cal_sp_v_aer(i)+m_cb*cal_sp_v_cb !(i)
	!print*, 'cal_sp_v_ame', i
	!write(*,4) cal_sp_v_ame(i)
!	print*, 'Cvaer(t)=', Cal_sp_v_aer(i), 'cal_sp_v_aer(i)=', cal_sp_v_aer(i)
!	print*, 'c_sp_v_cb=', cal_sp_v_cb(i), 'cal_sp_v_ame=', cal_sp_v_ame(i)
enddo

do i=1,60
	print*, i, "------"
	print*, 'c_sp_v_cb',  cal_sp_v_cb!(i)
	print*, 'cal_sp_v_ame', cal_sp_v_ame(i)
	print*, "cal_sp_v_aer", cal_sp_v_aer(i)
enddo

!######################################################################################
!call system("clear")
!######################################################################################

print*, "Compresia"
print*, "Numarul de puncte in care se face calculul comprimarii: 30"
nr_comp=30
ia=60
as=30
teta_ia=180+ia
teta_as=360-as
print*, "Evolutia comprimarii se va desfasura la ", teta_ia, " si ", teta_as
delta_teta=(teta_as-teta_ia)/(nr_comp-1)
!do i = 1, 30
!if (i == 1) then
!		teta_c(i)=teta_ia
!	else 
!		teta_c(i)=teta_c(i-1)+delta_teta
!	end if
!	print*, "teta_c(i)", teta_c(i)
!end do


!print*, "T", t
print*, "Presiunea in functie de unghiul RA:"
!pres_c(T,teta)=(Masa_mol_am_p*r_ct_am*T)/vol_cil(teta)
!pres_c(teta_c(i),t_comp(i)
do i=1,30
	if (i == 1) then
	teta_c(i)=teta_ia
	t_comp(i)=290
	else
	teta_c(i)=teta_c(i-1)+delta_teta
	t_comp(i)=t_comp(i-1)+3.68
	endif
	!print*, "teta", teta_c(i), "t_comp", t_comp(i)
	!pres_c(teta_c(i),t_comp(i))=(Masa_mol_am_p*r_ct_am*t_comp(i))/vol_cil(teta_c(i))
	vol_cil(i)=v2+(pi*d**2)/4*r_manivela*(1+lamda/2-cos(teta_c(i)*rad)-lamda/2*cos(teta_c(i)*rad))
	pres_c(i)=(Masa_mol_am_p*r_ct_am*t_comp(i))/vol_cil(i)
	!print*, "teta", teta_c(i), "t_comp", t_comp(i), "pres_c(teta(i),t_comp(i))", pres_c(i)!pres_c(teta_c(i),t_comp(i))
	
	!Temperatura medie
	!alfa(T, teta)=0.99*(1+0.055*wpm)*((p(T,teta_c)*1/10)**2*T)**0.33!1/3
	alfa_c(i)=0.99*(1+0.055*wpm)*((pres_c(i)*1/10)**2*T(i))**0.33!1/3
	!alfa_1(T,teta)=0.0042*n**0.8*(p(T,teta)**0.8)/(vol_cil(teta)**0.1*T**0.57)
	alfa_1(i)=0.0042*nr_rot**0.8*(pres_c(i)**0.8)/(vol_cil(i)**0.1*T(i)**0.57)
	
enddo
print*, "---------------------------------------------"
print*, "teta_comprimare"
write(*,*) teta_c
print*, "---------------------------------------------"
print*, "temperatura_comprimare"
write(*,*) t_comp
print*, "---------------------------------------------"
print*, "pres_c(teta(i),t_comp(i))"
write(*,*) pres_c !pres_c(teta_c(i),t_comp(i))
print*, "---------------------------------------------"

print*, "Temperatura medie"
print*, "---------------------------------------------"
!T_medie(T1,T2)=(T1+T2)/2
temp_medie_c=(t_comp(1)+t_comp(2))/2

print*, "temp_medie_c"
write(*,*)  temp_medie_c

!alfa(T, teta)=0.99*(1+0.055*wpm)*((p(T,teta_c)*1/10)**2*T)**0.33!1/3
!alfa_1(T,teta)=0.0042*n**0.8*(p(T,teta)**0.8)/(vol_cil(teta)**0.1*T**0.57)
!alfa_m(teta1,teta2,T1,T2)=(alfa_1(T1,teta1)+alfa_1(T2,teta2))/2*4.1848*1000/3600

print*, "-----------------------------------------------"
print*, "alfa"
write(*,*) alfa_C
print*, "-----------------------------------------------"
print*, "alfa_1"
write(*,*) alfa_1
print*, "-----------------------------------------------"
alfa_m=(alfa_1(1)+alfa_1(2))/2*4.1848*1000/3600
print*, "alfa_mediu"
write(*,*) alfa_m
print*, "-----------------------------------------------"

print*, "Caldura transferata prin convectie"
!A_1(teta1,teta2,T1,T2)=S_q*(T_medie(T1,T2)-T_q)+S_cm(teta1,teta2)*(t_mediu(T1.T2)-T_cil)
aria_cil(1)=pi*D*r_manivela*((1+lamda/2)-cos(teta_c(1)*rad)-lamda/2*cos(2*teta_c(1)*rad))
aria_cil(2)=pi*D*r_manivela*((1+lamda/2)-cos(teta_c(2)*rad)-lamda/2*cos(2*teta_c(2)*rad))
aria_cil_m=(aria_cil(1)+aria_cil(2))/2
print*, "-----------------------------------------------"
print*, "aria_cilindrului_media-aritmetica"
write(*,*) aria_cil_m
A_1=S_q*(Temp_medie_c-Temp_q)+aria_cil_m*(temp_medie_c-Temp_cil)
print*, "-----------------------------------------------"
print*, "A_1"
write(*,*) A_1
print*, "-----------------------------------------------"
!A(teta1,teta2,T1,T2)=S_q*(T_medie(T1,T2)-T_p)+A1(teta1,teta2,T1,T2)
A=S_q*(Temp_medie_c-Temp_p)+A_1
print*, "Aria"
write(*,*) a
print*, "-----------------------------------------------"
!Q_R(teta1,teta2,T1,T2)=(teta2-teta1)*(1/(6*nr_rot))*alfa_m(teta1,teta2,T1,T2)-A(teta1,teta2,T1,T2)
Q_R=(teta_c(2)-teta_c(1))*(1/(6*nr_rot))*alfa_m-A
print*, "Q_r"
write(*,*) q_r
print*, "-----------------------------------------------"
!pres_medie(teta1,teta2,T1,T2)=(p(T1,teta1)+p(T2,teta2))/2
pres_medie=(pres_c(1)+pres_c(2))/2
print*, "pres_medie"
write(*,*) pres_medie
print*, "-----------------------------------------------"

print*, "Temperatura la inchiderea supapei de admisie:"
print*, "-----------------------------------------------"
!V_ia=v(teta_ia)
vol_ia=v2+(pi*d**2)/4*r_manivela*(1+lamda/2-cos(teta_ia*rad)-lamda/2*cos(teta_ia*rad))
print*, "volumul la inchiderea supapei de admisie"
write(*,*) vol_ia
print*, "-----------------------------------------------"
k=1.4
Temp_ia=Temp_adm*(vol_adm/vol_ia)**(k-1)
print*, "Temperatura la inchiderea supapei de admisie"
write(*,*) temp_ia
print*, "-----------------------------------------------"

print*, "Presiunea la inchiderea supapei de admisie:"
pres_ia=(Masa_mol_am_p*r_ct_am*temp_ia)/vol_ia
write(*,*) pres_ia
print*, "-----------------------------------------------"
!1kcal=4.1868 KJ

print*, "Lucrul mecanica realizat de deplasarea pistonului"
!L(teta1,teta2,T1,T2)=p_m(teta1,teta2,T1,T2)*(v(teta2)-V(teta1))
Luc_mec=pres_medie*(vol_cil(2)-vol_cil(1))
print*, "Lucrul_mecanic"
write(*,*) luc_mec
print*, "-----------------------------------------------"
!cal_sp_v_am(T1,T2)=(cal_sp_v_ame(T1-273.15)+cal_sp_v_ame(T2-273.15))/2
masa_molara_aer=28.9
r_aer=nr_moli_am_l/nr_moli_am
m_aer=(r_aer*masa_molara_aer)/masa_molara_am
do i=1,30

	if (i == 1) then
	teta_c(i)=teta_ia
	t_comp(i)=290-273.15
	else
	teta_c(i)=teta_c(i-1)+delta_teta
	t_comp(i)=t_comp(i-1)+11.142
	endif
	
	!vol_cil
	vol_cil(i)=v2+(pi*d**2)/4*r_manivela*(1+lamda/2-cos(teta_c(i)*rad)-lamda/2*cos(teta_c(i)*rad))
	
	pres_c(i)=(Masa_mol_am_p*r_ct_am*t_comp(i))/vol_cil(i)
	p_medie(i)=(pres_c(i)+pres_c(i+1))/2
	
	!cal sp v aer BEGIN------------------------------------------------------------------
	Cal_1v_aer(i)=20.574+6.226*0.001*t_comp(i)-8.415*0.0000001*t_comp(i)**2 !-3 -7
	!print*, "Cal_1v_aer(i)"
	!write(*,4) Cal_1v_aer(i)
	cal_2v_aer(i)=25.54+13.942*0.0001*t(i) !-4
	!print*, "cal_2v_aer(i)"
	!write(*,4) cal_2v_aer(i)
	!print*, 'Cal_v_aer(t)'
	if ( t_comp(i) < (1500-273) ) then
		Cal_v_aer(i)=Cal_1v_aer(i)
		!write(*,4) Cal_v_aer(i)
	else
		Cal_v_aer(i)=cal_2v_aer(i)
		!write(*,4) Cal_v_aer(i)
	endif
	cal_sp_v_aer(i)=(Cal_v_aer(i)*1000)/m_aer
	
	!Q_r----------------------------------------------------------------------------------
	Q_R=(teta_c(i+1)-teta_c(i))*(1/(6*nr_rot))*alfa_m-A
	
	!Lucrul Mecanic----------------------------------------------------------------------
	Luc_mec(i)=p_medie(i)*(vol_cil(i+1)-vol_cil(i))
	
	
	
	cal_sp_v_ame(i)=m_aer*cal_sp_v_aer(i)+m_cb*cal_sp_v_cb !(i)
	cal_sp_v_am(i)=(cal_sp_v_ame(i)+cal_sp_v_ame(i+1))/2


!F((teta1,teta2,T1,T2))=(T2-T1)+(q_r(teta1,teta2,T1,T2))/(masa_mol_am_p*cal_sp_v_am(T1,T2))+L(teta1,teta2,T1,T2)
	f_comp(i)=(T_comp(i+1)-T_comp(i))+(q_r)/(masa_mol_am_p*cal_sp_v_am(i))+luc_mec(i)

!T(i)=root(F(teta(i-1),tetai,T2),T2)

!P_c(i)=p(T(i),teta(i))
Put_c(i)=pres_c(i)
enddo
print*, m_aer, m_cb, cal_sp_v_cb
do i=1,30
	print*, "cal_sp_v_am(i)", cal_sp_v_am(i), "f_comp(i)", f_comp(i), "cal_sp_v_aer(i)", cal_sp_v_aer(i), "luc_mec(i)", luc_mec(i)
	
enddo
do i=1,30
	print*, "vol_cil(i)", vol_cil(i), "pres_c(i)", pres_c(i), "T(i)", t_comp(i)
enddo

do i=1,30
	print*, "p_medie(i)", p_medie(i)/100000, "bar"
enddo

do i=1,30
	print*, "Put_c(i)", Put_c(i)
enddo

!GRAFIC TITLU EVOLUTIA DE COMPRIMARE, Xlayer PRESIUNE [BAR] p(T(i),teta(i))/10^5, Ylayer VOLUM [m^4] V(teta(i))*1000

print*, "-------------------------------------------------------------"
print*, "ARDEREA"
f_ard=0.04
psi_t_ard=0.845 !j/kg
p_ci=43800000 !kj/kg

print*, "Numarul de puncte in care se face arderea:"
teta_as=330 !RA
Na=30

Print*, "Viteza de ardere:"
W_ard=(0.42*nr_rot)/((alfaca-0.2707)/(0.0143)+(abs(25-as)/2)+(nr_rot-200)/1000-4)
print*, "w_ard", w_ard

print*, "Distanta pe care trebuie sa o parcurga frontul de ardere"
l_ard=(3*D)/4
print*, "l_ard", l_ardx

print*, "Durata arderii:"
delta_teta_ard=l_ard/w_ard*6*nr_rot
print*, "delta_teta_ard", delta_teta_ard

print*, "Unghiul corespunzator sfarsitului arderii:"
teta_sa=teta_as+delta_teta_ard
Delta_teta_ard=(teta_sa-teta_as)/(Na-1)

print*, "Unghiul corespunzator sfarsitului arderii:"
!teta_sa=teta_as+deta_teta_a
!Delta_teta_ard=(teta_sa-teta_as)/(Na-1)
!i=nc+1.nc+na
!teta(i)=teta(i-1)+delta_teta_ard
!F_a(teta)=((teta-teta_as)**2*(3*delta_teta_a-2*teta+2*teta_as))/(delta_teta_a**3)
!f_am(teta1,teta2)=f_a(teta1)-f_a(teta2)
!cal_v_ag(t)=(509.334+261.555*alfa+137.863*10**(-3)*t+262.980*10**(-3)*alfa*t)/(8+114.24*alfa)
!cal_sp_v_ga(t)=cal_v_ga(t)/m_mol_ga*1000 

do i=1,30
	teta_ard(i)=teta_ard(i-1)+delta_teta_ard
	F_a(teta)=((teta_ard(i)-teta_as)**2*(3*delta_teta_a-2*teta+2*teta_as))/(delta_teta_a**3)
	f_am(teta1,teta2)=f_a(teta1)-f_a(teta2)
	cal_v_ag(t)=(509.334+261.555*alfa+137.863*10**(-3)*t+262.980*10**(-3)*alfa*t)/(8+114.24*alfa)
	cal_sp_v_ga(t)=cal_v_ga(t)/m_mol_ga*1000
enddo





!----------------------------------------------------------------------
!2xgrafice + 1x(ciclul real)
!------------------------------Format----------------------------------
4 format(f20.3)
1 format(f20.0)
!-----------------------------Grafice----------------------------------
!-----------------------------Sfarsit----------------------------------
end program part2
!----------------------------Subrutine---------------------------------

!PROGRAM ROOTS_OF_A_FUNCTION
! 
!  IMPLICIT NONE
! 
!  INTEGER, PARAMETER :: dp = SELECTED_REAL_KIND(15)
!  REAL(dp) :: f, e, x, step, value 
!  LOGICAL :: s 
! 
!  f(x) = x*x*x - 3.0_dp*x*x + 2.0_dp*x
! 
!  x = -1.0_dp ; step = 1.0e-6_dp ; e = 1.0e-9_dp
! 
!  s = (f(x) > 0)
!  DO WHILE (x < 3.0)
!    value = f(x)
!    IF(ABS(value) < e) THEN
!      WRITE(*,"(A,F12.9)") "Root found at x =", x
!      s = .NOT. s
!    ELSE IF ((value > 0) .NEQV. s) THEN
!      WRITE(*,"(A,F12.9)") "Root found near x = ", x
!      s = .NOT. s
!    END IF
!    x = x + step
!  END DO
! 
!END PROGRAM ROOTS_OF_A_FUNCTION

!---------------------------------------------------------------------

