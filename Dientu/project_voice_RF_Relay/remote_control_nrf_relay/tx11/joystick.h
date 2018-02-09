#define PIN_ANALOG_X 0
#define PIN_ANALOG_Y 1
#define BUTTON_UP 2
#define BUTTON_RIGHT 3
#define BUTTON_DOWN 4
#define BUTTON_LEFT 5
#define BUTTON_E 6
#define BUTTON_F 7

#define PIN_BUTTON_SELECT 8
#define PIN_JOYSTICK_UP 9
#define PIN_JOYSTICK_DOWN 10
#define PIN_JOYSTICK_LEFT 11
#define PIN_JOYSTICK_RIGHT 12
#define PIN_JOYSTICK_UPRIGHT 13
#define PIN_JOYSTICK_UPLEFT 14
#define PIN_JOYSTICK_DOWNRIGHT 15
#define PIN_JOYSTICK_DOWNLEFT 16
#define PIN_JOYSTICK_CENTER 17

#define DELAY 0

const int THRESHOLD_LOW = 200;
const int THRESHOLD_HIGH = 500;    
int x_position;
int y_position;
int x_direction;
int y_direction;
int pressedbutton;

void showPressedButton(int button) {
  switch(button) {
    case BUTTON_UP:
      Serial.println("BUTTON_UP");
      break;
    case BUTTON_RIGHT:
      Serial.println("BUTTON_RIGHT");
      break;
    case BUTTON_DOWN:
      Serial.println("BUTTON_DOWN");
      break;
    case BUTTON_LEFT:
      Serial.println("BUTTON_LEFT");
      break;
    case BUTTON_E:
      Serial.println("BUTTON_E");
      break;
    case BUTTON_F:
      Serial.println("BUTTON_F");
      break;
    case PIN_BUTTON_SELECT:
      Serial.println("PIN_BUTTON_SELECT");
      break;      
    case PIN_JOYSTICK_UP:
      Serial.println("PIN_JOYSTICK_UP");
      break;
    case PIN_JOYSTICK_DOWN:
      Serial.println("PIN_JOYSTICK_DOWN");
      break;
    case PIN_JOYSTICK_LEFT:
      Serial.println("PIN_JOYSTICK_LEFT");   
      break;
    case PIN_JOYSTICK_RIGHT:
      Serial.println("PIN_JOYSTICK_RIGHT");
      break;
    case PIN_JOYSTICK_UPRIGHT:
      Serial.println("PIN_JOYSTICK_UPRIGHT");
      break;
    case PIN_JOYSTICK_UPLEFT:
      Serial.println("PIN_JOYSTICK_UPLEFT");
      break;
    case PIN_JOYSTICK_DOWNLEFT:
      Serial.println("PIN_JOYSTICK_DOWNLEFT");
      break;
    case PIN_JOYSTICK_DOWNRIGHT:
      Serial.println("PIN_JOYSTICK_DOWNRIGHT");
      break;      
    case PIN_JOYSTICK_CENTER:
      Serial.println("PIN_JOYSTICK_CENTER");
      break; 
    default: break;      
  }
}

void initJoystick() {
 Serial.begin(9600);
 pinMode(BUTTON_UP, INPUT);
 digitalWrite(BUTTON_UP, HIGH);
 pinMode(BUTTON_RIGHT, INPUT);
 digitalWrite(BUTTON_RIGHT, HIGH);
 pinMode(BUTTON_DOWN, INPUT);
 digitalWrite(BUTTON_DOWN, HIGH);
 pinMode(BUTTON_LEFT, INPUT);
 digitalWrite(BUTTON_LEFT, HIGH);
 pinMode(BUTTON_E, INPUT);
 digitalWrite(BUTTON_E, HIGH);
 pinMode(BUTTON_F, INPUT);
 digitalWrite(BUTTON_F, HIGH);
 pinMode(PIN_BUTTON_SELECT, INPUT);
 digitalWrite(PIN_BUTTON_SELECT, HIGH);
}

void showPressedButton() {
  if (x_direction == -1) {
      if (y_direction == -1) {
        Serial.println("left-down");
        pressedbutton = PIN_JOYSTICK_DOWNLEFT;
      } else if (y_direction == 0) {
        Serial.println("left");
        pressedbutton = PIN_JOYSTICK_LEFT;
      } else {
        // y_direction == 1
        Serial.println("left-up");
        pressedbutton = PIN_JOYSTICK_UPLEFT;
      }
  } else if (x_direction == 0) {
      if (y_direction == -1) {
        Serial.println("down");
        pressedbutton = PIN_JOYSTICK_DOWN;
      } else if (y_direction == 0) {
        Serial.println("centered");
        pressedbutton = PIN_JOYSTICK_CENTER;
      } else {
        // y_direction == 1
        Serial.println("up");
        pressedbutton = PIN_JOYSTICK_UP;
      }
  } else {
      // x_direction == 1
      if (y_direction == -1) {
        Serial.println("right-down");
        pressedbutton = PIN_JOYSTICK_DOWNRIGHT;
      } else if (y_direction == 0) {
        Serial.println("right");
        pressedbutton = PIN_JOYSTICK_RIGHT;
      } else {
        // y_direction == 1
        Serial.println("right-up");
        pressedbutton = PIN_JOYSTICK_UPRIGHT;
      }
  }

 if(digitalRead(BUTTON_UP) == LOW) {
   Serial.println("Button A is pressed");
   pressedbutton = BUTTON_UP;
 }
 else if(digitalRead(BUTTON_RIGHT) == LOW) {
   Serial.println("Button B is pressed");
   pressedbutton = BUTTON_RIGHT;
 }
 else if(digitalRead(BUTTON_DOWN) == LOW) {
   Serial.println("Button C is pressed");
   pressedbutton = BUTTON_DOWN;
 }
 else if(digitalRead(BUTTON_LEFT) == LOW) {
   Serial.println("Button D is pressed");
   pressedbutton = BUTTON_LEFT;
 }
 else if(digitalRead(BUTTON_E) == LOW) {
   Serial.println("Button E is pressed");
   pressedbutton = BUTTON_E;
 }
 else if(digitalRead(BUTTON_F) == LOW) {
   Serial.println("Button F is pressed");
   pressedbutton = BUTTON_F;
 }
 else if(digitalRead(PIN_BUTTON_SELECT) == LOW) {
   Serial.println("Button SELECT is pressed");
   pressedbutton = PIN_BUTTON_SELECT;
 } 

}
void pool_Buttons() {
 x_direction = 0;
 y_direction = 0;
 x_position = analogRead(PIN_ANALOG_X);
 y_position = analogRead(PIN_ANALOG_Y);
// Serial.println(analogRead(PIN_ANALOG_X));
// Serial.println(analogRead(PIN_ANALOG_Y));
// delay(DELAY);
  if (x_position > THRESHOLD_HIGH) {
    x_direction = 1;
  } else if (x_position < THRESHOLD_LOW) {
    x_direction = -1;
  }
  if (y_position > THRESHOLD_HIGH) {
    y_direction = 1;
  } else if (y_position < THRESHOLD_LOW) {
    y_direction = -1;
  }
  showPressedButton();
  delay(DELAY);
}
