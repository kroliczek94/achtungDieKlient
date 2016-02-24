#ifndef PLAYER_H
#define PLAYER_H
#include "punkt.h"
#include <string>


using namespace std;
class Player
{
public:
    Player(int id, string name, int angle);
    ~Player();
    
    int getId() const;
    void setId(int value);
    
    string getName() const;
    void setName(const string &value);
    
    int getPoints() const;
    void setPoints(int value);
    
    
    
    int getX() const;
    bool setX(int value);

    
    int getY() const;
    bool setY(int value);

    
    int getAngle() const;
    void setAngle(int value);
    
    int getOldY() const;
    void setOldY(int value);
    
    int getOldX() const;
    void setOldX(int value);
    
    int getKrok() const;
    void setKrok(int value);


    bool getOut() const;
    void setOut(bool value);

private :
    
    int id;
    string name;
    int oldX = x;
    int oldY = y;
    int angle;
    
    int x = 0;
    int y = 0;
    int points = 0;
    int krok = 4;
    bool out = false;
};

#endif // PLAYER_H
