#include "gamearea.h"
#include "math.h"

gameArea::gameArea()
{

    //gameArea(area);
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

bool gameArea::ruch(int dec, Player* &p)
{
    p->setAngle(p->getAngle() + dec);
    double pi = M_PI;
    double radians = ( p->getAngle() * pi ) / 180 ;
    //double alfaRadian = Math.toRadians(radians);
    double sinAlfa = sin(radians);
    double cosAlfa = cos(radians);

    p->setOldX(p->getX());
    p->setOldY(p->getY());

    p->setX((int) (p->getKrok() * sinAlfa)+p->getX());
    p->setY((int) (p->getKrok() * cosAlfa)+p->getY());

}





