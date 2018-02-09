/*********************************************************************
Author : Thanh Nhut
Date : 22 Mar 2017

This board receive button command from joystick of another adruino
by nrf24L01 and close or open relay
*********************************************************************/

#include "NRF24L01.h"
#include "joystick.h"
#include "relay.h"
#include "nrf24L01_program.h"

void setup() 
{
  Serial.begin(9600);
  initNRF24L01();
  Serial.println("*****************RX_Mode start******************************R");
  RX_Mode();                        // set RX mode
  initRelay();
}

void loop() 
{
  getDataNRF24L01();
  int cmd = rx_buf[14];
  showPressedButton(cmd);
  Serial.println(cmd);
  if (cmd == BUTTON_UP) {
    closeRelay(relayA);
    closeRelay(relayB);
  } 
  if (cmd == BUTTON_DOWN) {
    openRelay(relayA);
    openRelay(relayB);
  }
}


