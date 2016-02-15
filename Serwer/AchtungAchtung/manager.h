#ifndef MANAGER_H
#define MANAGER_H

#include <string>
#include <vector>
#include "player.h"
#include <string>
using namespace std;
class manager
{
public:
    manager();

    bool reservePosition(int id, int formerID);

    string stateBefore();
    bool getGameStarted();
    void setGameStarted(bool value);

    vector<bool> getReserved() const;
    void setReserved(const vector<bool> &value);

    vector<Player *> getPlayersTab() const;
    void setPlayersTab(const vector<Player *> &value);

    vector<string> getImiona();
    void wstawGraczy();
    string getNamePlayer(int i);

    int getTimeToGo() const;
    void setTimeToGo(int value);

    vector<bool> getReady() const;
    void setReady(const vector<bool> &value);

private:
    vector<Player *> playersTab;
    vector <bool> reserved = {false, false, false, false,false,false};
    vector <bool> ready = {false, false, false, false,false,false};
    bool gameStarted = false;
    int timeToGo = 10;
    bool reservePositions(int id, int formerID, vector <bool> & res);


};

#endif // MANAGER_H
