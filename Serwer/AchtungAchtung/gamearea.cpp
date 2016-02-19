#include "gamearea.h"

gameArea::gameArea()
{
    gameArea(area);
}

gameArea::gameArea(vector<vector<bool>> & pole)
{
    for(int i = 0; i < 1000 ; i++){
        vector<bool> c;
        pole.emplace_back(c);
        for (int j = 0 ; j < 800 ; j++)

            pole[i].emplace_back(false);
    }


}

bool gameArea::ruch(int skadX, int skadY, int doX, int doY, vector<vector<bool>> & pole)
{

    
}

bool gameArea::ruch(int dec, Player *p)
{   
    
}

vector<vector<bool> > gameArea::getArea() const
{
    return area;
}

void gameArea::setArea(const vector<vector<bool> > &value)
{
    area = value;
}


