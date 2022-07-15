program part1
    integer:: i, j, tcomp, tvar
    real:: rad, deg
    integer,parameter::N=9
    integer,parameter:: Tc=1
    integer, parameter::M=9
    real, parameter:: pi=3.14159
    
    !Date motor
    real:: nrcil=9                 !numarul de cilindrii
    real, parameter:: D=0.105      ![m]diametrul cameri de ardere
    real, parameter:: S=0.130      ![m]cursa pistonului
    real, parameter:: nr=2750      ![rpm]turatia
    real, parameter:: P=589000     ![W] Puterea motorului
    real, parameter:: alpha=0.9    !coeficientu de exces de aer
    
    !1) Calculul parametrilor termodinamici la altitudinea H
    real, parameter:: rho0=1.225   ![kg/m^3]densitatea 
    real, parameter:: p0=101325    ![Pa]presiunea la nivelul marii 
    real, parameter:: H=1          ![km]inaltimea considerata pentru calculul performantelor 
    real, parameter:: T0=288.15    ![K] temperatura la nivelul marii
    real, parameter:: taer=258.9   ![K] temperatura la nivelul marii
    real, parameter:: grad=6.5     ![K/km]
    real:: pH, TH, rhoH
    
     !2) Calculul presiunii de supraalimentare
    real, dimension(1:N):: PIC     !gradul de comprimare
    real, dimension(1:N):: P2      ![Pa]
    real, dimension(1:N):: Padmisie ![Pa] presiunea la admisie
    real, dimension(1:N):: EPS      !raportul de compresie
    real, parameter:: SGDA=0.93    !pierderea de sarcina in admisie
    real, parameter:: SGR=0.97     !pierderea de presiune in radiator
    real, parameter:: picmax=4     !Gradul maxim de comprimare
    real, dimension(1:N):: AB
    
    !3) Calcul temperaturii de admisie
    real:: etac=0.7    ![]randamentul de comprimare
    real:: k=1.4       !constanta adiabata
    real:: cpaer=1004.7  ![j/(kg*K)] caldura specifica la presiune constanta a aerului
    real, dimension(1:N):: T2ad  ![k] temperatura la admisie
    real, dimension(1:N):: Ladc  ![m^2/s^2] Lucru specific adiabat de comprimare
    real, dimension(1:N):: LmecC ![m^2/s^2] Lucrul mecanic specific de comprimare
    real:: XI !=0.953
    
    !4) Randamentul indicat
    real::etaW=0.95 !reprezinta gradul/raportul de plenitudine si se alege intre 0,94->0,97
    real:: etaAlpha, etad
    real:: etaca=1
    real, dimension(1:N)::etai
    
    !5) calculul presiunii in poarta supapei de evacuare
    real, dimension(1:N):: lc ![m^2/s^2] Lucurul mecanic pe compresor
    real:: rho=1 !gradul de descarcare al gazelor de evacuare
    Real:: T3=1100 ![k] temperatura din camera de ardere
    REal:: xeta=0.9 
    real:: psi=1 !coeficientul de spalare al camerei de ardere
    real:: L0=14.87 !raportul de masa al amestecului teretic aer-comb
    real:: omega
    real:: etat=0.714
    real, dimension(1:N):: delta, pev
    
    !6) Calculul coeficientului de umplere al cilindrului
    real:: etaVref=0.85
    real, dimension (N,M):: FI
    real, dimension (N,M):: ETAV
    
    !7) Calculul parametrilor de functionare ai compresorului
    real::Wmp
    real::R=286.69 ![m^2/(K*s^2)]
    real::etamc=0.94
    real, dimension (1:N)::rhoadm
    real, dimension (N,M):: Mac, Macciclu, Pc
    
    !8) Calculul presiunii medii indicate
    real::Pci=43500000 !puterea calorica inferioara a combustibilului utilizat
    real, dimension (N,M)::PMI
    
     !9)Calculul puterii indicate
     real, dimension (N,M)::Pind
     
     !10)Calculul presiunii medii de frecare
     real::a=0.35
     real::K1=1063.913  ![kg/s*m^2]
     real, dimension (1:M)::Pmkp
     real, dimension (N,M)::Pmf
    
    !11) Calculul puerii rezistentelor mecanice
    real, dimension (N,M)::Pr
    
    !12) Calculul randamentului mecanic
    real, dimension (N,M)::etamec
    
    !13) Calculul puterii efective
    real, dimension (N,M)::Pe
    
    !14) Calculul consumului specifi de combustibil
    real, dimension (N,M)::csp
    real::Pci2=43500
    
    !15) afisare grafic
    real, dimension (N,M)::Pef
    real, parameter:: Putere=100669.483 ![W] Putere tema
    real:: Ptema
    !------------------CALCULE------------------------------
     !open(0,file='*.txt')
    
!Calcul
    deg=180
    rad=pi/deg
    
    !1) Calculul Parametrilor termodinamici la altitudinea H 
    print*, '1) Calculul parametrilor termodinamici la altitudinea H'  
    print*, ' '
    open(1,file='1CalcParamTdLaAltH.txt')
    pH=p0*(1-(grad/T0)*H)**5.2553
    print*, 'ph=', ph, 'Pa'
    TH=T0-grad*H
    print*, 'TH=', TH, 'K'
    rhoH=rho0*(1-(grad/T0)*H)**4.2553
    print*, 'RhoH=', rhoH, 'kg/m^3'
    
    !2) Caulculul Presiunii de supraalimentare
    print*, '2) Caulculul Presiunii de supraalimentare'
    print*, ' '
    open(2,file='2CaclPresSupraalm.txt')
    do i=1,N
      PIC(i)=1+(i-1)*(picmax-1)/8
      EPS(i)=5+0.5*(i-1) !(i+9)*0.5 
      P2(i)=PIC(i)*pH*SGDA
      Padmisie(i)=P2(i)*SGR
      write(2,5) PIC(i), EPS(i), P2(i), Padmisie(i)
      print*, i, 'PIC=', PIC(i), 'EPS=', EPS(i), 'P2=', P2(i), 'Padm=', Padmisie(i) 
    enddo  
    close(2)
    
    !3) Calculul temperaturii de admisie
    print*, '3) Calculul temperaturii de admisie'
    print*, ' '
    open(3,file='3CalcTempAdm.txt')
    XI=(16)/(sqrt(TH)) 
    do i=1,N        
      T2ad(i)=TH*(((PIC(i)**((k-1)/k)-1)/etac)+1)
      Ladc(i)=1004.7*TH*XI*((PIC(i)**(0.286))-1) 
      LmecC(i)=Ladc(i)/etac
      write(3,5) T2ad(i), Ladc(i), LmecC(i)
      print*, i, 'T2ad=',T2ad(i), 'Ladc=', Ladc(i), 'LmecC=', LmecC(i)
    enddo
    close(3)
    
    !4) Randamentul indicat
    print*, '4) Randamentul indicat'
    print*, ' '
    open(4,file='4RandInd.txt')
    etad=1+(D*1000-150)/2000
    print*, 'etad=', etad
    etaAlpha=4.5*alpha*alpha*alpha-11.8*alpha*alpha+11.135*alpha-2.835
    print*, 'etaAlpha=', etaAlpha
    do i=1,N
      etai(i)=(1-(1/(((i+9)*0.5)**0.25)))*etaW*etad*etaAlpha*etaca
      write(4,5) etai(i)
      print*, i, 'etai=', etai(i)
    enddo
    close(4)
    
    !5) Calculul presiunii in poarta supapei de evacuare
    print*, '5) Calculul presiunii in poarta supapei de evacuare'
    print*, ' '    
    open(5,file='5CalcPresSupEv.txt')
    omega=0.87/(xeta*(1+(1/(psi*alpha*L0)*(TH/1100)*(1/(etac*etat)))))
    print*, 'omega=', omega
    do i=1,N
      lc(i)=Ladc(i)/etac
      delta(i)=1/((1-(omega*((PIC(i)**0.286)-1)/rho0))**4)
      pev(i)=delta(i)*pH
      print*, i, 'lc=', lc(i), 'delta=', delta(i), 'pev=', pev(i)
      write(5,5) lc(i), delta(i), pev(i)
    enddo
    close(5)

    !6) Calculul coeficientului de umplere al cilindrului
    print*, '6) Calculul coeficientului de umplere al cilindrului'
    print*, ' '
    open(61,file='61CoefUmplereFI.txt')
    open(62,file='62CoefUmplereETAV.txt')
    do i=1,N
        do j=1,N  
          FI(i,j)=(1.15*EPS(j)-(pH/Padmisie(i)))/(1.15*EPS(j)-1)
          ETAV(i,j)=etaVref*fi(i,j)*sqrt(T2ad(i)/288)
        enddo
    enddo
    print*, 'FI=', FI
    print*, 'ETAV=', ETAV
    write(61,5) FI
    write(62,5) ETAV
    close(61)
    close(62)
    
         
    !7)Calculul parametrilor de functionare ai compresorului
    print*, '7)Calculul parametrilor de functionare ai compresorului'
    print*, ' '
    open(7,file='7ParamFunctComp.txt')
    Wmp=nr*S/30
    print*, 'Wmp=', Wmp
    do i=1,N
        rhoadm(i)=padmisie(i)/(R*T2ad(i))
        print*, 'rhoadm=', rhoadm(i)
        do j=1,N 
         Mac(i,j)=((3.142*D**2)/16)*nrcil*etaV(i,j)*psi*rhoadm(i)*Wmp
         Macciclu(i,j)=Mac(i,j)*120/nr
         Pc(i,j)=(lc(i)*Mac(i,j))/etamc
         print*, 'Mac=', Mac (i,j), 'Macciclu=', Macciclu(i,j), 'Pc=', Pc(i,j)
        enddo
    enddo
     write(7,5) Mac, Macciclu, Pc
    close(7)

    open(8,file='8PresMedInd.txt')
    !8)Calculul presiuunii medii indicate
    print*, '8)Calculul presiuunii medii indicate'
    print*, ' '
    do i=1,N
        do j=1,N 
          PMI(i,j)=etai(j)*etaV(i,j)*rhoadm(i)*Pci/(alpha*L0)
!          print*, 'PMI=', PMI(i,j)
          !write(8,5) PMI(i,j)
        enddo
    enddo  
    print*, 'PMI=', PMI    
    write(8,5) PMI
    close(8)
    
    
    open(9,file='9PutereInd.txt')
    !9)Calculul puterii indicate
    print*, '9)Calculul puterii indicate'
    print*, ' '
    do i=1,N
        do j=1,M
          Pind(i,j)=(nrcil*D*D*PMI(i,j)*3.142*Wmp)/16
        enddo
    enddo
    print*, 'Pind=', Pind
    write(9,5) Pind    
    close(9)
    
    open(101,file='101PresMedPmkp.txt')
    open(102,file='102PresMedPmf.txt')
    !10) Caclculul presiunii medii
    print*, '10) Caclculul presiunii medii'
    print*, ' '
    do i=1,N
        do j=1,M
          Pmkp(j)=K1*(EPS(j)+8.5)*Wmp !problema numara pana la 81 trebuie pana la 9
          Pmf(i,j)=Pmkp(i)*(1-a*(1-pH/p0)*sqrt(T0/T2ad(i)))
        enddo
    enddo
    print*, 'Pmkp=', Pmkp
    print*, 'Pmf=', Pmf
    write(101,5) Pmkp
    write(102,5) Pmf
    close(101)
    close(102)
     
     open(11,file='11PutereaRezMec.txt')   
    !11) Calculul puterii rezistentelor mecanice
    print*, '11) Calculul puterii rezistentelor mecanice'
    print*, ' '
    do i=1,N
        do j=1,M
            Pr(i,j)=(nrcil*D*D*Pmf(i,j)*3.142*Wmp)/16
        enddo
    enddo
    print*,  'Pr=', Pr
    write(11,5) Pr       
    close(11)
    
    open(12,file='12RandMec.txt')
    !12) Calculul randamentului mecanic
    print*, '12) Calculul randamentului mecanic'
    print*, ' '
    do i=1,N
        do j=1,M
            etamec(i,j)=1-(Pr(i,j)/Pind(i,j))-(Pc(i,j)/Pind(i,j))
        enddo
    enddo
    print*, 'etamec=', etamec
    write(12,5) etamec
    close(12)         
    
    open(13,file='13PutereEf.txt')         
    !13) Calculul puterii efective
    print*, '13) Calculul puterii efective'
    print*, ' '
    do i=1,N
        do j=1,M
            Pe(i,j)=Pind(i,j)*etamec(i,j)
        enddo
    enddo
    print*, 'Pe=', Pe
    write(13,5) Pe
    close(13)        

    open(14,file='14ConsSpComb.txt')
!    open(15,file='15PutereaEf2.txt')
!    open(16, file='grafic.txt')
    !14) Calculul consumului specific de combusibil
    print*, '14) Calculul consumului specific de combusibil'
    print*, ' '
    do i=1,N
        do j=1,M
            csp(i,j)=3600/(etai(j)*etamec(i,j)*Pci2)
!            Pef(i,j)=Pe(i,j)/(nrcil*D*D) !aici
            !jos
            !write(16,6) EPS(i), csp(1,j), csp(2,j), csp(3,j), csp(4,j), csp(5,j), csp(6,j), csp(7,j), csp(8,j), csp(9,j), Pef(1,j), Pef(2,j), Pef(3,j), Pef(4,j), Pef(5,j), Pef(6,j), Pef(7,j), Pef(8,j), Pef(9,j), Ptema
            !sus
        enddo
    enddo
!    write(16,6) EPS, csp, Pef !(i,j), csp(2,j), csp(3,j), csp(4,j), csp(5,j), csp(6,j), csp(7,j), csp(8,j), csp(9,j), Pef(1,j), Pef(2,j), Pef(3,j), Pef(4,j), Pef(5,j), Pef(6,j), Pef(7,j), Pef(8,j), Pef(9,j), Ptema
    print*, 'csp=', csp
    write(14,5) csp
!    print*, 'Pef=', Pef  !aici
!    write(15,5) Pef     !aici
!    Ptema=Putere/(nrcil*D**2)  !aici
!    print*, 'Ptema=', Ptema     !aici
    close(14)
!    close(15)           !aici
!    close(16)           !aici
    
    open(15,file='15PutereaEf2.txt')
    !15) Afisare grafic
    print*, 'Afisare grafic'
    print*, ' '
    do i=1,N
        do j=1,M
            Pef(i,j)=Pe(i,j)/(nrcil*D*D) 
        enddo
    enddo
    print*, 'Pef=', Pef
    write(15,5) Pef
    close(15)
    Ptema=Putere/(nrcil*D**2)
    print*, 'Ptema=', Ptema

    print *, 'Sfarsit calcule'
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!pause
    
    open(161, file='grafic1.txt') !EPS
    open(162, file='grafic2.txt') !csp
    open(163, file='grafic3.txt') !Pef
!    write(16,7) EPS, csp, Pef
    do j=1,N
        !do j=1,M
          write(161,6) EPS(i), csp(i,1), csp(i,2), csp(i,3), csp(i,4), csp(i,5), csp(i,6), csp(i,7), csp(i,8), csp(i,9) !, Pef(1,j), Pef(2,j), Pef(3,j), Pef(4,j), Pef(5,j), Pef(6,j), Pef(7,j), Pef(8,j), Pef(9,j) !, Ptema
          write(162,6) EPS(j), csp(1,j), csp(2,j), csp(3,j), csp(4,j), csp(5,j), csp(6,j), csp(7,j), csp(8,j), csp(9,j) 
          write(163,6) EPS(j), Pef(1,j), Pef(2,j), Pef(3,j), Pef(4,j), Pef(5,j), Pef(6,j), Pef(7,j), Pef(8,j), Pef(9,j) !, Ptema
         !write(162,7) EPS, csp
         !write(162,6) csp
        !enddo
   enddo
!        write(162,7) csp

5 format(9f15.4)
6 format(19f15.4)
7 format(9f15.3)
end program part1
