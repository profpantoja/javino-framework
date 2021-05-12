/*
  Javino.cpp - Library communication for Arduino and Jason.
	Version Stable 2.0
    Created by Lazarin, NM and Pantoja, CE - January 29, 2015.
    nilson.lazarin@cefet-rj.br
	carlos.pantoja@cefet-rj.br

  Updated in 2021-05-12

*/

#include "Arduino.h"
#include "Javino.h"
#include "VirtualWire.h"

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

String Javino::getSrc()
{
   return _srcMsg;
}

String Javino::getDst()
{
   return _dstMsg;
}

boolean Javino::availableMsg(){
    start();
	listening();  //removido da função start...
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
}

void Javino::listening(){
  if(Serial.available()>0){
    registrator();
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

void Javino::registrator(){
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
    int converted = x+(y*16);
    return converted;
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
  String output;
  for(int i=6;i<sizein;i++){
    output=output+in[i];
  }
  return output;
}


String Javino::int2Hex(int v){
    String  stringOne =  String(v, HEX);
    if(v<16){
      stringOne="0"+stringOne;
    }
  return stringOne;
}

void Javino::enableRF(int pinTX, int pinRX){
	vw_set_ptt_inverted(true);
	pinMode(pinTX, OUTPUT);
	vw_set_tx_pin(pinTX);
	pinMode(pinRX, INPUT);
	vw_set_rx_pin(pinRX);
	vw_setup(2048);
	vw_rx_start(); 
}

void Javino::listeningRF(){
	uint8_t buf[100];
    uint8_t buflen = 100;
	if (vw_get_message(buf, &buflen)){
	  for (int i=0; i < buflen; i++){
		if(i==4){
			_msg = preambleRF(_finalMsg);
			if(!_msg){i=101;}
		}
		registratorRF(buf[i]);
	  }
	  if(_msg){
		treatMsgRF();
	  }	  
	}else{
		abort();
	}
}

void Javino::registratorRF(char byteIn){
	_d++;
	if(_d==9){_x=B64toInt(byteIn)*64;}
	if(_d==10){_x=_x+B64toInt(byteIn);}
	_finalMsg = _finalMsg+byteIn;
}

boolean Javino::preambleRF(String strHeader){
  boolean out=false;
	if(strHeader.substring(0,2)=="//"){
		if(strHeader.substring(2,4)=="//"){	
			out=true; /*broadcast*/
		}else if(_qtdAlias>0){
			String multiGR = strHeader.substring(2,4);
			for (int i = 1; i <= _qtdAlias; i++) {
				if(multiGR==getMyGroup(i)){
					out=true; /*multicast*/
					i=_qtdAlias+1;
				}
			}
		}
	}
	else if(_qtdAlias>0){
		for (int i = 1; i <= _qtdAlias; i++) {
			if((strHeader)==getAlias(i).substring(0,4)){
				out=true; /*unicast*/
				i=_qtdAlias+1;
			}
		}
	}
  return out;
}

void Javino::treatMsgRF(){
	if(_x==((_d-10)*8)){
		_dstMsg=_finalMsg.substring(0,4);
		_srcMsg=_finalMsg.substring(4,8);
		_finalMsg=_finalMsg.substring(10,_d);
		_msg=true;
	}else{
		_msg=false;
	}
}

boolean Javino::availableMsgRF(){
	_msg=false;
	_x=0;
	_d=0;
	_finalMsg="";
	_srcMsg="";
	_dstMsg="";
	listeningRF();
	return _msg;
}

//implementando recurso de endereçamento em Base64
void Javino::setId(String strID){
  _me = strID;
  _qtdAlias = getQtdAlias();
}

void Javino::setAlias(String strAlias){
  if(strAlias.length()==4){
  boolean exist = false;
  int x = getQtdAlias();
  for (int i = 1; i <= x; i++) {
    if(getAlias(i)==strAlias){
      exist = true;
    }
  }
  if(!exist){
   _me = _me + strAlias; 
  }   
}
  _qtdAlias = getQtdAlias();
}

int Javino::getQtdAlias(){
  return _me.length()/4;
}

String Javino::getAlias(int i){
  i = (i-1)*4;
  return _me.substring(i,i+4);
}

String Javino::getMyGroup(int i){
  i = (i-1)*4;
  return _me.substring(i,i+2);
}

String Javino::getId(){
  int x = getQtdAlias();
  String out;
  if(x==0){
	out = "[]";  
  }
   for (int i = 1; i <= x; i++) {
    out = out+"["+getAlias(i)+"]";
  }
  return out;
}

void Javino::sendMsgRF(String strMsgIn){
	Javino::sendMsgRF("////",strMsgIn);
}

void Javino::sendMsgRF(String destination, String strMsgIn){
	String strSource="////";
	if(_me!=""){strSource=getAlias(1);}
	strMsgIn = destination+strSource+sizeMSG_B64(strMsgIn.length()*8)+strMsgIn;
	_x = strMsgIn.length(); 
	if(_x>74){
			Serial.println("Sorry! It is only allowed 64 characters!");
	}else{
		char charMsgIn[_x];
		strMsgIn.toCharArray(charMsgIn, _x+1);
		vw_send((uint8_t *)charMsgIn, _x);
		vw_wait_tx();
		delay(50);
	}
}

String Javino::int2B64(int i){
	/*Based on RFC4648*/
	/*https://tools.ietf.org/html/rfc4648*/
  String vI="A";
  switch (i) {
    case 0: vI="A";  break;
	case 1: vI="B";  break;
	case 2: vI="C";  break;
	case 3: vI="D";  break;	
	case 4: vI="E";  break;
	case 5: vI="F";  break;
	case 6: vI="G";  break;
	case 7: vI="H";  break;
	case 8: vI="I";  break;
	case 9: vI="J";  break;
	case 10: vI="K";  break;
	case 11: vI="L";  break;	
	case 12: vI="M";  break;
	case 13: vI="N";  break;
	case 14: vI="O";  break;
	case 15: vI="P";  break;
	case 16: vI="Q";  break;
	case 17: vI="R";  break;
	case 18: vI="S";  break;
	case 19: vI="T";  break;	
	case 20: vI="U";  break;
	case 21: vI="V";  break;
	case 22: vI="W";  break;
	case 23: vI="X";  break;
	case 24: vI="Y";  break;
	case 25: vI="Z";  break;
	case 26: vI="a";  break;
	case 27: vI="b";  break;	
	case 28: vI="c";  break;
	case 29: vI="d";  break;
	case 30: vI="e";  break;
	case 31: vI="f";  break;
	case 32: vI="g";  break;
	case 33: vI="h";  break;
	case 34: vI="i";  break;
	case 35: vI="j";  break;	
	case 36: vI="k";  break;
	case 37: vI="l";  break;
	case 38: vI="m";  break;
	case 39: vI="n";  break;
	case 40: vI="o";  break;
	case 41: vI="p";  break;
	case 42: vI="q";  break;
	case 43: vI="r";  break;	
	case 44: vI="s";  break;
	case 45: vI="t";  break;
	case 46: vI="u";  break;
	case 47: vI="v";  break;
	case 48: vI="w";  break;
	case 49: vI="x";  break;
	case 50: vI="y";  break;
	case 51: vI="z";  break;	
	case 52: vI="0";  break;
	case 53: vI="1";  break;
	case 54: vI="2";  break;
	case 55: vI="3";  break;
	case 56: vI="4";  break;
	case 57: vI="5";  break;
	case 58: vI="6";  break;
	case 59: vI="7";  break;	
	case 60: vI="8";  break;
	case 61: vI="9";  break;
	case 62: vI="+";  break;
	case 63: vI="/";  break;
  }
  return vI;
}

int Javino::B64toInt(char s){
	/*Based on RFC4648*/
	/*https://tools.ietf.org/html/rfc4648*/
  int vI=0;
  switch (s) {
	case 'A': vI=0; break;
	case 'B': vI=1; break;
	case 'C': vI=2; break;
	case 'D': vI=3; break;
	case 'E': vI=4; break;
	case 'F': vI=5; break;
	case 'G': vI=6; break;
	case 'H': vI=7; break;
	case 'I': vI=8; break;
	case 'J': vI=9; break;
	case 'K': vI=10; break;
	case 'L': vI=11; break;
	case 'M': vI=12; break;
	case 'N': vI=13; break;
	case 'O': vI=14; break;
	case 'P': vI=15; break;
	case 'Q': vI=16; break;
	case 'R': vI=17; break;
	case 'S': vI=18; break;
	case 'T': vI=19; break;
	case 'U': vI=20; break;
	case 'V': vI=21; break;
	case 'W': vI=22; break;
	case 'X': vI=23; break;
	case 'Y': vI=24; break;
	case 'Z': vI=25; break;
	case 'a': vI=26; break;
	case 'b': vI=27; break;
	case 'c': vI=28; break;
	case 'd': vI=29; break;
	case 'e': vI=30; break;
	case 'f': vI=31; break;
	case 'g': vI=32; break;
	case 'h': vI=33; break;
	case 'i': vI=34; break;
	case 'j': vI=35; break;
	case 'k': vI=36; break;
	case 'l': vI=37; break;
	case 'm': vI=38; break;
	case 'n': vI=39; break;
	case 'o': vI=40; break;
	case 'p': vI=41; break;
	case 'q': vI=42; break;
	case 'r': vI=43; break;
	case 's': vI=44; break;
	case 't': vI=45; break;
	case 'u': vI=46; break;
	case 'v': vI=47; break;
	case 'w': vI=48; break;
	case 'x': vI=49; break;
	case 'y': vI=50; break;
	case 'z': vI=51; break;
	case '0': vI=52; break;
	case '1': vI=53; break;
	case '2': vI=54; break;
	case '3': vI=55; break;
	case '4': vI=56; break;
	case '5': vI=57; break;
	case '6': vI=58; break;
	case '7': vI=59; break;
	case '8': vI=60; break;
	case '9': vI=61; break;
	case '+': vI=62; break;
	case '/': vI=63; break;
  }
  return vI;
}

String Javino::sizeMSG_B64(int x){
	String outMSG_B64 = "AA";
	if(x>=0 && x<=4095){	
		int d = x/64;
		int r = x - (d*64);
		outMSG_B64=int2B64(d)+int2B64(r);
	}
	return outMSG_B64;
}