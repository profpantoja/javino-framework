#include <Javino.h>
Javino j;
void setup() {
  Serial.begin(9600);
  j.enableRF(12,11);
  j.setId("KaDU");
}

void loop(){
  j.sendMsgRF("broadcast");  
  j.sendMsgRF("//CA","multicast");
  j.sendMsgRF("CAFE","unicast");
  }
