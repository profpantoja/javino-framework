#define TXPin 12                                /*Pino Transmissor*/
#define RXPin 11                                /*Pino Receptor*/
#include <Javino.h>
Javino j;

void setup() {
  j.setId("KaDU");
  j.setAlias("KaJU");                              
  j.enableRF(TXPin,RXPin);
  j.setCipherKey("0123456789abcdef");
  Serial.begin(9600);
  pinMode(3,OUTPUT);
}

void loop(){
  if(j.availableMsg()){
    Serial.println(j.getMsg());

    digitalWrite(3,HIGH);
    delay(1000);
    digitalWrite(3,LOW);
  }
  
}
