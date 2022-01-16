
#define LDRPin    A0
#define BuzzPin   28
#define Giroflex  26
#define BLightPin 24
#define LightPin  30

#define M1        7
#define M2        8
#define M3        9
#define M4        10

#define Stop   0
#define Run    1


#define TXPin 12                /*Pino Transmissor*/
#define RXPin 11                /*Pino Receptor*/


#include <Javino.h>
Javino j;


String bufferMsgRF;
String bufferOrigem;
String bufferDestino;

void setup() {
  pinMode(LightPin, OUTPUT);
  pinMode(BLightPin, OUTPUT);
  pinMode(BuzzPin, OUTPUT);
  pinMode(Giroflex, OUTPUT);
  pinMode(M1, OUTPUT);
  pinMode(M2, OUTPUT);
  pinMode(M3, OUTPUT);
  pinMode(M4, OUTPUT);


  j.setId("AMBU");                          /*parâmetros de configuração*/
  j.enableRF(TXPin,RXPin);
  j.setCipherKey("0123456789abcdef");

  Serial.begin(9600);
}

void loop() {
  /*
  if(j.availableMsg()){
    if(j.getMsg() == "getMessage") {
      j.buffer2USB();   
    }
}

  j.availableMsgRF();
  */

  if(!isNight()){
    ambulance(Run);
    light(true);
    breakLight(true);
    strongAlert();
    lightGiroflex(true);
  }else{
    ambulance(Stop);
    light(false);
    breakLight(false);
    lightAlert();
  }
  delay(1000);
  
}

void strongAlert(){
  buzzer(true);
  lightAlert();
  lightAlert();
  lightAlert();
  lightAlert();
  lightAlert();
  buzzer(false);
}

void lightAlert(){
  light(true);
  breakLight(true);
  delay(100);
  light(false);
  breakLight(false);
  delay(100);
}


void light(boolean action){
  if(action){
    digitalWrite(LightPin, HIGH);
  }else{
    digitalWrite(LightPin, LOW);
  }
}


void lightGiroflex(boolean action){
  if(action){
    digitalWrite(Giroflex, HIGH);
  }else{
    digitalWrite(Giroflex, LOW);
  }
}


void breakLight(boolean action){
  if(action){
    digitalWrite(BLightPin, HIGH);
  }else{
    digitalWrite(BLightPin, LOW);
  }
}

boolean isNight(){
  int intLDR = analogRead(LDRPin);
  if(intLDR<768){
    return true;
  }else{
    return false;
  }
}



void buzzer(boolean action){
  if(action){
    digitalWrite(BuzzPin, HIGH);
  }else{
    digitalWrite(BuzzPin, LOW);
  }
}

void ambulance(int action){
  if(action==0){
    digitalWrite(M1, HIGH);
    digitalWrite(M2, HIGH);
    digitalWrite(M3, HIGH);
    digitalWrite(M4, HIGH);
  }else if(action==1){
    digitalWrite(M1, HIGH);
    digitalWrite(M2, LOW);
    digitalWrite(M3, HIGH);
    digitalWrite(M4, LOW);
  }
}
