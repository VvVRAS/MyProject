/* Conectictare jumper
* +---------------------------------------------+
* | LCD 2x16       =>         SDA->A4; SCL-> A5 |
* +---------------------------------------------+
* | Potentiometru  =>         mid->A0           |
* +---------------------------------------------+
* | Servomotor     =>         signal->D9        |
* +---------------------------------------------+
* | Buton          =>         GND + RESET       |
* +---------------------------------------------+
* | HC-SR04        =>      Echo->D2; Trig->D3   |
* +---------------------------------------------+
* | AM2302         =>         Data-> D4         |
* +---------------------------------------------+
*/

//------------------------------------------------------------------------------
//              Librari
//------------------------------------------------------------------------------
#include <LiquidCrystal_I2C.h>      // Driver liv pentru LCD 2x16
#include <Wire.h>                   //Driver lib pentru potentiometru 
#include <Servo.h>                  // Driver servo
#include <Adafruit_Sensor.h>
#include "DHT.h"
//------------------------------------------------------------------------------
//              Definre obiecte
//------------------------------------------------------------------------------
LiquidCrystal_I2C lcd = LiquidCrystal_I2C(0x27,16,2); 
// 0x27 adresa i2c, 16 nr. coloane, 2 nr linii

Servo servoSG90;                    // Denumire obiect servomotor

//------------------------------------------------------------------------------
//              Definire variabile si pini
//------------------------------------------------------------------------------
int i = 0;                          // valori loop
char mystrgL1[16];                  // mystrg - string de 16 charactere pentru L1
char mystrgL2[16];                  // mystrg - string de 16 charactere pentru L2

// Potentiometru
int potPin1 = A0;                   // Setare potentiometru, pin A0

// HC-SR04
const int trigPin = 3;              // HC-SR04 trig pin set D3
const int echoPin = 2;              // HC-SR04 echo pin set D2
long duration;
int distance;

//AM2302 DHT-22
#define DHTPIN 4
#define DHTTYPE DHT22
DHT dht(DHTPIN, DHTTYPE);

//-------------------------------------------------------------------------------

void setup() {
  //AM2302 DHT22
  dht.begin();
  
  // Setup LCD
  lcd.init();                       // initiere modul lcd
  lcd.backlight();                  // pornire backlight

  // Setup potentiometru
  pinMode(potPin1, INPUT);          // Setare tip pin potentiometru A0 - input  
  servoSG90.attach(9);              // Atribuire control servo pe pinul D9

  // Setup  HC-SR04
  pinMode(trigPin, OUTPUT);         // Setare trig ca output
  pinMode(echoPin, INPUT);          // Setare echo ca input
  Serial.begin(9600);               // Serial comunication
}
//-----------------------------------------------------------------------------

void loop() {
  i++;
  int temp = dht.readTemperature();                       //citire temperatura in Celsius, (True pt Fahrenheit)
  
  //------------------------------------------------------------------------
  digitalWrite(trigPin, LOW);
  delayMicroseconds(5);
  digitalWrite(trigPin, HIGH);   
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  distance = duration*0.034/2;
  //------------------------------------------------------------------------
  lcd.clear();                                              // stergere mesaj lcd
  int var=analogRead(potPin1);                              // citire pozitie potentiometru de pe pinul A0
  distance = map(distance, 0, 20, 0, 180);                  // mapare valori potentiometru [0;1022] la [0;180]
  
  if (distance > 50) {
    servoSG90.write(distance);                              // pozitioneaza servo pe valoarea data de potentiometru
  }
  
  sprintf(mystrgL1, "d=%d mm T=%d C", distance, temp);      // valori linia 1 lcd
  lcd.print(mystrgL1);                                      // afisare linia 1 lcd
  lcd.setCursor (0,1);                                      // pozitionare cursor linia 2
  sprintf(mystrgL2, "t=%d s Pt=%d", i, var);                // valori linia 2 lcd
  lcd.print(mystrgL2);                                      // afisare linia 2 lcd
  delay(1000);                                              // delay o secunda
}
