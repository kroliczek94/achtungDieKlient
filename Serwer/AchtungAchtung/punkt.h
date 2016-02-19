#ifndef PUNKT_H
#define PUNKT_H
#include <time.h>
#include <random>

using namespace std;

class Punkt
{
public:
    Punkt();
    int getX() const;
    void setX(int value);

    int getY() const;
    void setY(int value);

    void losujPozycje();
private :
    int x = 0;
    int y = 0;
    int maxX = 800;
    int maxY = 800;
};

#endif // PUNKT_H
