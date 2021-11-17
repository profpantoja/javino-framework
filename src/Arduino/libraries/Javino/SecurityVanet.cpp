#include "securino.h"
#include "Javino.h"
#include "Konverter.h"
#include "SecurityVanet.h"
Javino javino;
Securino securino;
Konverter konverter;

byte * byteMsgCifrada;
byte byteChave[16];
String strMsgCifradaB64;
String strMsgPlain;

byte *  byteMsgCifradaRecebida;
byte *  byteMsg;
byte *  byteXOrOut;
String  strClaro;

byte * IVRandom;

void SecurityVanet::setKey(String strKey){
  konverter.string2byteArray(strKey,byteChave);
}

/*
String SecurityVanet::encryptMessage(String strIN){
  byte byteMsg[strIN.length()];
  konverter.string2byteArray(strIN, byteMsg); 
  byteMsgCifrada = securino.encript("aes-128-cbc", byteMsg, byteChave);
  strMsgCifradaB64 = konverter.byte2strB64(byteMsgCifrada,32);
  return strMsgCifradaB64;
}

String SecurityVanet::decryptMessage(String strIN){
  byte byteMsgCifradaRecebida[32];
  konverter.strB64toByte(strIN,byteMsgCifradaRecebida);
  byteMsg = securino.decript("aes-128-cbc", byteMsgCifradaRecebida, byteChave);
  strMsgPlain = konverter.byteArray2String(byteMsg);
  return strMsgPlain;
}
*/

String SecurityVanet::encryptAES_CFB(String strIN){
  int tamanhoMsg = strIN.length();
  byte byteClaroCompleta[tamanhoMsg];
  konverter.string2byteArray(strIN,byteClaroCompleta);

  byte byteCifradaCompleta[tamanhoMsg+16];
  IVRandom = securino.getVIRandom();
  for(int i=0; i<16;i++){
    byteCifradaCompleta[i]=IVRandom[i];
  }

  boolean resta = true;
  int restante = tamanhoMsg-16;
  int posicao = 0;

  while (resta){
    int tamanhoBloco = 0;
    if(restante<=0){
      resta = false;
      tamanhoBloco = restante+16;
    }else{
      tamanhoBloco = 16;
  }

  byte * byteOutputBlock;
  byteOutputBlock = securino.encript("aes-128-ecb", IVRandom, byteChave);
  for(int i=0; i<tamanhoBloco; i++){
    byteCifradaCompleta[16+posicao+i] = byteOutputBlock[i] ^ byteClaroCompleta[posicao+i];
    if(resta){
      IVRandom[i]=byteCifradaCompleta[16+posicao+i];
    }
  }

  restante = restante-16;
  posicao = posicao+tamanhoBloco;    
  }
  strMsgCifradaB64 = konverter.byte2strB64(byteCifradaCompleta,tamanhoMsg+16);
  return strMsgCifradaB64;
}

String SecurityVanet::decryptAES_CFB(String strIN){
  byte byteMsgCifradaRecebida[strIN.length()];
  konverter.strB64toByte(strIN,byteMsgCifradaRecebida);
  int tamanhoMsg = konverter.getSizeofMsgB64()-16; 
  byte rIV[16];
  byte cipherTxt[tamanhoMsg];
  for(int i=0; i<tamanhoMsg+16; i++){
    if(i<16){
      rIV[i]=byteMsgCifradaRecebida[i];
    }else{
      cipherTxt[i-16]=byteMsgCifradaRecebida[i];
    }
  }

  byte plainText[tamanhoMsg];

  boolean resta = true;
  int restante = tamanhoMsg-16;
  int posicao = 0;

  while (resta){
    int tamanhoBloco = 0;
    if(restante<=0){
      resta = false;
      tamanhoBloco = restante+16;
    }else{
      tamanhoBloco = 16;
    }

    byte * XorOut;
    XorOut = securino.encript("aes-128-ecb", rIV, byteChave);
   
    for(int i=0; i<tamanhoBloco; i++){
      plainText[posicao+i] = XorOut[i] ^ cipherTxt[posicao+i];
        if(resta){
          rIV[i]=cipherTxt[posicao+i];
        }
    }

    restante = restante-16;
    posicao = posicao+tamanhoBloco;    
  }

  strMsgPlain = konverter.byteArray2String(plainText);
  return strMsgPlain;
}


