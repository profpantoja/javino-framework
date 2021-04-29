#include <Javino.h>
Javino j;
void setup() {
  Serial.begin(9600);
  j.setId("KADU");
  j.setAlias("NFA1");
  j.setAlias("chon");
  delay(1000);
  Serial.println(j.getId());
}

void loop() {
  j.enableRF(12,11);
  if(j.availableMsgRF()){
    Serial.println(j.getMsg());
  }
}
