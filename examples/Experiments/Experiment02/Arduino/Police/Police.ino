#define TXPin 12                /*Pino Transmissor*/
#define RXPin 11                /*Pino Receptor*/

#include <Javino.h>
Javino j;

#define PinLight  2
#define PinBreakL 3
#define PinEcho   5
#define PinTrig   4
#define PinBuzzer 6
#define PinBridH1 7
#define PinBridH2 8
#define PinBridH3 9
#define PinBridH4 10
#define PinPLight 13
#define PinLDR    A0

String bufferMsgRF;
String bufferOrigem;
String bufferDestino;

void setup() {
  j.setId("POLI");                          /*parâmetros de configuração*/
  j.enableRF(TXPin,RXPin);
  j.setCipherKey("0123456789abcdef");

  pinMode(PinLight,   OUTPUT);
  pinMode(PinBreakL,  OUTPUT);
  pinMode(PinBuzzer,  OUTPUT);
  pinMode(PinPLight,  OUTPUT);
  pinMode(PinBridH1,  OUTPUT);
  pinMode(PinBridH2,  OUTPUT);
  pinMode(PinBridH3,  OUTPUT);
  pinMode(PinBridH4,  OUTPUT);
  pinMode(PinLDR,     INPUT);
  
  Serial.begin(9600);
}

void loop() {
  if(j.availableMsg()){
    String msg = j.getMsg();
    if(msg == "getMessage") {
      j.buffer2USB();   
    }
  }

  if(j.availableMsgRF()) {
    digitalWrite(PinBuzzer, HIGH);
    delay(100);
    digitalWrite(PinBuzzer, LOW);
    String msg = j.getMsg();                 
    if(msg=="tell;help"){
      delay(100);
      digitalWrite(PinBuzzer, HIGH);
      delay(100);
      digitalWrite(PinBuzzer, LOW);
      delay(100);
      digitalWrite(PinBuzzer, HIGH);
      delay(100);
      digitalWrite(PinBuzzer, LOW);
    }
  }
  
}
