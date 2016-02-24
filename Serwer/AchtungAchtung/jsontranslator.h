#ifndef JSONTRANSLATOR_H
#define JSONTRANSLATOR_H

#include <string>
#include <iostream>
#include "rapidjson/document.h"
#include "rapidjson/writer.h"
#include "rapidjson/stringbuffer.h"
#include "manager.h"

using namespace std;
using namespace rapidjson;

class JSONTranslator
{
public:
    JSONTranslator(manager * man);
    string reply(char * buffer, int i);
private:
    manager * man;
};

#endif // JSONTRANSLATOR_H
