#include "MAX30100.h"
#include <Wire.h>

MAX30100 sensor;

void setup() {
  Wire.begin();
  Serial.begin(9600);
  while(!Serial);
//  Serial.println("initial");

  sensor.begin(pw1600, i50, sr100 );
}

void loop() {
//  Serial.println("-------");
  sensor.readSensor();
  Serial.println(meanDiff(sensor.IR));
//  Serial.println(meanDiff(sensor.RED));
//  Serial.println("-------");
  delay(10);
}

long meanDiff(int M) {
  #define LM_SIZE 15
  static int LM[LM_SIZE];      // LastMeasurements
  static byte index = 0;
  static long sum = 0;
  static byte count = 0;
  long avg = 0;

  // keep sum updated to improve speed.
  sum -= LM[index];
  LM[index] = M;
  sum += LM[index];
  index++;
  index = index % LM_SIZE;
  if (count < LM_SIZE) count++;

  avg = sum / count;
  return avg - M;
}
