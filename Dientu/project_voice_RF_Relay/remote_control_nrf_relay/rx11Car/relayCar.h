#define redlight 2
#define greenlight 3
#define bluelight 4

void initCarLight() {
  pinMode(redlight, OUTPUT);
  digitalWrite(redlight, HIGH);
  pinMode(greenlight, OUTPUT);
  digitalWrite(greenlight, HIGH);
  pinMode(bluelight, OUTPUT);
  digitalWrite(bluelight, HIGH);
}

void lightup(boolean red, boolean green, boolean blue) {
  digitalWrite(redlight, red);
  digitalWrite(greenlight, green);
  digitalWrite(bluelight, blue);
}

#define off 0
#define blue 1
#define green 2
#define light blue 3
#define red 4
#define pink 5
#define yellow 6
#define white 7

void carlightup(int color) {
  switch(color) {
    default: lightup(true, true, true); break; // off
    case 1: lightup(true, true, false); break;  // blue
    case 2: lightup(true, false, true); break;  // green
    case 3: lightup(true, false, false); break;   // light blue
    case 4: lightup(false, true, true); break;  // red
    case 5: lightup(false, true, false); break;   // pink
    case 6: lightup(false, false, true); break;   // yellow
    case 7: lightup(false, false, false); break;    // white
  }
}

void stopCar() {
  openRelay(relayA);
  openRelay(relayB);
  carlightup(red);
}

void leftCar(int d) {
  closeRelay(relayB);  
  openRelay(relayA);
  delay(d);
  stopCar();
  carlightup(yellow); 
}

void rightCar(int d) {
  openRelay(relayB);
  closeRelay(relayA);  
  delay(d);
  stopCar();   
  carlightup(yellow);
}

void straightCar(int d) {
  closeRelay(relayA);
  closeRelay(relayB);  
  delay(d);
  stopCar(); 
  carlightup(green);
}

#define turn15   0
#define turn45   5
#define turn90   20
#define turn180  40
#define straightLine 3000

void turnleftCar() {
  rightCar(turn90);
}

void turnrightCar() {
  leftCar(turn90);
}

void turnoverCar() {
  leftCar(turn180);
}

void turnleft45Car() {
  leftCar(turn45);
}

void turnright45Car() {
  rightCar(turn45);
}

void straightleftCar() {
  leftCar(turn15);
  straightCar(straightLine);
}

void straightrightCar() {
  rightCar(turn15);
  straightCar(straightLine);    
}

#define horn 5

void initHorn() {
  pinMode(horn, OUTPUT);
  digitalWrite(horn, HIGH);
}

void hornSound() {
  for(int i = 0; i<30; i++) {
    digitalWrite(horn, LOW);
    delay(1);
    digitalWrite(horn, HIGH);
    delay(1);
  }
}

void initCar() {
  initHorn();  
  initCarLight();
  carlightup(7);
}

