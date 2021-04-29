#include <Javino.h>
Javino j;
void setup() {
  Serial.begin(9600);
  j.enableRF(12,11);
}

void loop() {
  if(j.availableMsgRF()){
    Serial.println(j.getMsg());
  }
}
