//*****************************************************************************
//
// MSP432 main.c template - Empty main
//
//****************************************************************************


#include <msp432p401r.h>
#include "hal_hardware_board.h"
#include "mmc.h"
#include <stdio.h>
//#include "msp.h"

unsigned long cardSize = 0;
unsigned char status = 1;
unsigned int timeout = 0;
int i = 0;

unsigned char buffer[512];

int main( void )
{
  WDTCTL = WDTPW + WDTHOLD;

  //Initialisation of the MMC/SD-card
  while (status != 0)                       // if return in not NULL an error did occur and the
                                            // MMC/SD-card will be initialized again
  {
    status = mmcInit();
    timeout++;
    if (timeout == 150)                      // Try 50 times till error
    {
      //printf ("No MMC/SD-card found!! %x\n", status);
      break;
    }
  }

  while ((mmcPing() != MMC_SUCCESS));      // Wait till card is inserted

  // Read the Card Size from the CSD Register
  cardSize =  mmcReadCardSize();

// Clear Sectors on MMC
  for (i = 0; i < 512; i++) buffer[i] = 0;
  mmcWriteSector(0, buffer);                // write a 512 Byte big block beginning at the (aligned) adress

  for (i = 0; i < 512; i++) buffer[i] = 0;
  mmcWriteSector(1, buffer);                // write a 512 Byte big block beginning at the (aligned) adress

  mmcReadSector(0, buffer);                 // read a size Byte big block beginning at the address.
  for (i = 0; i < 512; i++) if(buffer[i] != 0) P1OUT |= 0x01;

  mmcReadSector(1, buffer);                 // read a size Byte big block beginning at the address.
  for (i = 0; i < 512; i++) if(buffer[i] != 0) P1OUT |= 0x02;


// Write Data to MMC
  for (i = 0; i < 512; i++) buffer[i] = i;
  mmcWriteSector(0, buffer);                // write a 512 Byte big block beginning at the (aligned) adress

  for (i = 0; i < 512; i++) buffer[i] = i+64;
  mmcWriteSector(1, buffer);                // write a 512 Byte big block beginning at the (aligned) adress

  mmcReadSector(0, buffer);                 // read a size Byte big block beginning at the address.
  for (i = 0; i < 512; i++) if(buffer[i] != (unsigned char)i) P1OUT |= 0x04;

  mmcReadSector(1, buffer);                 // read a size Byte big block beginning at the address.
  for (i = 0; i < 512; i++) if(buffer[i] != (unsigned char)(i+64)) P1OUT |= 0x08;

  for (i = 0; i < 512; i++)
    mmcReadSector(i, buffer);               // read a size Byte big block beginning at the address.

  mmcGoIdle();                              // set MMC in Idle mode

  while (1);
}
