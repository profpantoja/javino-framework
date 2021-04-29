#include <Javino.h>
Javino j;
void setup() {
  Serial.begin(9600);
  j.enableRF(12,11);
}

void loop(){
  delay(1000);
  j.sendMsgRF("broadcast");  
  delay(1000);
  j.sendMsgRF("//NF","multicast");
  delay(1000);
  j.sendMsgRF("KADU","unicast");
  }
