#ifndef GAMEAREA_H
#define GAMEAREA_H
#include "player.h"

#include <vector>

using namespace std;

class gameArea
{
public:
    gameArea();
    gameArea(vector<vector<bool>> & pole);
    bool ruch(int skadX, int skadY, int doX, int doY, bool** & pole);
    vector<vector<bool> > getArea() const;
    void setArea(const vector<vector<bool> > &value);

    bool ruch(int skadX, int skadY, int doX, int doY, vector<vector<bool> > &pole);
    bool ruch(int dec, Player * p);
private:
    //bool area[1000][800];
    vector<vector<bool>> area;

};

#endif // GAMEAREA_H
