

// include the library code:
#include <LiquidCrystal_I2C.h>
#include <Wire.h>
#include <Servo.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);

Servo myservo; //obiect pt controlul servomotorului

int potPin1 = A0; // potentiometru

void setup() {
  
  // Set pin I/O
  pinMode(potPin1, INPUT);
  myservo.attach(9); //control servo pe pinul digital 9
  
  // initialize the LCD, 
  lcd.begin();
  lcd.backlight();
}

void loop() {
  for(int i = 0; i<10000; i++) {
    lcd.clear();
    char mystrg[16];
    int var=analogRead(potPin1);
    var = map(var, 0, 1022, 0, 180);
    sprintf(mystrg, "i=%d Val=%d", i, var);
    myservo.write(var); // pozitioneaza servo pe valoarea data de potentiometru
    lcd.print("------WRAS------");
    lcd.setCursor (0,1); // 2nd screen line
    lcd.print(mystrg);
    //lcd.print(millis() / 1000);
    delay(1000);
    //end screen line
  }
}
