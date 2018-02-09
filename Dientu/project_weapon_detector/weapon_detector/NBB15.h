#define NBB15 6

void initNBB15() {
  pinMode(NBB15, INPUT);  
}

boolean detectMetal() {
  return !digitalRead(NBB15);
}



