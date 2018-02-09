#include <nRF24L01.h>
#include <RF24.h>
#include <RF24_config.h>

#include <SPI.h>
//#include "RF24.h"

#define RFpin 10 //thay 10 thành 53 với mega
//địa chỉ 1
const uint64_t pipe[] = {0xE8E8F0F0E1LL, 0xE8E8F0F0E2LL}; // {địa chỉ 1, địa chỉ 2}
RF24 radio(9,RFpin); 
 
void setup(){ 
  Serial.begin(9600);
}
void loop(){
  byte msg[3];
  msg[0] = 1;msg[1] = 2;msg[2] = 3;
  nrf24_setup_send();
  nrf24_send(msg);
  nrf24_setup_receive();  
  nrf24_receive(3);  
}

byte* nrf24_receive(int numberOfByte) {
  int i = 0;
  byte msg [numberOfByte];
  if (radio.available()){
    radio.read(&msg, sizeof(numberOfByte));
    Serial.println("-------------------");
    for (i = 0; i < numberOfByte; i++) Serial.println(msg[i]);
    Serial.println("-------------------");
  } else Serial.println("radio error");
  return msg;
}

void nrf24_send(byte msg[]) {
  radio.write(&msg, sizeof(msg));
  Serial.println("Sent");
}

void nrf24_setup_receive() {
  radio.begin();                     
  radio.setAutoAck(1);              
  radio.setDataRate(RF24_1MBPS);    // Tốc độ dữ liệu
  radio.setChannel(10);             // Đặt kênh
  radio.openReadingPipe(1,pipe[1]);     
  radio.startListening();  
}

void nrf24_setup_send() {
  radio.begin();                     
  radio.setAutoAck(1);               
  radio.setRetries(1,1);             
  radio.setDataRate(RF24_1MBPS);      // Tốc độ truyền
  radio.setPALevel(RF24_PA_MAX);      // Dung lượng tối đa
  radio.setChannel(10);               // Đặt kênh
  radio.openWritingPipe(pipe[0]);        // mở kênh
}
