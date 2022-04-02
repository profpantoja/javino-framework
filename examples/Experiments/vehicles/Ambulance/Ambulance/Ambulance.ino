//#include <Ultrasonic.h>
#include <VirtualWire.h>

#define PinLDR      A0
#define PinBuzzer   28
#define PinPLight   26
#define PinBreakL   24
#define PinLight    30

#define PinBridH1   7
#define PinBridH2   8
#define PinBridH3   9
#define PinBridH4   10

#define TXPin       12                /*Pino Transmissor*/
#define RXPin       11                  /*Pino Receptor*/


//Ultrasonic ultrasonic(PinTrig, PinEcho);

String m;
void setup() {
Serial.begin(9600);  // Debugging only
    Serial.println("setup");
    pinMode(13,OUTPUT);
    // Initialise the IO and ISR
    vw_set_rx_pin(11);
    //vw_set_ptt_inverted(true); // Required for DR3100
    vw_setup(4096); // Bits per sec

    vw_rx_start();       // Start the receiver PLL running
   


  Serial.begin(9600);
  pinMode(PinLight,   OUTPUT);
  pinMode(PinBreakL,  OUTPUT);
  pinMode(PinBuzzer,  OUTPUT);
  pinMode(PinPLight,  OUTPUT);
  pinMode(PinBridH1,  OUTPUT);
  pinMode(PinBridH2,  OUTPUT);
  pinMode(PinBridH3,  OUTPUT);
  pinMode(PinBridH4,  OUTPUT);
  pinMode(PinLDR,     INPUT);

}

void loop() {



  m="";
    uint8_t buf[VW_MAX_MESSAGE_LEN];
    uint8_t buflen = VW_MAX_MESSAGE_LEN;

    if (vw_get_message(buf, &buflen)) // Non-blocking
    {
  int i;  
  for (i = 0; i < buflen; i++)
  {
     m=m+String((char)buf[i]);
  }
  Serial.println(m);
        digitalWrite(13, false);
    }

  
  delay(1000);
  digitalWrite(PinBuzzer, HIGH);
  digitalWrite(PinLight,  HIGH);
  digitalWrite(PinBreakL, HIGH);
  digitalWrite(PinPLight, HIGH);
  
 // float cmMsec;
//  long microsec = ultrasonic.timing();
 // cmMsec = ultrasonic.convert(microsec, Ultrasonic::CM);
  //Exibe informacoes no serial monitor
 // Serial.print("Distancia em cm: ");
 // Serial.println(cmMsec);

  Serial.print("Luminosidade: ");
  Serial.println(analogRead(PinLDR));




  
  digitalWrite(PinBridH1, HIGH);
  digitalWrite(PinBridH2,  LOW);
  digitalWrite(PinBridH3, HIGH);
  digitalWrite(PinBridH4, LOW);

  
  delay(100);
  digitalWrite(PinBuzzer, LOW);
  digitalWrite(PinLight,  LOW);
  digitalWrite(PinBreakL, LOW);
  digitalWrite(PinPLight, LOW);
  delay(400);  
  digitalWrite(PinBridH1, LOW);
  digitalWrite(PinBridH2, LOW);
  digitalWrite(PinBridH3, LOW);
  digitalWrite(PinBridH4, LOW);
  // put your main code here, to run repeatedly:


}
