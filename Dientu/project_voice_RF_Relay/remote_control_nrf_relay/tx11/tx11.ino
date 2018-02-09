/*********************************************************************
Author : Thanh Nhut
Date : 22 Mar 2017

This board send by NRF24L01 button pressed by user to another board
*********************************************************************/

#include "NRF24L01.h"
#include "joystick.h"
#include "nrf24L01_program.h"

void setup() 
{
  Serial.begin(9600);
  initNRF24L01();
  Serial.println("*******************TX_Mode Start****************************");
  TX_Mode();                       // set TX mode
  initJoystick();
  tx_buf[0] = 'L';//k++;
  tx_buf[1] = 'u';//k++;
  tx_buf[2] = 'u';//k++;
  tx_buf[3] = ' ';//k++;
  tx_buf[4] = 'T';//k++;
  tx_buf[5] = 'h';//k++;
  tx_buf[6] = 'a';//k++;
  tx_buf[7] = 'n';//k++;
  tx_buf[8] = 'h';//k++;
  tx_buf[9] = ' ';//k++;
  tx_buf[10] = 'N';//k++;
  tx_buf[11] = 'h';//k++;
  tx_buf[12] = 'u';//k++;
  tx_buf[13] = 't';//k++;
  for (int i = 15; i < 32; i++) tx_buf[i] = 0;
}
void loop() 
{         
  pool_Buttons();
  tx_buf[14] = pressedbutton;
  sendDataNRF24L01(tx_buf);
}
