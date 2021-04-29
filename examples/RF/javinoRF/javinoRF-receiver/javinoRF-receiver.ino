#include <Javino.h>
Javino j;
void setup() {
  Serial.begin(9600);
  j.setId("BASE");
  j.enableRF(12,11);
  delay(1000);
  Serial.println(j.getId());
}

void loop() {
  if(j.availableMsgRF()){
    Serial.println(j.getMsg());
  }
}
