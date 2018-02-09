/*
 * Mandelbrot.h
 *
 *  Created on: Mar 14, 2017
 *      Author: Nhut
 */

#ifndef MANDELBROT_H_
#define MANDELBROT_H_

namespace caveofprogramming {

class Mandelbrot {
public:
	static const int MAX_ITERATIONS = 1000;

public:
	Mandelbrot();
	virtual ~Mandelbrot();

	static int getIterations(double x, double y);
};

} /* namespace caveofprogramming */

#endif /* MANDELBROT_H_ */
