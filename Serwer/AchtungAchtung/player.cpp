#include "player.h"
#include <string>

using namespace std;

Player::Player(int id, string name)
{
    this->id = id;
    this->name=  name;
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

