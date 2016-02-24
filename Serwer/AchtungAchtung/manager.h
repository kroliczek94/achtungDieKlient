#ifndef MANAGER_H
#define MANAGER_H

#include <string>
#include <iostream>
#include <string>
#include <vector>
#include <map>
#include "gamearea.h"
#include "player.h"


using namespace std;
class manager
{
public:
    manager();

    bool reservePosition(int id, int formerID, int klient);
    bool getGameStarted();
    vector<bool> getReserved() const;

    //Zestaw funkcji zwracających informacje o graczu - były problemy z vectorem
    void setImiePlayer(int i, string s);
    string getNamePlayer(int i);
    int getPointsPlayer(int i);
    int getPlayerX(int i);
    int getPlayerY(int i);
    int getPlayerAngle(int i);
    bool getPlayerOut(int i);
    bool playerOfClient(int id, int i);
    void movePlayer(int i);
    int setKlient(int id, int i);
    int playerLose(int i);

    vector<bool> getReady() const;
    void setReady(const vector<bool> &value);

    void readyPlayer(int id);

    int letsStart();
    int movePlayer(int dec, int i);

    void reset();
    void hardReset(); //reset się włącza po każdej partii, a hardReset po całości

    bool points(int oldX, int oldY, int X, int Y);
    void pauza(bool c);
    bool getPauzed() const;
    int getArea() const;
    bool twoPointsDifference(int pts, int id);
private:
    void wstawGraczy();
    void hardReset(vector <Player *>& pl, vector<bool> & res, vector<bool> & rdy, bool & gameStarted, vector<int> & klienci);
    void readyPlayer(int id, vector<bool> rdy);
    void pauza(bool c, bool & pauzed);
    void reset(vector<Player *> &pl, bool & pauza);
    void playerReset(Player * & p);
    bool reservePositions(int id, int formerID, vector <bool> & res, int klient);
    int setKlient(int id, int i, vector<int> & kl);
    int playerLose(int i, vector<Player *> pl);
    void setArea(vector<vector<bool> > &value);
    void cleanArea();

    bool columnplus(int X, int oldY, int Y, vector<vector<bool>> & pole);
    bool columnminus(int X, int oldY, int Y, vector<vector<bool>> & pole);
    bool rowplus(int Y, int oldX, int X, vector<vector<bool>> & pole);
    bool rowminus(int Y, int oldX, int X, vector<vector<bool>> & pole);
    bool points(int oldX, int oldY, int X, int Y, vector<vector<bool>> &ar);
    void setImiePlayer(int i, string s, vector<Player *> &pl);

    int move(int dec, Player * & p);
    int letsStart(bool & gameStarted);
    bool pauzed = true;
    bool gameStarted = false;
    vector <int> klienci = {-1, -1, -1, -1, -1, -1};
    vector<Player *> playersTab;
    vector <bool> reserved = {false, false, false, false,false,false};
    vector <bool> ready = {false, false, false, false,false,false};
    vector< vector< bool > > area;

};

#endif // MANAGER_H
