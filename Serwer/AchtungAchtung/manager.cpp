#include "manager.h"
#include <stdio.h>
#include <string.h>
#include <random>
#include <time.h>
#include <iostream>
#include <math.h>


manager::manager()
{
    wstawGraczy();
    area.resize( 1000 , vector<bool>( 600 , false ) );
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
        Player * p = new Player(i, "Player "+ to_string(i+1), rand()%360);
        playersTab.emplace_back(p);
        p->setX(random()% 800+100);
        p->setY(random()% 400+100);

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

int manager::getPlayerAngle(int i)
{
    return playersTab[i]->getAngle();
}

bool manager::getPlayerOut(int i)
{
    return playersTab[i]->getOut();
}

bool manager::playerOfClient(int id, int i)
{
    bool answer = false;

    if(klienci.at(id) == i){
        answer = true;
    }
    return answer;
}

int manager::move(int dec, Player *&p)
{

    p->setAngle(p->getAngle() + dec);
    double pi = M_PI;
    double radians = ( p->getAngle() * pi ) / 180 ;
    //double alfaRadian = Math.toRadians(radians);
    double sinAlfa = sin(radians);
    double cosAlfa = cos(radians);


    p->setOldX(p->getX());
    p->setOldY(p->getY());

    bool error = false;
    bool errorx = p->setX((int) (p->getKrok() * sinAlfa)+p->getX());
    bool errory = p->setY((int) (p->getKrok() * cosAlfa)+p->getY());
    bool errorpos = points(p->getOldX(), p->getOldY(), p->getX(), p->getY());

    p->setOut(error+errorx+errory+errorpos);

    //printf("error no. %d%d%d\n", errorx,errory,errorpos);
    return error+errorx+errory+errorpos;
}

int manager::movePlayer(int dec, int i)
{
    if (!playersTab.at(i)->getOut())
        return move(dec, playersTab.at(i));

    else return -1;
}



bool manager::column(int X, int oldY, int Y, vector<vector<bool> > &pole)
{
    bool collision = false;
    for (int i = oldY; i < Y ; i++){
        if (pole.at(X).at(i)) collision = true;
        pole.at(X).at(i) = true;
        printf("%d,%d - wynik %d\n", X, i, collision);
    }


    return collision;
}

bool manager::columnplus(int X, int oldY, int Y, vector<vector<bool> > &pole)
{
    bool collision = false;
    for (int i = oldY; i <= Y ; i++){
        if (pole.at(X).at(i)) collision = true;
        pole.at(X).at(i) = true;
        printf("%d,%d - wynik %d\n", X, i, collision);

    }


    return collision;
}

bool manager::columnminus(int X, int oldY, int Y, vector<vector<bool> > &pole)
{
    bool collision = false;
    for (int i = oldY; i >= Y; i--) {
        if (pole.at(X).at(i)) collision = true;
        pole.at(X).at(i) = true;
       // printf("%d,%d - wynik %d\n", X, i, collision);

    }


    return collision;
}

bool manager::points(int oldX, int oldY, int X, int Y)
{

    points(oldX, oldY,X, Y, area);
}

bool manager::points(int oldX, int oldY, int X, int Y, vector<vector<bool> > &ar)
{
    bool zmianaWezla = true;
    bool fail = false;
    double yy =0;
    int rozstaw = 1;
    int wx = X - oldX;
    int wy = Y - oldY;
    int currx = 0;
    int curry = 0;
    double w = (double) wy / (double) wx;
    double b = oldY - w * oldX;
    double alfa= atan(w);
    int p= round(sin(alfa)*rozstaw);
    int q= round(cos(alfa)*rozstaw);

    p = q =0;
    int lastY = oldY;
    cout << "x: " << X << " y: " << Y <<endl;
    if (oldX == X) {
        if (wy > 0) {
            fail += columnplus(X, oldY, Y,ar);

        }
        else {
            fail += columnminus(X, oldY, Y,ar);

        }
    }
    else {

        if (wx > 0) {
            for (int ii = oldX; ii <= X; ii++) {

                yy = (double)(ii + p) * w + q + b;

                if (wy > 0) {
                    //if (ii != currx && (int) yy != curry)
                        fail += columnplus(ii, lastY, (int)yy, ar);
                    currx = ii;
                    curry = (int)yy;

                }
                else {
                    //if (ii != currx && (int) yy != curry)
                        fail +=columnminus(ii, lastY, (int)yy, ar);
                    currx = ii;
                    curry = (int)yy;

                }

                lastY = (int)yy;
            }

        }
        else {

            for (int ii = oldX; ii >= X; ii--) {

                yy = (double)(ii - p) * w - q + b;
                if (wy > 0) {
                    //if (ii != currx && (int) yy != curry)
                        fail +=columnplus(ii, lastY, (int)yy,ar);
                    currx = ii;
                    curry = (int)yy;

                }
                else {
                    //if (ii != currx && (int) yy != curry)
                        fail +=columnminus(ii, lastY, (int)yy,ar);
                    currx = ii;
                    curry = (int)yy;

                }
                lastY = (int)yy;
            }
        }

    //ar.at(X).at(Y) = false;
    }
    return fail;
}



int manager::setKlient(int id, int i)
{
    setKlient(id, i, klienci);
}

void manager::playerLose(int i)
{
    playerLose(i, playersTab);
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
    }else{
        i = 0;
    }
    return 1;
}

vector<int> manager::getKlienci() const
{
    return klienci;
}

void manager::setKlienci(const vector<int> &value)
{
    klienci = value;
}


bool manager::reservePositions(int id, int formerID, vector<bool> & res, int klient)
{
    bool success = false;

    if (res[id] == true) {

    }else{

        setKlient(id, klient);
        res[id]  = true;

        success = true;
    }

    if (formerID >= 0) res[formerID] = false;

    return success;
}

int manager::setKlient(int id, int i, vector<int>  & kl)
{
    kl.at(id) = i;
}

void manager::playerLose(int i, vector<Player *> pl)
{
    cout << "Wywolane 6 razy? Gracz: " << i << endl;
    for (int x = 0; x < 6; x++){
        if (x != i){
            Player * p = pl[x];
            if (!p->getOut())   p->setPoints(p->getPoints()+1);
        }
    }
}


