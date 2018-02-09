#include <SPI.h>
#include <Adafruit_GFX.h>
#include <Adafruit_PCD8544.h>

// pin 7 - Serial clock out (SCLK)
// pin 6 - Serial data out (DIN)
// pin 5 - Data/Command select (D/C)
// pin 4 - LCD chip select (CS)
// pin 3 - LCD reset (RST)
Adafruit_PCD8544 display = Adafruit_PCD8544(7, 6, 5, 4, 3);

void setup() {
  Serial.begin(9600);
  display.begin();
  display.setContrast(50);
  display.display();
display.clearDisplay();
  for(int i = 4; i< 50; i++)
    for (int j = 4; j < 50; j++)
      display.drawPixel(10, 10, WHITE);
  display.display();
  display.println("-------------");
}

void loop() {
  // put your main code here, to run repeatedly:

}
