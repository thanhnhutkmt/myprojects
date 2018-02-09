#include <iostream>

using namespace std;
/* run this program using the console pauser or add your own getch, system("pause") or input loop */
class MusicalInstrument {
	public:
		virtual void play() {cout << "Playing instrument ..." << endl;}
		virtual void reset() {cout << "Resetting instrument ..." << endl;}
		virtual ~MusicalInstrument() {};
};

class Machine {
	public:
		virtual void start() {cout << "Starting machine ..." << endl;}
		virtual void reset() {cout << "Resetting machine ..." << endl;}
		virtual ~Machine() {};
};

class Synthesizer: public Machine, public MusicalInstrument {
	public:
		virtual ~Synthesizer(){};		
};

int main(int argc, char** argv) {
	Synthesizer *pSynth = new Synthesizer();
	pSynth->play();
	pSynth->start();
	pSynth->MusicalInstrument::reset();
	pSynth->Machine::reset();
	
	delete pSynth;
	return 0;
}
