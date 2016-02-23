#ifndef TCP_H
#define TCP_H
#include "manager.h"
#include "jsontranslator.h"

class tcp
{
public:

    tcp(JSONTranslator *jtr, manager *mn);

private:
     tcp(JSONTranslator *jtr, manager *mn, JSONTranslator *&tr, manager *&m);

    JSONTranslator * trans;
    manager * man;
};

#endif // TCP_H
