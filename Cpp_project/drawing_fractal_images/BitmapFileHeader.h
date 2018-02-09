/*
 * BitmapFileHeader.h
 *
 *  Created on: Mar 14, 2017
 *      Author: Nhut
 */

#ifndef BITMAPFILEHEADER_H_
#define BITMAPFILEHEADER_H_

#include <cstdint>

using namespace std;

#pragma pack(2)

namespace caveofprogramming {

struct BitmapFileHeader {
	char header[2] { 'B', 'M' };
	int32_t fileSize;
	int32_t reserved { 0 };
	int32_t dataOffset;
};

}

#endif /* BITMAPFILEHEADER_H_ */
