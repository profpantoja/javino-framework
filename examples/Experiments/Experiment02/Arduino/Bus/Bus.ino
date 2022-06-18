#include <Ultrasonic.h>
#include <Javino.h>

#define LDRPin    A0
#define BLightPin 33
#define LightPin  6
#define TrigPin   4 
#define EchoPin   5
#define BuzzPin   26
#define M1        7
#define M2        8
#define M3        9
#define M4        50

#define Stop   0
#define Run    1

#define TXPin 12                                /*Pino Transmissor*/
#define RXPin 11                                /*Pino Receptor*/


Javino j; 
Ultrasonic ultrasonic(TrigPin, EchoPin);

boolean accident  = false;
boolean wait      = false;
void setup() {
  Serial.begin(9600);
  pinMode(LightPin, OUTPUT);
  pinMode(BLightPin, OUTPUT);
  pinMode(BuzzPin, OUTPUT);
  pinMode(M1, OUTPUT);
  pinMode(M2, OUTPUT);
  pinMode(M3, OUTPUT);
  pinMode(M4, OUTPUT);

  j.setId("ROMA");                              /*parâmetros de configuração*/
  j.enableRF(TXPin,RXPin);
  j.setCipherKey("0123456789abcdef");

}

void loop(){
 if(j.availableMsg()){
    String msg = j.getMsg();
    if(msg == "getMessage") {
      j.buffer2USB();   
    } else if(msg == "getPercepts") {
      j.sendMsg(getPercepts());
    } else if(msg == "turnOnLight") {
      light(true);
    } else if(msg == "turnOffLight") {
      light(false);
    } else if(msg == "accidentAlert") {
      strongAlert();
    } else if(msg == "stopBus") {
      bus(Stop);
    } else if(msg == "runBus") {
      bus(Run);
    }
 }

 if(j.availableMsgRF()){
    digitalWrite(BuzzPin, HIGH);
    delay(100);
    digitalWrite(BuzzPin, LOW);
    String msg = j.getMsg();                 
    if(msg=="tell;helpComing"){
      delay(100);
      digitalWrite(BuzzPin, HIGH);
      delay(100);
      digitalWrite(BuzzPin, LOW);
      delay(100);
      digitalWrite(BuzzPin, HIGH);
      delay(100);
      digitalWrite(BuzzPin, LOW);
    }
 }
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

int getLuminosity() {
  int intLDR = analogRead(LDRPin);
  return intLDR;
}

float getObstacleDistance() {
  float cmMsec;
  long microsec = ultrasonic.timing();
  cmMsec = ultrasonic.convert(microsec, Ultrasonic::CM);
  return cmMsec;
}

String getPercepts() {
  String valLumi = String(getLuminosity());
  String valProx = String(getObstacleDistance());
  return "luminosity("+valLumi+");proximity("+valProx+")";
}

void light(boolean action){
  if(action){
    digitalWrite(LightPin, HIGH);
  }else{
    digitalWrite(LightPin, LOW);
  }
}

void breakLight(boolean action){
  if(action){
    digitalWrite(BLightPin, HIGH);
  }else{
    digitalWrite(BLightPin, LOW);
  }
}

void buzzer(boolean action){
  if(action){
    digitalWrite(BuzzPin, HIGH);
  }else{
    digitalWrite(BuzzPin, LOW);
  }
}

void bus(int action){
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
