#define relayA 6
#define relayB 7

void initRelay() {
  pinMode(relayA, OUTPUT);
  digitalWrite(relayA, LOW);
  pinMode(relayB, OUTPUT);
  digitalWrite(relayB, LOW);
}

void openRelay(int relay) {
  digitalWrite(relay, LOW);
}
void closeRelay(int relay) {
  digitalWrite(relay, HIGH);
}
