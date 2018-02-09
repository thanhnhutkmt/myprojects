 #include <string>
 #include <cstdint>
 #include <memory>
 #include <vector>
 #include <math.h>
 #include "Zoom.h"
 #include "Mandelbrot.h"
 #include "Bitmap.h"
 #include "ZoomList.h"
 #include "RGB.h"
 
 using namespace std;
 
 namespace caveofprogramming {
 	class FractalCreator {
 		private:
 			int m_width;
 			int m_height;
 			unique_ptr<int[]> m_histogram;
 			unique_ptr<int[]> m_fractal;
 			Bitmap m_bitmap;
 			ZoomList m_zoomList;
 			int m_total{0};
 			vector<int> m_ranges;
 			vector<RGB> m_colors;
 			vector<int> m_rangeTotals;
 			bool m_bGotFirstRange{false};
 			
 		public:
		 	FractalCreator(int width, int height);		
 			virtual ~FractalCreator();
 			void addRange(double rangeEnd, const RGB& rgb);
 			void calculateIteration();
 			void calculateTotalIteration();
 			void calculateRangeTotals();
 			void drawFratal();
 			void addZoom(const Zoom& zoom);
 			void writeBitmap(string name);
 			int getRange(int iterations) const;
 			void run(string name);
 	};
 }
