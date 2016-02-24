#include "manager.h"
#include <stdio.h>
#include <string.h>
#include <random>
#include <time.h>
#include <iostream>
#include <math.h>

int curry, currx = 0;

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

    double sinAlfa = sin(radians);
    double cosAlfa = cos(radians);


    p->setOldX(p->getX());
    p->setOldY(p->getY());

    bool error = false;
    bool errorx = p->setX((int) (p->getKrok() * sinAlfa)+p->getX());
    bool errory = p->setY((int) (p->getKrok() * cosAlfa)+p->getY());
    bool errorpos = points(p->getOldX(), p->getOldY(), p->getX(), p->getY());

    p->setOut(error+errorx+errory+errorpos);

    return error+errorx+errory+errorpos;
}

int manager::movePlayer(int dec, int i)
{
    if (!playersTab.at(i)->getOut())
        return move(dec, playersTab.at(i));

    else return -1;
}

bool manager::columnplus(int X, int oldY, int Y, vector<vector<bool> > &pole)
{
    bool collision = false;
    for (int i = oldY; i <= Y ; i++){
        if (pole.at(X).at(i)) collision = true;
        pole.at(X).at(i) = true;
        printf("#,%d,%d,%d\n", X, i, collision);

    }
    return collision;
}

bool manager::columnminus(int X, int oldY, int Y, vector<vector<bool> > &pole)
{
    bool collision = false;
    for (int i = oldY -1; i >= Y; i--) {
        if (pole.at(X).at(i)) collision = true;
        pole.at(X).at(i) = true;
        printf("#,%d,%d,%d\n", X, i, collision);

    }

    return collision;
}

bool manager::points(int oldX, int oldY, int X, int Y)
{
    points(oldX, oldY,X, Y, area);
}

void manager::pauza(bool c)
{
    pauza(c, pauzed);
}

bool manager::points(int oldX, int oldY, int X, int Y, vector<vector<bool> > &ar)
{
    bool zmianaWezla = true;
    bool fail = false;
    double yy =0;
    int rozstaw = 1;
    int wx = X - oldX;
    int wy = Y - oldY;

    double w = (double) wy / (double) wx;
    double b = oldY - w * oldX;
//    double alfa= atan(w);
//    int p= round(sin(alfa)*rozstaw);
//    int q= round(cos(alfa)*rozstaw);

//    p = q =0;
    int lastY = oldY;
    //cout << "x: " << X << " y: " << Y <<endl;
    if (oldX == X) {
        if (wy > 0) {

            fail += columnplus(X, oldY+1, Y,ar);

        }
        else {

            fail += columnminus(X, oldY-1, Y,ar);

        }
        zmianaWezla = false;
    }
    else {

        if (wx > 0) {
            for (int ii = oldX; ii <= X; ii++) {

                yy = (double)ii * w + b;
                //cout << ii << " : " << yy << endl;

                if (wy > 0) {
                    if (!zmianaWezla && wy != 0)
                    fail += columnplus(ii, lastY, round(yy), ar);

                }
                else {
                    if (!zmianaWezla && wy != 0)
                    fail +=columnminus(ii, lastY, round(yy), ar);


                }
                lastY = round(yy);
                zmianaWezla = false;
            }

        }
        else {

            for (int ii = oldX; ii >= X; ii--) {

                yy = (double)ii * w + b;

                if (wy > 0) {
                    if (!zmianaWezla && wy != 0)
                    fail +=columnplus(ii, lastY, round(yy),ar);


                }
                else {
                    if (!zmianaWezla && wy != 0)
                    fail +=columnminus(ii, lastY, round(yy),ar);


                }
                lastY = round(yy);
                zmianaWezla = false;
            }
        }
    }
    return fail;
}



int manager::setKlient(int id, int i)
{
    setKlient(id, i, klienci);
}

int manager::playerLose(int i)
{

    return playerLose(i, playersTab);
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

int manager::getArea() const
{
    int n = 0;
    for (int i = 0; i < 1000 ; i++){
        for (int j = 0; i < 600; i++){
            if (area.at(i).at(j) == true) n++;

        }
    }
    return n;
}

void manager::setArea(vector<vector<bool> > &value)
{
    for (int i = 0; i < 1000; i++){
        for (int j = 0; j < 600; j++){
            value[i][j] = false;
        }
    }
}

void manager::cleanArea()
{
    setArea(area);
}

bool manager::twoPointsDifference(int pts, int id)
{   bool status = true;
    int cel = 0;

    for (int i = 0; i < 6; i++){
        if (i == id || !ready[i]) continue;

        if (ready[i] == true) cel++;
        if ((pts - 2) < getPointsPlayer(i))
        {
            status = false;
            cout << endl << "Owszem " << pts << " " <<getPointsPlayer(i) << " " << id << " " << i;
        }
    }

    cout << cel << " /status :  " << status <<  " " << 10 * (cel -1 ) << endl;
    if (pts < 20) status = false;
    return status;
}

void manager::hardReset(vector<Player *> &pl, vector<bool> &res, vector<bool> &rdy, bool & gameStarted, vector<int> &klienci)
{
     srand (time (NULL));
    for (int i = 0; i < 6; i++){
        klienci[i] = -1;
        res[i] = false;
        rdy[i] = false;

        Player * p = pl[i];
        p->setAngle(rand()%360);
        p->setOut(false);
        p->setX(random()% 800+100);
        p->setY(random()% 400+100);
        p->setName("Player " + to_string(i+1));
        p->setPoints(0);


    }
    gameStarted = false;
}



bool manager::getPauzed() const
{
    return pauzed;
}

void manager::setPauzed(bool value)
{
    pauzed = value;
}

void manager::pauza(bool c, bool &pauzed)
{

    pauzed = c;
}

bool manager::getRestart() const
{
    return restart;
}

void manager::setRestart(bool value)
{
    restart = value;
}


vector<int> manager::getKlienci() const
{
    return klienci;
}

void manager::setKlienci(const vector<int> &value)
{
    klienci = value;
}

void manager::reset()
{
    setArea(area);
    reset(playersTab, pauzed);
}

void manager::hardReset()
{
    hardReset(playersTab, reserved, ready, gameStarted, klienci);
}

void manager::playerReset(Player *&p)
{
    srand(time(NULL));
    p->setX(random()% 800+100);
    p->setY(random()% 400+100);
    p->setAngle(random()% 360);
    p->setOut(false);


}

void manager::reset(vector<Player *> & pl, bool &pauza)
{
    srand (time (NULL));

    for (int x = 0 ; x < 6 ; x++){
        Player * p = pl[x];
        p->setAngle(rand()%360);
        p->setOut(false);
        p->setX(random()% 800+100);
        p->setY(random()% 400+100);

    }
    pauza = true;
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

int manager::playerLose(int i, vector<Player *> pl)
{
    int n = -1;
    for (int x = 0; x < 6; x++){
        if (x != i){
            Player * p = pl[x];
            if (!p->getOut())
            {
                p->setPoints(p->getPoints()+1);
                n++;
            }
        }
    }
    cout <<"Pozostalo " << n << endl;
    return n;
}
