#ifndef PLAYER_H
#define PLAYER_H

#include <string>

using namespace std;
class Player
{
public:
    Player(int id, string name);
    ~Player();

int getId() const;
void setId(int value);
string getName() const;
void setName(const string &value);
int getPoints() const;
void setPoints(int value);



private :

int id;
string name;
int points;

};

#endif // PLAYER_H
