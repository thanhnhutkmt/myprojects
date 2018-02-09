#include "buzzer.h"
#include "NBB15.h"

void setup() {
  // put your setup code here, to run once:
  initNBB15();
  initHorn();
  mediumHorn();
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  if (detectMetal())  {
    Serial.println("Detected");
    shortHorn();
  }
}
