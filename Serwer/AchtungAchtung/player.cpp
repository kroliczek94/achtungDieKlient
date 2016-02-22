#include "player.h"

#include <string>

using namespace std;

Player::Player(int id, string name, int angle)
{
    this->id = id;
    this->name=  name;
    this->angle = angle;

}

Player::~Player()
{

}

int Player::getPoints() const
{
    return points;
}

void Player::setPoints(int value)
{
    points = value;
}

int Player::getX() const
{
    return x;
}

bool Player::setX(int value)
{
    bool exit = false;

    if (value > 0 && value < 1000) x = value;
    else exit = true;

    return exit;
}


int Player::getY() const
{
    return y;
}

bool Player::setY(int value)
{
    bool exit = false;

    if (value > 0 && value < 600) y = value;
    else exit = true;

    return exit;
}

int Player::getAngle() const
{
    return angle;
}

void Player::setAngle(int value)
{
    angle = value;
}

int Player::getOldY() const
{
    return oldY;
}

void Player::setOldY(int value)
{
    oldY = value;
}

int Player::getOldX() const
{
    return oldX;
}

void Player::setOldX(int value)
{
    oldX = value;
}

int Player::getKrok() const
{
    return krok;
}

void Player::setKrok(int value)
{
    krok = value;
}

bool Player::getOut() const
{
    return out;
}

void Player::setOut(bool value)
{
    out = value;
}



string Player::getName() const
{
    return name;
}

void Player::setName(const string &value)
{
    name = value;


}

int Player::getId() const
{
    return id;
}

void Player::setId(int value)
{
    id = value;
}

