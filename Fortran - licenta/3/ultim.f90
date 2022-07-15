!--------------------------------------------------------------------
program ultim
implicit none
!---------------------------Declaratii_variabile---------------------
real:: lamda 	!
real:: R 	!lungiema manivelei
real:: L 	!lungimea bielei
real:: s 	!cursa pistonului
real:: D 	! diametrul pistonului
real:: n 	!numarul de rotatii pe minut
real:: ns 	!numarul de rotati pe secunda
real:: pi=3.142
real:: rad
real:: eta=0.84!Randamentul
!------Miscarea_Pistonului------------
integer:: teta
real, dimension(1:720):: xp_aprox(720), xp_exact(720)
real:: max_xp, min_xp
!------Viteza_pistonului--------------
real:: omega	! viteza unghiulara
real:: min_wp
real:: max_wp
real, dimension(0:720):: wp(720)
!------ACCELERATIA_PISTONULUI---------
real:: min_ap, max_ap, min_ap2, max_ap2, min_ap3, max_ap3
real:: lamda2=0.2, lamda3=0.3
real, dimension(0:720):: ap(720),  ap2(720),  ap3(720)
!------Presiunea_medie_si_cea_utila_in_functie_de_unghiul_de_RAC
real:: mp  	! masa pistonului
real:: mb  	! masa bielei
real:: eps 	! raportul de compresie
real:: ii  	! numarul de cilindrii
real:: vs  	! cilindreea motorului
real:: vc  	! cilindreea totala
real:: v2 	! volumul camerei de arders
real:: v1 	! volumul camerei de ardele la PME
!------Volumul cilindrului in functie de unghiul RAC
real, dimension(0:720):: v(720)
real:: Mamp 	! masa de ameste proaspat  
real:: Ram	! constanta generala a gazelor in amestec
! -----presiunea in functie de unghiul RAC
real, dimension(1:720):: p(720)
real:: t				! temperatura-test
real, dimension(1:100):: temp(100)	! temperatura_fisier
real, dimension(1:100):: thetha(100)	! teta_fisier
integer:: i, j 			! 1-80

!Presiunea atmosferica la altitudinea de zbor de 4 km este:
integer:: H=4 				!altitudinea de zbor
real:: T0=288.15			!temperatura la altitudinea de 0m
real:: p0=101325			!presiunea la altitudinea de 0m
real:: t_mediu
real:: p_mediu

!presiunea de supraalimentare
real:: pi_c
integer, parameter :: n_gauss=3
integer i_gauss,j_gauss

!Rezultatul ecuatiei presiunii in functie de volum, la admisie este:
double precision:: pres_adm(1126)
double precision pres_ev(1126)
double precision p_leg_adm(309)
double precision p_leg_ev(11)
REAL(8):: a111, a112, a113, a121, a122, a123, a131, a132, a133, b11, b12, b13
REAL(8):: a211, a212, a213, a221, a222, a223, a231, a232, a233, b21, b22, b23
REAL(8):: a311, a312, a313, a321, a322, a323, a331, a332, a333, b31, b32, b33
REAL(8):: a411, a412, a413, a421, a422, a423, a431, a432, a433, b41, b42, b43
!real(8):: a1, b1, c1
!real(8):: a2, b2, c2
!real(8):: a3, b3, c3
double precision a1(n_gauss,n_gauss), b1(n_gauss), x1(n_gauss)
double precision a2(n_gauss,n_gauss), b2(n_gauss), x2(n_gauss)
double precision a3(n_gauss,n_gauss), b3(n_gauss), x3(n_gauss)
double precision a4(n_gauss,n_gauss), b4(n_gauss), x4(n_gauss)

real(8)::ae1, ae2, ae3, ae4, be1, be2, be3, be4, ce1, ce2, ce3, ce4
real(8)::ae5, ae6, ae7, be5, be6, be7, ce5, ce6, ce7
real(8):: vx, v_xa, v_xe
!double precision:: p_ev(10000), p_leg_adm(100000), p_leg_ev(100000)
real(8), dimension(1000):: P_a(1000), P_e(1000), P_la(1000), P_le(1000)
real(8):: sum_p_a, sum_P_e, sum_P_la, sum_P_le
real, dimension(1000):: teta_ev, teta_adm, teta_la, teta_le
!Calculul presiunii edii
double precision:: p_cad(1000)
real(8):: p_m_ciclu_principal, p_m_ciclu_secundar, pm, pu
real:: teta_m
double precision:: t_thetha(1000), theta(1000)
real, dimension(1000):: p_teta(1000), f_g(1000), f_ip(1000)
real, dimension(1000):: f_sigma(1000), norm(1000), k(1000)
real, dimension(1000):: z(1000), tg(1000), m(1000)
!------------------------Variabile_pentru_testare--------------------
real:: test, test_i, test_j
!----------------------Sfarsit_declaratii_variabile------------------
!-------------------------PROGRAM_TEST-------------------------------


!-----------------------------Program_start--------------------------
print*, "-------------------------START------------------------------"
lamda=0.25
s=0.13
R=s/2
rad=pi/180
D=0.105
L=R/lamda
n=2750
ns=n/60
print*,"lamda", lamda 
print*, "n=", n 
print*, "R=", R 
print*, "L=", L
!------------------------------------------------
!-----------Miscarea_Pistonului------------------
Print*, "Miscarea Pistonului"
open (1, file="miscarea_pistonului.txt")
do teta=1,720
	xp_exact(teta)=R*(1+1/lamda-(cos(teta*rad)+1/lamda*sqrt(1-lamda**2*sin(teta*rad)**2)))
	xp_aprox(teta)=R*(1-(cos(teta*rad))+lamda/4-lamda/4*(cos(2*teta*rad)))

	if(abs(xp_aprox(teta-1))<abs(xp_aprox(teta))) then
		max_xp=abs(xp_aprox(teta-1))
	end if

	if(xp_aprox(teta-1)>xp_aprox(teta)) then
		min_xp=xp_aprox(teta-1)
	end if
	!print*, "	-----------------------------------------------------------------------------"
	!print*, teta, "| xp_exact(teta) |", xp_exact(teta), "| xp_aprox(teta) |", xp_aprox(teta)
	write(1,*) teta, xp_exact(teta), xp_aprox(teta)
end do
!print*, "	-----------------------------------------------------------------------------"
print*, "min_xp", min_xp, "max_xp", max_xp
 close (1)
!call system('gnuplot -p ./PLOT_SCRIPT/miscarea_pistonului.script')
!call system('clear')
!--------------------------------------------------
!---------------VITEZA_PISTONULUI------------------
print*, "Viteza pistonului"
omega=pi*n/30
open (2, file="viteza_pistonului.txt")
min_wp=0
do teta=1,720
	wp(teta)=r*omega*(sin(teta*rad)+lamda/2*sin(2*teta*rad))
	!print*, "	------------------------------------"
	!print*, teta, "| wp(teta) |", wp(teta)
	write(2,*) teta, wp(teta)

	if(abs(wp(teta-1))<abs(wp(teta))) then
		max_wp=abs(wp(teta-1))
	end if

	if(wp(teta-1)>wp(teta)) then
		min_wp=wp(teta-1)
	end if
end do
!print*, "	------------------------------------"
print*, "min_wp", min_wp, "max_wp", max_wp
 close (2)
!call system('gnuplot -p ./PLOT_SCRIPT/viteza_pistonului.script')
!call system('clear')
!--------------------------------------------------------------------
!-------------------------ACCELERATIA_PISTONULUI---------------------
print*, "Acceleratia pistonului"
open (3, file="acceleratia_pistonului.txt")
do teta=1,720
	ap(teta)=r*omega**2*(cos(teta*rad)+lamda*cos(2*teta*rad))
	ap2(teta)=r*omega**2*(cos(teta*rad)+lamda2*cos(2*teta*rad))
	ap3(teta)=r*omega**2*(cos(teta*rad)+lamda3*cos(2*teta*rad))
	!print*, "	------------------------------------"
	!print*, teta, "| ap(teta) |", ap(teta)
	
	!----ap1
	if(abs(ap(teta-1))<abs(ap(teta))) then
		max_ap=abs(ap(teta-1))
	end if

	if(ap(teta-1)>ap(teta)) then
		min_ap=ap(teta-1)
	end if

	!----ap2
	if(abs(ap2(teta-1))<abs(ap2(teta))) then
		max_ap2=abs(ap2(teta-1))
	end if

	if(ap2(teta-1)>ap2(teta)) then
		min_ap2=ap2(teta-1)
	end if

	!----ap3
	if(abs(ap3(teta-1))<abs(ap3(teta))) then
		max_ap3=abs(ap3(teta-1))
	end if

	if(ap3(teta-1)>ap3(teta)) then
		min_ap3=ap3(teta-1)
	end if

	!-------
	write(3,*) teta, ap(teta), ap2(teta), ap3(teta)
end do
!print*, "	------------------------------------"
print*, "min_ap", min_ap, "max_ap", max_ap
print*, "min_ap2", min_ap2, "max_ap2", max_ap2
print*, "min_ap3", min_ap3, "max_ap3", max_ap3
 close (3)
!call system('gnuplot -p ./PLOT_SCRIPT/acceleratia_pistonului.script')
!call system('clear')
!-------Presiunea medie si cea utila in functie de unghiul RAC-------
mp=1 		!kg masa pistonului
mb=3 		!km masa bielei
ii=9 		!numarul de cilindrii
eps=7.5 	!raport de compresie
mamp=0.0040470554
ram=274.956 !j/(kg*k)
vs=(pi*d**2)/4*s
print*, "vs", vs
vc=ii*vs
print*, "vc", vc
v2=vs/(eps-1)
print*, "v2", v2
v1=eps*V2
print*, "v1", v1

!volumul cilindrului in functie de unghiul RAC
print*, "volumul cilindrului in functie de unghiul RAC"
open (4, file="volumul cilindrului in functie de unghiul rac.txt")
do teta=1,720
	v(teta)=v2+(pi*d**2)/4*R*(1+lamda/4-cos(teta*rad)-lamda/4*cos(2*teta*rad))
	!print*, "	----------------------------------------"
	!clear
	!print*, teta, "| v(teta) |", v(teta)
	write(4,*) v(teta)
end do
!print*, "	----------------------------------------"
 close (4)
 
!---------------presiunea in functie de unghiul RAC-------------------------------------
print*, "presiunea in functie de unghiul RAC"
open (5, file="presiunea in cilindru in functie de unghiul rac.txt")
!-----------------------------------------
t=1
!-----------------------------------------
do teta=1,720
	p(teta)=(mamp*Ram*T)/v(teta)
	!print*, "	----------------------------------------"
	!print*, teta, "| p(teta) |", p(teta)
	write(5,*) p(teta)
end do
!print*, "	----------------------------------------"
 close (5)
!-------------------------------------------------------------
!citestte temperaturi din fisierul temperaturi.txt
open (62, file="temperaturi.txt", status="old")
do i=1,80
	read(62,*) temp(i)
	!write(*,*) temp(i)
end do
 close(62)

!--------------------------------------------------------------
!citeste thetha din fisierul thetha.txt
open (71, file="thetha.txt", status="old")
do i=1,80
	read(71,*) thetha(i)
	!write(*,*) thetha(i)
end do
 close (71)

!--------------------------------------------------------------
!Afiseaza temperaturile si thetha valorile memorate din fisiere
do i=1,80
	!print*, "	----------------------------------------"
	!print*, i, thetha(i), temp(i)
end do
!print*, "	----------------------------------------"

!---------
!recalculeaza p si v
!pentru grafic ciclu real fara admisie si evacuare
open (81, file="ciclul_real_deschis.txt")
do i=1,80 !80
	v(i)=v2+((pi*d**2)/4)*R*(1+(lamda/4)-cos(thetha(i)*(pi/180))-(lamda/4)*cos(2*thetha(i)*(pi/180)))
	p(i)=(mamp*Ram*temp(i))/v(i)
	!print*, "	----------------------------------------------------------"
	!print*, i, "| p(thetha) |", p(i), "| v(thetha) |", v(i)
	write(81,*) p(i), v(i)
end do
!print*, "	----------------------------------------------------------"
!call system('gnuplot -p ./PLOT_SCRIPT/ciclul_real_deschis.script')
!call system('clear')
print*, " "
print*, "Presiunea atmosferica la altitudinea de zbor de 4Km"
t_mediu=t0-6.5*h
print*, "t_mediu", t_mediu
p_mediu=p0*(t_mediu/t0)**5.225
print*, "p_mediu", p_mediu
print*, " "
print*, "Presiunea de supraalimentare"
pi_c=1.51*p_mediu
print*, "pi_c", pi_c
!#############################Matricea-1#############################
 	OPEN(111,FILE='input1.txt')
   	
    	a111=V2**2
	a112=V2
	a113=1
	a121=((V2+V1)/2)**2!((V1+V2)/2)
	a122=(V2+V1)/2
	a123=1
	a131=V1**2!*v1
	a132=V1
	a133=1

    	b11=(pi_c+p_mediu)/2 
	b12=(pi_c/2)+(pi_c+p_mediu)/4 !85470.0
	b13=pi_c

	write(111,*) a111, a112, a113, b11
    	write(111,*) a121, a122, a123, b12
    	write(111,*) a131, a132, a133, b13

  	close (111)
  	
	OPEN(121,FILE='input1.txt')
	do i=1,3
		read(121,*) (a1(i,j),j=1,n_gauss), b1(i)
	end do
	close(121)
	call gauss_1(a1,b1,x1,n_gauss)

! print solutions
	OPEN(122,FILE='output1.txt')
  	write (122,*) (x1(i),i=1,n_gauss)

  	print*, " "
  	print*, "		a1=", x1(1)
  	print*, "Solutiile:", "	b1=", x1(2)
  	print*, "		c1=", x1(3)
	print*, " "
	close (122)

!##################################################################################################
!Print*, "Rezultatul ecuatiei presiunii in functie de volum, la admisie este:"
!pres_adm(Vx)=a1*Vx**2+b1*Vx+c1
	ae1=x1(1)
	be1=x1(2)
	ce1=x1(3)
!#############################Matricea-2#############################
OPEN(211,FILE='input2.txt')
   	
    	a211=V2**2
	a212=V2
	a213=1
	a221=((V1+V2)/2)**2!((V1+V2)/2)
	a222=(V1+V2)/2
	a223=1
	a231=V1**2!*v1
	a232=V1
	a233=1

    	b21=(pi_c+p_mediu)/2 
	b22=p_mediu !85470.0
	b23=pi_c
	
	write(211,*) a211, a212, a213, b21
    	write(211,*) a221, a222, a223, b22
    	write(211,*) a231, a232, a233, b23

  	close (211)
  	
	OPEN(221,FILE='input2.txt')
	do i=1,3
		read(221,*) (a2(i,j),j=1,n_gauss), b2(i)
	end do
	close(221)
	call gauss_2(a2,b2,x2,n_gauss)

! print solutions
	OPEN(222,FILE='output2.txt')
  	write (222,*) (x2(i),i=1,n_gauss)

  	print*, "		a2=", x2(1)
  	print*, "Solutiile:", "	b2=", x2(2)
  	print*, "		c2=", x2(3)
	print*, " "
	close (222)
	
	ae2=x2(1)
	be2=x2(2)
	ce2=x2(3)
!####################################################################
!print*, "rezulta ecuatia presiunii in functie de volum, la evacuare este:"

!vx=v2-v1*100000 ! cerceteaa pentru pasul lui for e de v2 0.000001 v1
!do 
!	p_ev(i)=ae2*vx**2+be2*vx+ce2
!enddo

!########################MATRIX-3#################################### 
 ae3=(p(1)-pi_c)/(v(1)-v1)
 be3=pi_c-v1*ae3
 print*, "Solutiile:	", "ae3=", ae3
 print*, "		be3=", be3
!####################################################################

!#############################Matricea-4#############################
OPEN(231,FILE='input4.txt')
   	
    	a411=v(80)**2
	a412=v(80)
	a413=1
	a421=(0.00334)**2!((V1+V2)/2)
	a422=0.00334
	a423=1
	a431=V1**2!*v1
	a432=V1
	a433=1

    	b41=p(80) 
	b42=(p(80)+pi_c)/1.9 !85470.0
	b43=pi_c

	write(231,*) a411, a412, a413, b41
    	write(231,*) a421, a422, a423, b42
    	write(231,*) a431, a432, a433, b43

  	close (231)
  	
	OPEN(232,FILE='input4.txt')
	do i=1,3
		read(232,*) (a4(i,j),j=1,n_gauss), b4(i)
	end do
	close(232)
	call gauss_4(a4,b4,x4,n_gauss)

! print solutions
	OPEN(233,FILE='output4.txt')
  	write (233,*) (x4(i),i=1,n_gauss)

  	print*, " "
  	print*, "		a4=", x4(1)
  	print*, "Solutiile:", "	b4=", x4(2)
  	print*, "		c4=", x4(3)
	print*, " "
	close (233)
	ae4=x4(1)
	be4=x4(2)
	ce4=x4(3)
	
!####################################################################


!v_xe=v(teta(79)),v(teta(79))+0.00001..V1
!p_leg_ev(v_xe)=a4+v_xe**2+b4*v_xe+c4

OPEN(111,FILE='ciclul_real_inchis.txt')

do i=1,80
	write(111,*) p(i), v(i)
enddo

Print*, "Rezultatul ecuatiei presiunii in functie de volum, la admisie este:"
vx=v2
i=1
do while (vx<=v1)
	pres_adm(i)=ae1*Vx**2+be1*Vx+ce1
	write(111,*) pres_adm(i), vx
!	print*, i, "pres_adm(vx)=", pres_adm(i)
	i=i+1
	vx=vx+0.000001
enddo

print*, "rezulta ecuatia presiunii in functie de volum, la evacuare este:"
vx=v2
i=1
do while (vx<=v1)	
	pres_ev(i)=ae2*vx*vx+be2*vx+ce2
	write(111,*) pres_ev(i), vx
!	print*, i, "pres_ev(vx)=", pres_ev(i)
	i=i+1
	vx=vx+0.000001
enddo

print*, "rezulta ecuatia presiunii in functie de volumul de legatura la admisie este:"
v_xa=v(1)
i=1
do while (v_xa<=v1)
	p_leg_adm(i)=ae3*v_xa+be3
	write(111,*) p_leg_adm(i), v_xa
!	print*, i, "p_leg_adm(V_xa)=", p_leg_adm(i)
	v_xa=v_xa+0.000001
	i=i+1
enddo

print*, "rezulta ecuatia presiunii in functie de volumul de legatura la evacuare este:"
v_xe=v(80)
i=1
do while (v_xe<=v1)
	p_leg_ev(i)=ae4*v_xe**2+be4*v_xe+ce4
!	write(111,*) p_leg_ev(i), v_xe
!	print*, i, "p_leg_ev(V_xe)=", p_leg_ev(i)
	v_xe=v_xe+0.00001
	i=i+1
enddo
 close(111)

!----------------------ciclul_real_inchis---------------------------
!call system('gnuplot -p ./PLOT_SCRIPT/ciclul_real_inchis.script')

!----------------------------Sume-----------------------------------
sum_p_a=0
do i=1,180
	teta_adm(i) = i
	teta=i
	!p_a(teta e)=a1*v(teta a)**2+b1*v(teta a) +c1
	!p_a(i)=ae1*v(teta)**2+be1*v(teta)+ce1
	v(teta)=v2+(pi*d**2)/4*R*(1+lamda/4-cos(teta*rad)-lamda/4*cos(2*teta*rad))
	p_a(i)=ae1*v(teta)**2+be1*v(teta)+ce1
	!print*, i, "P_a(teta_a)=", P_a(i)
	sum_p_a=sum_p_a+p_a(i)
enddo	
print*, "sum_p_a=", sum_p_a

sum_p_e=0
do i = 540,720
	teta_ev(i)=i
	teta=i
	!p_e=a2*v(teta_e)**2+b2*v(teta e)+c2
	v(teta)=v2+(pi*d**2)/4*R*(1+lamda/4-cos(teta*rad)-lamda/4*cos(2*teta*rad))
	p_e(i)=ae2*v(teta)**2+be2*v(teta)+ce2
	!print*, "P_e(teta_e)=", P_e(i)
	sum_p_e=sum_p_e+p_e(i)
enddo	
print*, "sum_p_e=", sum_p_e

sum_p_la=0
do i=180,250
teta=i
	teta_la(i)=i
	v(teta)=v2+(pi*d**2)/4*R*(1+lamda/4-cos(teta*rad)-lamda/4*cos(2*teta*rad))
	p_la(i)=ae3*v(teta)+be3
	!print*, "P_la(teta_la)=", P_la(i)
	sum_p_la=sum_p_la+p_la(i)
enddo	
print*, "sum_p_la=", sum_p_la

sum_p_le=0
do i=500,540
teta=i
	teta_le(i)=i
	!p_le(i)=ae4*v(teta)**2+be4*v(teta)+ce4
	p_le(i)=ae4*v(i)**2+be4*v(i)+ce4
	!print*, "P_le(teta_le)=", P_le(i)
	sum_p_le=sum_p_le+p_le(i)
enddo	
print*, "sum_p_le=", sum_p_le

print*, "Calculul presiunii medii"

!print*, "p_cad(i)", p_cad(1)
do i=1,80
	if (i==1) then
	p_cad(1)=p(1)
	else
	p_cad(i)=p_cad(i-1)+p(i)
	endif
	!print*, "p_cad(i)", p_cad(i)
enddo

p_m_ciclu_principal=p_cad(80)/81
print*, "p_m_ciclu_principal=", p_m_ciclu_principal

p_m_ciclu_secundar=((sum_p_a/182)+(sum_p_e/182)+(sum_p_la/72)+(sum_p_le/42))/4
print*, "p_m_ciclu_secundar=", p_m_ciclu_secundar

pm=(p_m_ciclu_principal+p_m_ciclu_secundar)/2
print*, "Presiunea_medie=", pm

pu=(pm*vc*eta*n)/120
print*, "Presiunea_utila=", pu

OPEN(23,FILE='teta_m.txt')
do i=1,720
	teta_m=i
	write(23,*) teta_m
enddo
 close(23) 

!--------------------------------------------------------------------

OPEN(112,FILE='Presiunea_in_functie_de_teta.txt')

!print*, "----------p_a(i)    teta_adm(i)-----------"

do i=1,180
	!print*, p_a(i), teta_adm(i)
	write(112,*) p_a(i), teta_adm(i)
enddo

!print*, "----------p_la(i)    teta_la(i)-----------"	
do i=180,250
	!print*, p_la(i), teta_la(i)
	write(112,*) p_la(i), teta_la(i)
enddo

do i=1,80
	!teta=i
	write(112,*) p(i), thetha(i)
enddo

!print*, "----------p_le(i)    teta_le(i)-----------"	

do i=500,540
	!print*, p_le(i), teta_le(i)
	write(112,*) p_le(i), teta_le(i)
enddo

!print*, "----------p_e(i)    teta_ev(i)-----------"

do i=540,720
	!print*, p_e(i), teta_ev(i)
	write(112,*) p_e(i), teta_ev(i)
enddo
			
 close(112)

OPEN(65,FILE='teta_m_pm.txt')
do i=1,720
	teta_m=i
	write(65,*) pm, teta_m
enddo
 close(65)
 
!call system('gnuplot -p ./PLOT_SCRIPT/Presiunea_in_functie_de_teta.script')
call system('paste temperaturi.txt thetha.txt > temperaturi_in_functie_de_teta.txt')
!call system('gnuplot -p ./PLOT_SCRIPT/temperatura_in_functie_de_teta.script')
!-------------------------------------------------------------------
!doua grafice Presiunea in fucntie de teta

!#############################Matricea-5#############################
ae5=2.545
be5=-136.364
!####################################################################

!#############################Matricea-6#############################
ae6=38
be6=-12900
!####################################################################

!#############################Matricea-7#############################
ae7=-12
be7=7100
!####################################################################

!print*, " m(teta)	  ", "  f_ip(teta)	  ", "   Tg(teta)", "		z(teta)", "		K(teta)	", "	norm(teta)	", "f_sigma(teta)	"

OPEN(72,FILE='forte_piston.txt')

do i=250,500
theta(i)=i
if (250<=theta(i).and.theta(i)<360) then
	t_thetha(i)=(28*theta(i)-1500)/11
	else if (360<=theta(i).and.theta(i)<400) then
		t_thetha(i)=38*theta(i)-12900
		else if (400<=theta(i).and.theta(i)<=500) then
			t_thetha(i)=-12*theta(i)+7100
endif
!print*, i, "T(teta)=", t_thetha(i), theta(i)
end do

do i=1,720

teta=i
if (0<=i.and.i<=180) then
	p_teta(i)=p_a(i)
	else if (180<i.and.i<250) then
		p_teta(i)=p_leg_adm(i)
		else if (250<=i.and.i<=500) then
			p_teta(i)=(mamp*Ram*t_thetha(i))/v(teta)
			else if (500<i.and.i<540) then
				p_teta(i)=p_le(i)
				else if (540<=i.and.i<=720) then
					p_teta(i)=p_e(i) ! sau pres_ev era p_ev
endif	
!print*, i, "p(teta)=", p_teta(i)

!f_g(teta)=(p(teta)-P_mediu(4))*(pi*D**2)/4
f_g(teta)=(p_teta(i)-P_mediu)*(pi*D**2)/4
!print*, i, "F_g(teta)=", f_g(i), p_teta(i), v(teta)
!f_ip(teta)=-(m_p+m_b/3)*(omega**2*r*(cos(teta*pi/180)+lamda*cos(2*teta*pi/180)))
f_ip(teta)=-(mp+mb/3)*(omega**2*r*(cos(teta*rad)+lamda*cos(2*teta*rad)))

!f_sigma(teta)=f_g(teta)+f_ip(teta)
f_sigma(teta)=f_g(teta)+f_ip(teta)
!print*,"f_sigma(teta)", f_sigma(teta)  !nu da bine mai verifica in mtch sau calcul si RAD
!n(teta)=f_sigma(teta)*tan(asin(lamda*sin(teta*pi/180)))
norm(teta)=f_sigma(teta)*tan(asin(lamda*sin(teta*rad)))!si aici e o greseala

!k(teta)=f_sigma(teta)/(cos(asin(lamda*sin(teta*pi/180))))
k(teta)=f_sigma(teta)/(cos(asin(lamda*sin(teta*pi/180))))

!z(teta)=k(teta)*cos(teta*pi/180+asin(lamda*sin(teta*pi/180)))
z(teta)=k(teta)*cos(teta*pi/180+asin(lamda*sin(teta*pi/180)))

!tg(teta)=k(teta)*sin(teta*pi/180+asin(lamda*sin(teta*pi/180)))
tg(teta)=k(teta)*sin(teta*pi/180+asin(lamda*sin(teta*pi/180)))

m(teta)=r*Tg(teta)!, nu bun

write(72,*) f_sigma(teta), norm(teta), k(teta), z(teta), tg(teta), teta
!print*,  m(teta),  f_ip(teta), tg(teta), z(teta), k(teta), norm(teta), f_sigma(teta)
enddo
 close (72)
!call system('gnuplot -p ./PLOT_SCRIPT/forte_piston.script')

print*,"Moment:"
!do i=0,720
!	teta=i
!	m(teta)=r*Tg(teta)!, nu bun
!	print*, "m(teta)", m(teta)
!enddo
!Mom_mediu=(integrala de la 0-720 din) m(teta)/720
!enddo
!
!print*, "Puterea utila"
!P_utila=(eta*nr_cil*Mom_mediu*n*pi)/30
!
!do teta=0,720
	!t_suma(teta)=tg(teta+80)+tg(teta+160+tg(teta+240+tg(teta+320+tg(teta+400+tg(teta+480+tg(teta+500+tg(teta+640)
!	case (teta)
!		(0<=teta.and.teta<=160)
!			t_t(teta)=
!

!#############################Matricea-x#############################
!####################################################################



!--------------------------------Format------------------------------
200 format (' Gauss elimination with scaling and pivoting ',/,/,' Matrix A and vector b')
201 format (6f12.6)
100 format (e20.15)

!test=12465.6454654654
!print '(F16.10 E15.7 G15.7)', test, test, test
!9.8765430  0.9876543E+01  9.8765430
 
!------------------------------End_format----------------------------
!-----------------------------Program_end----------------------------
end program ultim 
!---------------------------Sfarsit_program--------------------------

!-----------------------------DE_RETINUT-----------------------------
!la call gauss_X trebuie sa pun a1/2/3/4 pentru fiecare element, dar
!sa si modific instructiunea din subrutina
!verifica matricele la gauss

!------------------------------subrutine-----------------------------
 subroutine gauss_1(a1,b1,x1,n_gauss)
!===========================================================
! Solutions to a system of linear equations A*x=b
! Method: Gauss elimination (with scaling and pivoting)
! Alex G. (November 2009)
!-----------------------------------------------------------
! input ...
! a(n,n) - array of coefficients for matrix A
! b(n)   - array of the right hand coefficients b
! n      - number of equations (size of matrix A)
! output ...
! x(n)   - solutions
! coments ...
! the original arrays a(n,n) and b(n) will be destroyed 
! during the calculation
!===========================================================
implicit none 
integer n_gauss
double precision a1(n_gauss,n_gauss), b1(n_gauss), x1(n_gauss)
double precision s1(n_gauss)
double precision c1, pivot1, store1
integer i1, j1, k1, l1

! step 1: begin forward elimination
do k1=1, n_gauss-1

! step 2: "scaling"
! s(i) will have the largest element from row i 
  do i1=k1,n_gauss                       ! loop over rows
    s1(i1) = 0.0
    do j1=k1,n_gauss                    ! loop over elements of row i
      s1(i1) = max(s1(i1),abs(a1(i1,j1)))
    end do
  end do

! step 3: "pivoting 1" 
! find a row with the largest pivoting element
  pivot1 = abs(a1(k1,k1)/s1(k1))
  l1 = k1
  do j1=k1+1,n_gauss
    if(abs(a1(j1,k1)/s1(j1)) > pivot1) then
      pivot1 = abs(a1(j1,k1)/s1(j1))
      l1 = j1
    end if
  end do

! Check if the system has a sigular matrix
  if(pivot1 == 0.0) then
    write(*,*) ' The matrix is sigular '
    return
  end if

! step 4: "pivoting 2" interchange rows k and l (if needed)
if (l1 /= k1) then
  do j1=k1,n_gauss
     store1 = a1(k1,j1)
     a1(k1,j1) = a1(l1,j1)
     a1(l1,j1) = store1
  end do
  store1 = b1(k1)
  b1(k1) = b1(l1)
  b1(l1) = store1
end if

! step 5: the elimination (after scaling and pivoting)
   do i1=k1+1,n_gauss
      c1=a1(i1,k1)/a1(k1,k1)
      a1(i1,k1) = 0.0
      b1(i1)=b1(i1)- c1*b1(k1)
      do j1=k1+1,n_gauss
         a1(i1,j1) = a1(i1,j1)-c1*a1(k1,j1)
      end do
   end do
end do

! step 6: back substiturion 
x1(n_gauss) = b1(n_gauss)/a1(n_gauss,n_gauss)
do i1=n_gauss-1,1,-1
   c1=0.0
   do j1=i1+1,n_gauss
     c1=c1+a1(i1,j1)*x1(j1)
   end do 
   x1(i1) = (b1(i1)- c1)/a1(i1,i1)
end do

end subroutine gauss_1

!-----------------------Matrix_2-----------------------
 subroutine gauss_2(a2,b2,x2,n_gauss)
!===========================================================
! Solutions to a system of linear equations A*x=b
! Method: Gauss elimination (with scaling and pivoting)
! Alex G. (November 2009)
!-----------------------------------------------------------
! input ...
! a(n,n) - array of coefficients for matrix A
! b(n)   - array of the right hand coefficients b
! n      - number of equations (size of matrix A)
! output ...
! x(n)   - solutions
! coments ...
! the original arrays a(n,n) and b(n) will be destroyed 
! during the calculation
!===========================================================
implicit none 
integer n_gauss
double precision a2(n_gauss,n_gauss), b2(n_gauss), x2(n_gauss)
double precision s2(n_gauss)
double precision c2, pivot2, store2
integer i2, j2, k2, l2

! step 1: begin forward elimination
do k2=1, n_gauss-1

! step 2: "scaling"
! s(i) will have the largest element from row i 
  do i2=k2,n_gauss                       ! loop over rows
    s2(i2) = 0.0
    do j2=k2,n_gauss                    ! loop over elements of row i
      s2(i2) = max(s2(i2),abs(a2(i2,j2)))
    end do
  end do

! step 3: "pivoting 1" 
! find a row with the largest pivoting element
  pivot2 = abs(a2(k2,k2)/s2(k2))
  l2 = k2
  do j2=k2+1,n_gauss
    if(abs(a2(j2,k2)/s2(j2)) > pivot2) then
      pivot2 = abs(a2(j2,k2)/s2(j2))
      l2 = j2
    end if
  end do

! Check if the system has a sigular matrix
  if(pivot2 == 0.0) then
    write(*,*) ' The matrix is sigular '
    return
  end if

! step 4: "pivoting 2" interchange rows k and l (if needed)
if (l2 /= k2) then
  do j2=k2,n_gauss
     store2 = a2(k2,j2)
     a2(k2,j2) = a2(l2,j2)
     a2(l2,j2) = store2
  end do
  store2 = b2(k2)
  b2(k2) = b2(l2)
  b2(l2) = store2
end if

! step 5: the elimination (after scaling and pivoting)
   do i2=k2+1,n_gauss
      c2=a2(i2,k2)/a2(k2,k2)
      a2(i2,k2) = 0.0
      b2(i2)=b2(i2)- c2*b2(k2)
      do j2=k2+1,n_gauss
         a2(i2,j2) = a2(i2,j2)-c2*a2(k2,j2)
      end do
   end do
end do

! step 6: back substiturion 
x2(n_gauss) = b2(n_gauss)/a2(n_gauss,n_gauss)
do i2=n_gauss-1,1,-1
   c2=0.0
   do j2=i2+1,n_gauss
     c2=c2+a2(i2,j2)*x2(j2)
   end do 
   x2(i2) = (b2(i2)- c2)/a2(i2,i2)
end do

end subroutine gauss_2
!---------------------END_Matrix_2---------------------

!-----------------------Matrix_4-----------------------
 subroutine gauss_4(a4,b4,x4,n_gauss)
implicit none 
integer n_gauss
double precision a4(n_gauss,n_gauss), b4(n_gauss), x4(n_gauss)
double precision s4(n_gauss)
double precision c4, pivot4, store4
integer i4, j4, k4, l4

! step 1: begin forward elimination
do k4=1, n_gauss-1

! step 2: "scaling"
! s(i) will have the largest element from row i 
  do i4=k4,n_gauss                       ! loop over rows
    s4(i4) = 0.0
    do j4=k4,n_gauss                    ! loop over elements of row i
      s4(i4) = max(s4(i4),abs(a4(i4,j4)))
    end do
  end do

! step 3: "pivoting 1" 
! find a row with the largest pivoting element
  pivot4 = abs(a4(k4,k4)/s4(k4))
  l4 = k4
  do j4=k4+1,n_gauss
    if(abs(a4(j4,k4)/s4(j4)) > pivot4) then
      pivot4 = abs(a4(j4,k4)/s4(j4))
      l4 = j4
    end if
  end do

! Check if the system has a sigular matrix
  if(pivot4 == 0.0) then
    write(*,*) ' The matrix is sigular '
    return
  end if

! step 4: "pivoting 2" interchange rows k and l (if needed)
if (l4 /= k4) then
  do j4=k4,n_gauss
     store4 = a4(k4,j4)
     a4(k4,j4) = a4(l4,j4)
     a4(l4,j4) = store4
  end do
  store4 = b4(k4)
  b4(k4) = b4(l4)
  b4(l4) = store4
end if

! step 5: the elimination (after scaling and pivoting)
   do i4=k4+1,n_gauss
      c4=a4(i4,k4)/a4(k4,k4)
      a4(i4,k4) = 0.0
      b4(i4)=b4(i4)- c4*b4(k4)
      do j4=k4+1,n_gauss
         a4(i4,j4) = a4(i4,j4)-c4*a4(k4,j4)
      end do
   end do
end do

! step 6: back substiturion 
x4(n_gauss) = b4(n_gauss)/a4(n_gauss,n_gauss)
do i4=n_gauss-1,1,-1
   c4=0.0
   do j4=i4+1,n_gauss
     c4=c4+a4(i4,j4)*x4(j4)
   end do 
   x4(i4) = (b4(i4)- c4)/a4(i4,i4)
end do

end subroutine gauss_4
!---------------------END_Matrix_4---------------------

!--------------------------sfarsit_subrutine-------------------------
