#define TXPin 12                                /*Pino Transmissor*/
#define RXPin 11                                /*Pino Receptor*/

#include <Javino.h>
Javino j;



void setup() {
  j.setId("KaDU");                              /*parâmetros de configuração*/
  j.enableRF(TXPin,RXPin);
  j.setCipherKey("0123456789abcdef");

  Serial.begin(9600);
}

void loop(){
 
 j.sendMsgRF("broadcast message");                     /* enviando mensagem broadcast*/
 delay(500);
 j.sendMsgRF("//CA","multicast message");       /* enviando mensagem multicast*/
 delay(500);
 j.sendMsgRF("CAFE","unicast message");       /* enviando mensagem unicast*/
 delay(500);
  
}
