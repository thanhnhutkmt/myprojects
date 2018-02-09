void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}
// Vs : 5V, GND : GND
int LM35 = 0; // A0 for LM35 Vout

void loop() {
  // put your main code here, to run repeatedly:
    float temp = analogRead(LM35) * 0.488;
    Serial.print("Temperature : ");
    Serial.println(temp);
    delay(1000);
}
