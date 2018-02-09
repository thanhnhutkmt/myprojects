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
#include "relayCar.h"

void setup() 
{
  Serial.begin(9600);
  initNRF24L01();
  Serial.println("*****************RX_Mode start******************************R");
  RX_Mode();                        // set RX mode
  initRelay();
  initCar();  
}

int lightcolor = 0;
void loop() 
{
  getDataNRF24L01();
  int cmd = rx_buf[14];
  showPressedButton(cmd);
  Serial.println(cmd);
  if (cmd == PIN_JOYSTICK_UP) {
    straightrightCar();
  } 
  if (cmd == PIN_JOYSTICK_LEFT) {
    turnleftCar();
  }
  if (cmd == PIN_JOYSTICK_RIGHT) {
    turnrightCar();
  }  
  if (cmd == PIN_JOYSTICK_UPRIGHT) {
    turnright45Car(); 
  } 
  if (cmd == PIN_JOYSTICK_UPLEFT) {
    turnleft45Car(); 
  } 
  if (cmd == PIN_JOYSTICK_DOWN) {
      turnoverCar();
  }
  if (cmd == PIN_JOYSTICK_CENTER) {
    stopCar(); 
  } 
  if (cmd == BUTTON_RIGHT) {
    hornSound();
    Serial.println("buzzer");
  }

  if (cmd == BUTTON_LEFT) lightcolor++;
  if (lightcolor == 8) lightcolor = 0;
  carlightup(lightcolor);
  delay(500);
}


