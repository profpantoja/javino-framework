#include <Javino.h>

void setup() {
  Serial.begin(9600);
}

void loop() {
  Javino j;
  j.enableRF(12,11);
  j.sendMsgRF("Hello Javino!");
  delay(5000);
}
