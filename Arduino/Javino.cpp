/*
  Javino.cpp - Library communication for Arduino and Jason.
Version Stable 1.1
  Created by Lazarin, NM and Pantoja, CE - January 29, 2015.
	nilson.lazarin@cefet-rj.br
	carlos.pantoja@cefet-rj.br

  Updated in 2015-06-21
  Released into the public domain.
*/

#include "Arduino.h"
#include "Javino.h"

Javino::Javino()
{

}

void Javino::sendMsg(String msg)
{
	msg = "fffe"+int2Hex(msg.length())+msg;
	Serial.println(msg);
}

String Javino::getMsg()
{
   return _finalMsg;
}

boolean Javino::availableMsg(){
    //_msg = false;
    start();
	  return _msg;
}

void Javino::start(){
  _x         = 261;
  _d         = 0;
  _n         = 10;
  /*
  for( int i = 0; i < sizeofarraymsg;  ++i ){
    _arraymsg[i] = (char)0;
  }
  */
  listening();
}

void Javino::listening(){
  if(Serial.available()>0){
    register();
  }
  else{
    timeout();
  }
}

void Javino::timeout(){
  delay(5);
  _n--;
  if(_n>0){
    listening();
  }else{
    abort();
  }
}

void Javino::register(){
    _arrayMsg[_d]=Serial.read();
    _d++;
    _x--;
    _n=5;
    monitorMsg();
}

void Javino::monitorMsg(){

  if((_d==4)){
        if((_arrayMsg[0]!='f')||(_arrayMsg[1]!='f')||(_arrayMsg[2]!='f')||(_arrayMsg[3]!='e')){
                abort();
        }
  }else if(_d==6){
    _x = sizeOfMsg();
  }

  if(_x==0){
    treatMsg();
  }else{
    listening();
  }
}

void Javino::abort(){
	_msg = false;
    _x=0;
}

int Javino::sizeOfMsg(){
    int x = forInt(_arrayMsg[5]);
    int y = forInt(_arrayMsg[4]);
    int convertido = x+(y*16);
    return convertido;
}

void Javino::treatMsg(){
  if(preamble()){
	_msg=true;
    _finalMsg = char2String(_arrayMsg,_d);
  }else{
	_msg=false;
  }
}

boolean Javino::preamble(){
    boolean out =false;
  if((_arrayMsg[0]=='f')&&(_arrayMsg[1]=='f')&&(_arrayMsg[2]=='f')&&(_arrayMsg[3]=='e')){
    out=true;
  }
  return out;
}


int Javino::forInt(char v){
  int vI=0;
  switch (v) {
    case '1': vI=1;  break;
    case '2': vI=2;  break;
    case '3': vI=3;  break;
    case '4': vI=4;  break;
    case '5': vI=5;  break;
    case '6': vI=6;  break;
    case '7': vI=7;  break;
    case '8': vI=8;  break;
    case '9': vI=9;  break;
    case 'a': vI=10; break;
    case 'b': vI=11; break;
    case 'c': vI=12; break;
    case 'd': vI=13; break;
    case 'e': vI=14; break;
    case 'f': vI=15; break;
  }
  return vI;
}

String Javino::char2String(char in[], int sizein){
  String saida;
  for(int i=6;i<sizein;i++){
    saida=saida+in[i];
  }
  return saida;
}


String Javino::int2Hex(int v){
    String  stringOne =  String(v, HEX);
    if(v<16){
      stringOne="0"+stringOne;
    }
  return stringOne;
}

