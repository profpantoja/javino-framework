
// transmitter.pde
//
// Simple example of how to use VirtualWire to transmit messages
// Implements a simplex (one-way) transmitter with an TX-C1 module
//
// See VirtualWire.h for detailed API docs
// Author: Mike McCauley (mikem@airspayce.com)
// Copyright (C) 2008 Mike McCauley
// $Id: transmitter.pde,v 1.3 2009/03/30 00:07:24 mikem Exp $

#include <VirtualWire.h>
int msgId=0;
void setup()
{
    Serial.begin(9600);   // Debugging only
    Serial.println("setup");
    pinMode(13,OUTPUT);

    // Initialise the IO and ISR
    vw_set_tx_pin(12);
    vw_set_ptt_inverted(true); // Required for DR3100
    vw_setup(4096); // Bits per sec
    delay(550);
}

void loop()
{
  int r = random(1,10000);
    if(msgId<1024){
      String m="Número aleatorio: "+String(r);
      char msg[63];
      m.toCharArray(msg,63);
      digitalWrite(13, true); // Flash a light to show transmitting
      vw_send((uint8_t *)msg, strlen(msg));
      vw_wait_tx(); // Wait until the whole message is gone
      digitalWrite(13, false);
      delay(500); 
      msgId++;     
    }

}