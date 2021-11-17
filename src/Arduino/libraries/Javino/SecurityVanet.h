#ifndef SecurityVanet_h
#define SecurityVanet_h
#include "VirtualWire.h"
#include "Arduino.h"
#include "securino.h"
#include "Javino.h"
#include "Konverter.h"

class SecurityVanet{
  public:
    void setKey(String strKey);
    String encryptAES_CFB(String strIn);
    String decryptAES_CFB(String strIN);
   
};
#endif








