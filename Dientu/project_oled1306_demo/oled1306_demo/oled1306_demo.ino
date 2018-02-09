#include <Arduino.h>
#include <U8x8lib.h>

U8X8_SSD1306_128X64_NONAME_SW_I2C u8x8(/* clock=2*/ A5, /* data=0*/ A4, /* reset=*/ U8X8_PIN_NONE);
void setup(void) {  
  u8x8.begin();
  u8x8.setPowerSave(0);   
}

void pre(void)
{
  u8x8.setFont(u8x8_font_amstrad_cpc_extended_f);    
  u8x8.clear();

  u8x8.inverse();
  u8x8.print(" U8x8 Library ");
  u8x8.setFont(u8x8_font_chroma48medium8_r);  
  u8x8.noInverse();
  u8x8.setCursor(0,1);
}

void draw_bar(uint8_t c, uint8_t is_inverse)
{  
  uint8_t r;
  u8x8.setInverseFont(is_inverse);
  for( r = 0; r < u8x8.getRows(); r++ )
  {
    u8x8.setCursor(c, r);
    u8x8.print(" ");
  }
}

void draw_ascii_row(uint8_t r, int start)
{
  int a;
  uint8_t c;
  for( c = 0; c < u8x8.getCols(); c++ )
  {
    u8x8.setCursor(c,r);
    a = start + c;
    if ( a <= 255 )
      u8x8.write(a);
  }
}
void oledstring() {
  u8x8.setFont(u8x8_font_chroma48medium8_r);
  u8x8.drawString(20,20,"Thanh Nhut");
  u8x8.clear();

  u8x8.print("Tile size:");
  u8x8.print((int)u8x8.getCols());
  u8x8.print("x");
  u8x8.print((int)u8x8.getRows());
  u8x8.drawUTF8(20, 20, "Thành Nhựt");
}

void oleddraw() {
//  for(int c = 5; c < 20; c++) draw_bar(c, 20);
//  u8x8.drawFrame(2,50, 20,10);
  u8x8.drawGlyph(2,2,18);
}

void loop() {
  oledstring();
  oleddraw();
}
