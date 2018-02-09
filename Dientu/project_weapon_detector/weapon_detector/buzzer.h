#define horn 5

int LONGSOUND = 0;
int SHORTSOUND = 1;
int MEDIUMSOUND = 2;

void initHorn() {
  pinMode(horn, OUTPUT);
  digitalWrite(horn, HIGH);
}

void hornSound(int len) {
  for(int i = 0; i<len; i++) {
    digitalWrite(horn, LOW);
    delay(10);
    digitalWrite(horn, HIGH);
    delay(10);
  }
}

void longHorn() {
  hornSound(30);
}

void mediumHorn() {
  hornSound(10);
}

void shortHorn() {
  hornSound(5);
}

