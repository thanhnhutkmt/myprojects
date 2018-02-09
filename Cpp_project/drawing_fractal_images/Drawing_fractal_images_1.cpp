//============================================================================
// Name        : Fractal.cpp
// Author      : Nhut
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include "FractalCreator.h"

using namespace std;
using namespace caveofprogramming;

int main() {	
	FractalCreator fractalCreator(800, 600);
	fractalCreator.addRange(0.0, RGB(255, 255, 0));
	fractalCreator.addRange(0.3, RGB(255, 0, 0));
	fractalCreator.addRange(0.4, RGB(255, 255, 255));
	fractalCreator.addRange(0.8, RGB(255, 0, 255));
	
	cout << fractalCreator.getRange(999) << endl;
	
	fractalCreator.addZoom(Zoom(295, 202, 0.1));
	fractalCreator.addZoom(Zoom(312, 304, 0.1));
	fractalCreator.run("test.bmp");
	cout << "Finished." << endl; // prints !!!Hello World!!!
	return 0;
}
