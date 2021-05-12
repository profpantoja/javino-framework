#include <Javino.h>
Javino j;
void setup() {
  j.setId("CAFE");
  Serial.begin(9600);
  j.enableRF(12,11);
}

void loop() {
   if(j.availableMsgRF()){
    digitalWrite(7,HIGH);
    Serial.print("TO: ");
    Serial.println(j.getDst());
    Serial.print("FROM: ");
    Serial.println(j.getSrc());
    Serial.print("MSG: ");
    Serial.println(j.getMsg());
  }
}
