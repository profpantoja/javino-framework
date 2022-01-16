#define TXPin 12                /*Pino Transmissor*/
#define RXPin 11                /*Pino Receptor*/

#include <Javino.h>
Javino j;

String bufferMsgRF;
String bufferOrigem;
String bufferDestino;

void setup() {
  j.setId("POLI");                          /*parâmetros de configuração*/
  j.enableRF(TXPin,RXPin);
  j.setCipherKey("0123456789abcdef");

  Serial.begin(9600);
}

void loop() {
  if(j.availableMsg()){
    if(j.getMsg() == "getMessage") {
      j.buffer2USB();   
    }
}

  j.availableMsgRF();
}
