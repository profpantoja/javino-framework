#include <Javino.h>
Javino j;
int msgId=0;
void setup() {
  pinMode(13,OUTPUT);
  Serial.begin(9600);
  j.enableRF(12,11);
  delay(425);
}

void loop(){
    if(msgId<1024){
      String strI=String(msgId);
      String m=strI+";V2;precisoDeAjudaLocalABC";
      digitalWrite(13, true); // Flash a light to show transmitting
      j.sendMsgRF(m);
      digitalWrite(13, false);
      delay(500); 
      msgId++;     
    }
  }
