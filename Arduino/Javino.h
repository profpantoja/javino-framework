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

#include "Arduino.h"

class Javino
{
  public:
    Javino();
    void sendMsg(String msg);
    String getMsg();
    boolean availableMsg();
  private:
	int _x;
	int _d;
	int _n;
	char _arrayMsg[sizeOfArrayMsg];
	boolean _msg;
	String _finalMsg;
	String int2Hex(int v);
	void start();
	void listening();
	void timeout();
	void register();
	void monitorMsg();
	void abort();
	int sizeOfMsg();
	void treateMsg();
	boolean preamble();
	int hex2Int(char z[]);
	int forInt(char v);
	String char2String(char in[], int sizeIn);
};

#endif
