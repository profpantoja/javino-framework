#include "Arduino.h"
#include "Konverter.h"
#include "Base64.h"

// Hexadecimal to ASCII conversion
void Konverter::hex2ascii(byte * hex, char * ascii){
  for(byte i = 0; i < 16; i++){
    if(hex[i] > 0x1f){ //tratativa para caracteres não imprimíveis serem ignorados
      ascii[i] = char(hex[i]);
    }
  }
}

// ASCII to Hexadecimal conversion
void Konverter::ascii2hex(char * ascii, byte * hex){
  for(byte i = 0; i < 16; i++){
    if(byte(ascii[i]) < 0x7f){ //tratativa para caracteres além da ASCII serem ignorados
      hex[i] = byte(ascii[i]);
    }
  }
}

String Konverter::byte2strBin(byte * in, int x){
  String strOut = "";
  String strIn="";
  int sizeStrIn=0;
  for(int i=0; i < x; i++){
      strIn = String(in[i],BIN);
      sizeStrIn=strIn.length();
      if(sizeStrIn<8){
        for(int a=0; a<8-sizeStrIn; a++){
          strIn="0"+strIn;
        }
      }
      strOut=strOut+strIn; 
   }  
  return strOut;
}

void Konverter::string2byteArray(String strIn, byte * bX){
	unsigned int sizeStrIn = strIn.length();
	char charOut[sizeStrIn+1];
	strIn.toCharArray(charOut, sizeStrIn+1);
	for(int i=0; i < sizeStrIn; i++){
		bX[i] = byte(charOut[i]);
	}
}

String Konverter::byteArray2String(byte * bIn){
	String strOut;
	for(int i=0; i < getSizeofMsgB64()-16; i++){
		strOut = strOut+char(bIn[i]);
	}
	return strOut;
}

String Konverter::byte2strB64(byte * byteIn, int x){
	unsigned int sizeOut = BASE64::encodeLength(x);
	char encoded[sizeOut];
	BASE64::encode((const byte*)byteIn,x, encoded);
	String strOut = String(encoded);
    return strOut;
}

void Konverter::strB64toByte(String str64, byte * bOut){
	unsigned int sizeStr = str64.length();
	char encoded[sizeStr];
	str64.toCharArray(encoded,sizeStr+1); 	
  setSizeofMsgB64(BASE64::decodeLength(encoded));
  uint8_t raw[getSizeofMsgB64()];
	BASE64::decode(encoded, raw);
	for(int i=0; i < getSizeofMsgB64(); i++){
	  bOut[i]=byte(raw[i]); 
	}
}

int Konverter::getSizeofMsgB64(){
return _sizeofMsgIN;
}

void Konverter::setSizeofMsgB64(int t){
  _sizeofMsgIN = t;
}