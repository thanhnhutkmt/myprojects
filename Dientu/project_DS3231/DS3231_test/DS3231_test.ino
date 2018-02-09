// CONNECTIONS:
// DS3231 SDA --> SDA
// DS3231 SCL --> SCL
// DS3231 VCC --> 3.3v or 5v
// DS3231 GND --> GND
#include <avr/pgmspace.h>
#include <Wire.h> // must be included here so that Arduino library object file references work
#include <RtcDS3231.h>
RtcDS3231<TwoWire> Rtc(Wire);

void setup () 
{
    Serial.begin(9600);

    Rtc.Begin();
    Rtc.Enable32kHzPin(false);
    Rtc.SetSquareWavePin(DS3231SquareWavePin_ModeNone); 
    // Wire.begin(0, 2); // due to limited pins, use pin 0 and 2 for SDA, SCL
    Rtc.SetDateTime(RtcDateTime(__DATE__, __TIME__));
    if (!Rtc.GetIsRunning()) {
        Serial.println("RTC was not actively running, starting now");
        Rtc.SetIsRunning(true);
    }
    RtcDateTime now = Rtc.GetDateTime();
}

void loop () 
{
    if (!Rtc.IsDateTimeValid()) 
    {
        // Common Cuases:
        //    1) the battery on the device is low or even missing and the power line was disconnected
        Serial.println("RTC lost confidence in the DateTime!");
    }

    RtcDateTime now = Rtc.GetDateTime();
    printDateTime(now);
    Serial.println();

    RtcTemperature temp = Rtc.GetTemperature();
    Serial.print(temp.AsFloat());
    Serial.println("C");

    delay(10000); // ten seconds
}

#define countof(a) (sizeof(a) / sizeof(a[0]))

void printDateTime(const RtcDateTime& dt)
{
    char datestring[20];

    snprintf_P(datestring, 
            countof(datestring),
            PSTR("%02u/%02u/%04u %02u:%02u:%02u"),
            dt.Month(),
            dt.Day(),
            dt.Year(),
            dt.Hour(),
            dt.Minute(),
            dt.Second() );
    Serial.print(datestring);
}

