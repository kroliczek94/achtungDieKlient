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

void Player::setX(int value)
{
    x = value;
}


int Player::getY() const
{
    return y;
}

void Player::setY(int value)
{
    y = value;
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

