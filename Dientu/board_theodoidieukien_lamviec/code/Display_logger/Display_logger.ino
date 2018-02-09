/*================================================================
 * Author      : Engineer Luu Thanh Nhut
 * Date        : 6 Nov 2016  
 * Description : Display environment status and 
 *                write log to SD card (file named "status.log")
 * All right reserved 2016.
 * ================================================================
 */

#include <SPFD5408_Adafruit_GFX.h>    // Core graphics library
#include <SPFD5408_Adafruit_TFTLCD.h> // Hardware-specific library
#include <SPFD5408_TouchScreen.h>
#include <SD.h>

// The control pins for the LCD can be assigned to any digital or
// analog pins...but we'll use the analog pins as this allows us to
// double up the pins with the touch screen (see the TFT paint example).
#define LCD_CS A3 // Chip Select goes to Analog 3
#define LCD_CD A2 // Command/Data goes to Analog 2
#define LCD_WR A1 // LCD Write goes to Analog 1
#define LCD_RD A0 // LCD Read goes to Analog 0
#define LCD_RESET A4 // Can alternately just connect to Arduino's reset pin
#define SD_CS 10  // SD Chip select
//   D0 connects to digital pin 8  (Notice these are
//   D1 connects to digital pin 9   NOT in order!)
//   D2 connects to digital pin 2
//   D3 connects to digital pin 3
//   D4 connects to digital pin 4
//   D5 connects to digital pin 5
//   D6 connects to digital pin 6
//   D7 connects to digital pin 7

#define	BLACK   0x0000
#define	BLUE    0x001F
#define	RED     0xF800
#define	GREEN   0x07E0
#define CYAN    0x07FF
#define MAGENTA 0xF81F
#define YELLOW  0xFFE0
#define WHITE   0xFFFF

Adafruit_TFTLCD tft(LCD_CS, LCD_CD, LCD_WR, LCD_RD, LCD_RESET);
File myFile;
int tem = 30;
int h = 50;
int li = 100;
boolean lc = true, ac = true, nc = true, mfc = true;

void setup(void) {
  Serial.begin(9600);  
//#ifdef USE_ADAFRUIT_SHIELD_PINOUT
//  progmemPrintln(PSTR("Using Adafruit 2.8\" TFT Arduino Shield Pinout"));
//#else
//  progmemPrintln(PSTR("Using Adafruit 2.8\" TFT Breakout Board Pinout"));
//#endif

  tft.reset(); 
  tft.begin(0x9341); // SDFP5408
  tft.setRotation(0); // Need for the Mega, please changed for your choice or rotation initia
  tft.fillScreen(BLACK);  
  tft.setTextSize(2);
  tft.println("====================");
  tft.println(" Working environment");
  tft.println("       monitor      ");
  tft.println("     ===========    ");
  tft.println("Engineer: Thanh Nhut");
  tft.println("====================");
  tft.setTextSize(1);
  tft.println("");
  tft.println("Starting ...");
//  lightMeter.begin();tft.println("Light meter intializing ...");
  initSD();
  delay(1000);  
  tft.fillScreen(BLUE);  
}

void loop(void) {  
  saveInfo();
  showForm();
  while (1) {
    process();
    delay(1000);
  }
}

void initSD() {
  pinMode(10, OUTPUT); 
  digitalWrite(10, HIGH);
  if (!SD.begin(SD_CS)) {
    tft.println("SD module failed !");
    return;
  } else tft.println("SD module initialized ..."); 
}

void saveInfo() {
  myFile = SD.open("status.log", FILE_WRITE);  
  if (myFile) {
    myFile.println("====================");
    myFile.println("");
    myFile.println("  Current env info");
    myFile.println("Temperature : ");     myFile.print("        "); myFile.print(tem); myFile.println(" C");
    myFile.println("Humidity : ");        myFile.print("        "); myFile.print(h); myFile.println(" %");
    myFile.println("Light intensity : "); myFile.print("        "); myFile.print(li); myFile.println(" lux");
    myFile.println("Light condition : "); myFile.print("        "); myFile.println((lc) ? "Ok" : "Bad");
    myFile.println("Air condition : ");   myFile.print("        "); myFile.println((ac) ? "Ok" : "Bad");
    myFile.println("Noise condition : "); myFile.print("        "); myFile.println((nc) ? "Ok" : "Bad");
    myFile.println("Mag condition :");    myFile.print("        "); myFile.println((mfc) ? "Ok" : "Bad");
    myFile.println("");
    myFile.println("====================");
  }  
  myFile.close();
}

void process() {
  int n_tem = random(30);
  int n_h = random(100);
  int n_li = random(100);
  boolean n_lc = (random(1) == 1) ? true : false;
  boolean n_ac = (random(1) == 1) ? true : false;
  boolean n_nc = (random(1) == 1) ? true : false;
  boolean n_mfc = (random(1) == 1) ? true : false;
  if (n_tem != tem || n_h != h || n_li != li 
  || n_lc != lc || n_ac != ac || n_nc != nc || n_mfc != mfc) {
    tem = n_tem;
    h = n_h;
    li = n_li;
    lc = n_lc;
    ac = n_ac;
    nc = n_nc;
    mfc = n_mfc;
    saveInfo();
    updateForm();       
  } 
}

void showForm() {
  tft.setCursor(0, 0);
  tft.setTextSize(2);
  tft.println("====================");
  tft.println("");
  tft.println("  Current env info");
  tft.println(" Temperature : ");     tft.print("        ");  tft.print(tem); tft.println(" C");
  tft.println(" Humidity : ");        tft.print("        ");  tft.print(h); tft.println(" %");
  tft.println(" Light intensity : "); tft.print("       ");   tft.print(li); tft.println(" lux");
  tft.println(" Light condition : "); tft.print("        "); tft.println((lc) ? " Ok" : "Bad");
  tft.println(" Air condition : ");   tft.print("        "); tft.println((ac) ? " Ok" : "Bad");
  tft.println(" Noise condition : "); tft.print("        "); tft.println((nc) ? " Ok" : "Bad");
  tft.println(" Mag condition :");    tft.print("        "); tft.println((mfc) ? " Ok" : "Bad");
  tft.println("");
  tft.println("====================");
}

void updateForm() {
  tft.setAddrWindow(0, 50, 240, 100);  
  for (int i = 0; i < 16; i++) tft.drawFastHLine(70, 80 + i, 100, BLACK);  
  for (int i = 0; i < 16; i++) tft.drawFastHLine(70, 111 + i, 100, BLACK);  
  for (int i = 0; i < 16; i++) tft.drawFastHLine(70, 144 + i, 100, BLACK);  
  for (int i = 0; i < 16; i++) tft.drawFastHLine(70, 175 + i, 100, BLACK);  
  for (int i = 0; i < 16; i++) tft.drawFastHLine(70, 209 + i, 100, BLACK);  
  for (int i = 0; i < 16; i++) tft.drawFastHLine(70, 240 + i, 100, BLACK);  
  for (int i = 0; i < 16; i++) tft.drawFastHLine(70, 271 + i, 100, BLACK); 
  tft.setCursor(100, 80); tft.print(tem); tft.println(" C");
  tft.setCursor(100, 111); tft.print(h);   tft.println(" %");
  tft.setCursor(80, 144); tft.print(li);  tft.println(" lux");
  tft.setCursor(100, 175); tft.println((lc) ? " Ok" : "Bad");
  tft.setCursor(100, 209); tft.println((ac) ? " Ok" : "Bad");
  tft.setCursor(100, 240); tft.println((nc) ? " Ok" : "Bad");
  tft.setCursor(100, 271); tft.println((mfc) ? " Ok" : "Bad");
}
