#include "jsontranslator.h"
#include "player.h"

JSONTranslator::JSONTranslator(manager *man)
{
    this->man = man;
}

string JSONTranslator::reply(char *buffer){

    Document doc;
    doc.Parse(buffer);


    StringBuffer s;
    Writer<StringBuffer> writer(s);


    assert(doc.IsObject());
    assert(doc.HasMember("action"));
    assert(doc["action"].IsString());


    string action = doc["action"].GetString();

    if (action == "register"){
        assert(doc.HasMember("id"));
        assert(doc["id"].IsInt());
        int id = doc["id"].GetInt();

        assert(doc.HasMember("formerid"));
        assert(doc["formerid"].IsInt());
        int formerid = doc["formerid"].GetInt();


        bool ok = man->reservePosition(id, formerid);
        writer.StartObject();
        writer.String("action");
        writer.String("register");
        writer.String("id");
        writer.Int(id);
        writer.String("formerid");
        writer.Int(formerid);
        writer.String("ok");
        writer.Bool(ok);
        writer.EndObject();

    }
     else if (action == "state"){

        if (!man->getGameStarted()){

            writer.StartObject();
            writer.String("action");
            writer.String("statebefore");
            writer.String("ttl");
            writer.Int(man->getTimeToGo());
            writer.String("reservedplaces");
            writer.StartArray();
                for (int i = 0; i < 6 ; i++){
                    writer.Bool(man->getReserved()[i]);
                }
            writer.EndArray();
            writer.String("readypeople");
            writer.StartArray();
                for (int i = 0; i < 6 ; i++){
                    writer.Bool(man->getReady()[i]);
                }
            writer.EndArray();
            writer.String("peoplenames");
            writer.StartArray();
                for (int i = 0; i < 6 ; i++){
                    writer.String(man->getNamePlayer(i).c_str());
                }
            writer.EndArray();
            writer.EndObject();
        }
    }



    string ss = s.GetString();
    cout << ss;
    return ss;
}
