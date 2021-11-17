#define TXPin 12                /*Pino Transmissor*/
#define RXPin 11                /*Pino Receptor*/

#include <Javino.h>
Javino j;

void setup() {
  j.setId("CAFE");                          /*parâmetros de configuração*/
  j.enableRF(TXPin,RXPin);
  j.setCipherKey("0123456789abcdef");

  Serial.begin(9600);
}

void loop() {
   if(j.availableMsgRF()){                 /*recebendo mensagem RF*/
    Serial.print(" MSG: ");
    Serial.println(j.getMsg());
  }
}
