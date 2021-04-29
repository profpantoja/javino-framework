#include <Javino.h>
Javino j;
String m;
int i;
void setup() {
  Serial.begin(9600);
  j.enableRF(12,11);
}

void loop(){
  i=random(1,1000);
  delay(i);
  m=i+" Mensagem broadcast na rede VANET";
  j.sendMsgRF(m);

  i=random(1,1000);
  delay(i);
  m=i+" Mensagem multicast na rede VANET para grupo (NF)";
  j.sendMsgRF("//NF",m);


  i=random(1,1000);
  delay(i);
  m=i+" Mensagem unicast na rede VANET para o usuário (DU) no grupo (KA)";
  j.sendMsgRF("KADU",m);
  }
