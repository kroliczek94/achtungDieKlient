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

    vector<bool> getReady() const;
    void setReady(const vector<bool> &value);

    void readyPlayer(int id);
    void readyPlayer(int id, vector<bool> rdy);

    int letsStart();
    map<int, int> getKlienci() const;
    void setKlienci(const map<int, int> &value);
  //  void countdown(int & timeToGo);

private:
    int letsStart(bool & gameStarted);
    map<int, int> klienci;
    vector<Player *> playersTab;
    vector <bool> reserved = {false, false, false, false,false,false};
    vector <bool> ready = {false, false, false, false,false,false};
    bool gameStarted = false;

    bool reservePositions(int id, int formerID, vector <bool> & res, int klient);


};

#endif // MANAGER_H
