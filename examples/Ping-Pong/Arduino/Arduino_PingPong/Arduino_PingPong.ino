#define AlertPin 10             /*Pino de ação Buzzer ou LED no Arduino*/

#include <Javino.h>             /*Include da Biblioteca Javino*/
Javino j;                       

void setup(){
  Serial.begin(9600);           /*[OBRIGATÓRIO] comunicação Serial do Arduino */
  pinMode(AlertPin,OUTPUT);     /*[OBRIGATÓRIO] pino de ação tipo OUTPUT */
  j.setId("CaFE");              /*[OPCIONAL] Identificação do Arduino */
}

void loop(){
  if(j.availableMsg()){         /*Caso o Arduino receba uma mensagem válida ele envia uma resposta */
    answer(j.getMsg());
  }
}

void answer(String ask){        /*Método da resposta do Arduino */
  if (ask=="Ping"){
    action();
    j.sendMsg("Pong");
  }else if (ask=="Pong"){
    action();
    action();
    j.sendMsg("Ping");
  }
}

void action(){                /* Ação realizada no Hardware (Buzzer ou LED) */
  digitalWrite(AlertPin,HIGH);
  delay(100);
  digitalWrite(AlertPin,LOW);
  delay(50);
}
