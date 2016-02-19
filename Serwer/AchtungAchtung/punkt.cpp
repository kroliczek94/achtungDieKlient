#include "punkt.h"

Punkt::Punkt()
{

}

int Punkt::getX() const
{
    return x;
}

void Punkt::setX(int value)
{
    x = value;
}

int Punkt::getY() const
{
    return y;
}

void Punkt::setY(int value)
{
    y = value;
}

void Punkt::losujPozycje()
{
    srand(time(0));

    setX(random() % maxX);
    setY(random() % maxY);
}

