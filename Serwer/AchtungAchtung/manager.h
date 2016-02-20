#ifndef MANAGER_H
#define MANAGER_H

#include <atomic>
#include <string>
#include <vector>
#include <map>
#include "player.h"
#include <string>
#include <thread>
#include <pthread.h>
#include <iostream>

using namespace std;
class manager
{
public:
    manager();

    bool reservePosition(int id, int formerID, int klient);

    string stateBefore();
    bool getGameStarted();
    void setGameStarted(bool value);

    vector<bool> getReserved() const;
    void setReserved(const vector<bool> &value);

    vector<Player *> getPlayersTab() const;
    void setPlayersTab(const vector<Player *> &value);

    vector<string> getImiona();
    void setImiePlayer(int i, string s);
    void setImiePlayer(int i, string s, vector<Player *> &pl);
    void wstawGraczy();
    string getNamePlayer(int i);
    int getPointsPlayer(int i);
    int getPlayerX(int i);
    int getPlayerY(int i);
    int getPlayerAngle(int i);
    bool playerOfClient(int id, int i);
    int move(int dec, Player * & p);
    void movePlayer(int i);
    int setKlient(int id, int i);

    vector<bool> getReady() const;
    void setReady(const vector<bool> &value);

    void readyPlayer(int id);
    void readyPlayer(int id, vector<bool> rdy);

    int letsStart();


  //  void countdown(int & timeToGo);

    void movePlayer(int dec, int i);
    vector<int> getKlienci() const;
    void setKlienci(const vector<int> &value);

private:
    int letsStart(bool & gameStarted);
    vector <int> klienci = {-1, -1, -1, -1, -1, -1};
    vector<Player *> playersTab;
    vector <bool> reserved = {false, false, false, false,false,false};
    vector <bool> ready = {false, false, false, false,false,false};
    bool gameStarted = false;

    bool reservePositions(int id, int formerID, vector <bool> & res, int klient);
    int setKlient(int id, int i, vector<int> & kl);

};

#endif // MANAGER_H
