#include "manager.h"
#include <stdio.h>
#include <string.h>

manager::manager()
{
    wstawGraczy();
}


bool manager::reservePosition(int id, int formerID)
{
    reservePositions(id, formerID, reserved);
}

bool manager::getGameStarted()
{
    return gameStarted;
}

void manager::setGameStarted(bool value)
{
    gameStarted = value;
}

vector<bool> manager::getReserved() const
{
    return reserved;
}

void manager::setReserved(const vector<bool> &value)
{
    reserved = value;
}


void manager::wstawGraczy()
{
    for (int i = 0 ; i < 6; i++){
        Player * p = new Player(i, "Player "+ to_string(i+1));
        playersTab.emplace_back(p);
    }
}

string manager::getNamePlayer(int i)
{
    return playersTab[i]->getName();
}

int manager::getTimeToGo() const
{
    return timeToGo;
}

void manager::setTimeToGo(int value)
{
    timeToGo = value;
}

vector<bool> manager::getReady() const
{
    return ready;
}

void manager::setReady(const vector<bool> &value)
{
    ready = value;
}

bool manager::reservePositions(int id, int formerID, vector<bool> & res)
{
    bool success = false;

    if (res[id] == true) {

    }else{
        bool aa = false;
        res[id]  = true;

        success = true;
    }

    if (formerID >= 0) res[formerID] = false;

    return success;
}

