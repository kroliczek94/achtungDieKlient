#include "manager.h"
#include <stdio.h>
#include <string.h>
#include <random>
#include <time.h>


manager::manager()
{
    wstawGraczy();
}



bool manager::reservePosition(int id, int formerID, int klient)

{
    reservePositions(id, formerID, reserved, klient);
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

void manager::setImiePlayer(int i, string s)
{
    setImiePlayer(i, s, playersTab);
}

void manager::setImiePlayer(int i, string s, vector<Player *> &pl)
{
    pl[i]->setName(s);

}


void manager::wstawGraczy()
{
    srand(time(NULL));
    for (int i = 0 ; i < 6; i++){
        Player * p = new Player(i, "Player "+ to_string(i+1));
        playersTab.emplace_back(p);
        p->setX(random()% 700+100);
        p->setY(random()% 700+100);
        printf("%d, %d\n", p->getX(), p->getY());
    }


}

string manager::getNamePlayer(int i)
{
    return playersTab[i]->getName();
}

int manager::getPointsPlayer(int i)
{
    return playersTab[i]->getPoints();
}

int manager::getPlayerX(int i)
{
    return playersTab[i]->getX();
}

int manager::getPlayerY(int i)
{
    return playersTab[i]->getY();
}


vector<bool> manager::getReady() const
{
    return ready;
}

void manager::setReady(const vector<bool> &value)
{
    ready = value;
}

void manager::readyPlayer(int id)
{
    readyPlayer(id, ready);
}

void manager::readyPlayer(int id, vector<bool> rdy)
{
    ready[id] = true;
}

int manager::letsStart()
{
    int i = letsStart(gameStarted);
    return i;
}

map<int, int> manager::getKlienci() const
{
    return klienci;
}

void manager::setKlienci(const map<int, int> &value)
{
    klienci = value;
}

int manager::letsStart(bool &gameStarted)
{
    int i = -1;
    int x = 0;
    for (int i = 0 ; i < 6; i++){
        if (ready[i] == true)  x++;
    }
    if (x >1){
        gameStarted = true;
        i = 1;
//        thread t1([&]
//        {

//            //while(true) printf("1");
//            for (int i= 10 ; i < 0; i--){
//                setTimeToGo(0);
//                std:this_thread::sleep_for(1s);
//            }
//        });
//        t1.detach();


    }else{
        i = 0;
    }
    return 1;
}



bool manager::reservePositions(int id, int formerID, vector<bool> & res, int klient)
{
    bool success = false;

    if (res[id] == true) {

    }else{
        klienci.emplace(id, klient);
        res[id]  = true;

        success = true;
    }

    if (formerID >= 0) res[formerID] = false;

    return success;
}

//void manager::countdown(int &time)
//{



//}

