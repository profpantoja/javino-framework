#include <Javino.h>
Javino j;
String m;
String strI;
int i;
int x;
void setup() {
  Serial.begin(9600);
  j.enableRF(12,11);
}

void loop(){
  x++;
  i=random(1,1000);
  delay(i);
  strI=String(i);
  m=strI+";Broadcast na rede VANET";
  strI=String(x);
  m=strI+";"+m;
  Serial.println(m);
  j.sendMsgRF(m);
  
  x++;
  i=random(1,1000);
  delay(i);
  strI=String(i);
  m=strI+";Multicast na rede VANET para grupo (NF)";
  strI=String(x);
  m=strI+";"+m;
  Serial.println(m);
  j.sendMsgRF("//NF",m);

  x++;
  i=random(1,1000);
  delay(i);
  strI=String(i);
  m=strI+";Unicast na rede VANET usuário (DU) grupo (KA)";
  strI=String(x);
  m=strI+";"+m;
  Serial.println(m);
  j.sendMsgRF("KADU",m);
  
  if(x>1024){x=0;};
  }
