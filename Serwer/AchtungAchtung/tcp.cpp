#include "tcp.h"

tcp::tcp(JSONTranslator * jtr, manager * mn)
{
    tcp(jtr, mn, this->trans, this->man);
}

tcp::tcp(JSONTranslator * jtr, manager * mn, JSONTranslator *&tr, manager *&m)
{
    tr = jtr;
    m = mn;
}

