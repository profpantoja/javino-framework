/*
  Javino.h - Library communication for Arduino and Jason.
Version Stable 1.1
  Created by Lazarin, NM and Pantoja, CE - January 29, 2015.
    nilson.lazarin@cefet-rj.br
	carlos.pantoja@cefet-rj.br

  Updated in 2015-06-21
  Released into the public domain.

*/

#ifndef Javino_h
#define Javino_h
#define sizeOfArrayMsg 261
#include "VirtualWire.h"
#include "Arduino.h"

class Javino
{
  public:
    Javino();
    void sendMsg(String msg);
    String getMsg();
    boolean availableMsg();
	void enableRF(int pinTX, int pinRX);
	void sendMsgRF(String msg);
	void sendMsgRF(String destino, String msg);
	boolean availableMsgRF();
	void setId(String strID);
	void setAlias(String strAlias);
	String getId();
  private:
	int _x;
	int _d;
	int _n;
	char _arrayMsg[sizeOfArrayMsg];
	boolean _msg;
	String _finalMsg;
	String int2Hex(int v);
	String _me;
	void start();
	void listening();
	void timeout();
	void registrator();
	void monitorMsg();
	void abort();
	int sizeOfMsg();
	void treatMsg();
	boolean preamble();
	int hex2Int(char z[]);
	int forInt(char v);
	String char2String(char in[], int sizeIn);
	void registratorRF(char byteIn);
	void listeningRF();
	int getQtdAlias();
	String getAlias(int i);
	String int2B64(int i);
	int B64toInt(char s);
	void treatMsgRF();
	boolean preambleRF(String strHeader);
	String getMyGroup(int i);
};
#endif
