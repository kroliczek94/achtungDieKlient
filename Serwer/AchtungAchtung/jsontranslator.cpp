#include "jsontranslator.h"
#include "player.h"
#include <time.h>

JSONTranslator::JSONTranslator(manager *man)
{
    this->man = man;
}

string JSONTranslator::reply(char *buffer){

}

string JSONTranslator::reply(char *buffer, int i)
{
    Document doc;
    doc.Parse(buffer);


    StringBuffer s;
    Writer<StringBuffer> writer(s);

    //cout << buffer << endl;

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


        bool ok = man->reservePosition(id, formerid, i);
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
        }else{

            assert(doc.HasMember("dec"));
            assert(doc["dec"].IsArray());
            bool end = false;
            int winner = -1;

            for (int j = 0 ; j < 6 ; j++){
                assert(doc["dec"][j].IsInt());
                int dec = doc["dec"][j].GetInt();

                bool ok = man->playerOfClient(j, i);
                int error = 0;

                if (ok && !man->getPlayerOut(j) && !man->getPauzed())
                {
                    error = man->movePlayer(dec, j);
                    if (error) man->playerLose(j);

                    for (int x= 0; x <6; x++){
                        if (!man->getReady()[x]) continue;
                        if (man->twoPointsDifference(man->getPointsPlayer(x), x))
                        {
                            end = true;
                            winner = x;

                        }
                    }
                    error = 0;
                }


            }

            int stan = 0;
            int gamers = 0;

            for (int i = 0; i < 6 ; i++){
                if (man->getReady().at(i)) gamers++;
                if (man->getPlayerOut(i)) stan++;

            }
            if (stan == gamers && !end){
                man->reset();
            }

            writer.StartObject();
            writer.String("action");
            writer.String("stateafter");

            writer.String("idplayers");
            writer.StartArray();
            for (int i = 0; i < 6 ; i++){
                if (man->getReady().at(i))
                    writer.Int(i);
            }
            writer.EndArray();

            writer.String("peoplenames");
            writer.StartArray();
            for (int i = 0; i < 6 ; i++){
                if (man->getReady().at(i))
                    writer.String(man->getNamePlayer(i).c_str());
            }
            writer.EndArray();
            writer.String("points");
            writer.StartArray();
            for (int i = 0; i < 6 ; i++){
                if (man->getReady().at(i))
                    writer.Int(man->getPointsPlayer(i));
            }
            writer.EndArray();
            writer.String("xpos");
            writer.StartArray();
            for (int i = 0; i < 6 ; i++){
                if (man->getReady().at(i))
                    writer.Int(man->getPlayerX(i));
            }
            writer.EndArray();
            writer.String("ypos");

            writer.StartArray();
            for (int i = 0; i < 6 ; i++){
                if (man->getReady().at(i))
                    writer.Int(man->getPlayerY(i));
            }
            writer.EndArray();
            if (stan == gamers)
            {
                writer.String("restart");
                writer.Int(0);
            }
            else
            {
                writer.String("restart");
                writer.Int(1);

            }
            writer.String("end");
            writer.Bool(end);
            writer.String("winner");
            if (end){
                writer.String(man->getNamePlayer(winner).c_str());
                man->hardReset();
            }else{
                writer.String("noone");
            }

            writer.EndObject();

        }
    }else if (action == "changename"){
        assert(doc.HasMember("id"));
        assert(doc["id"].IsInt());
        int id = doc["id"].GetInt();

        assert(doc.HasMember("name"));
        assert(doc["name"].IsString());
        string name = doc["name"].GetString();

        man->setImiePlayer(id, name);
    }else if (action == "readytoplay"){
        assert(doc.HasMember("id"));
        assert(doc["id"].IsInt());
        int id = doc["id"].GetInt();

        man->readyPlayer(id);
    }else if (action == "letstart"){
        man->letsStart();

        writer.StartObject();
        writer.String("action");
        writer.String("letstart");
        writer.String("success");
        writer.Bool(man->getGameStarted());
        writer.EndObject();

        \
    }else if (action == "unpause"){
        man->pauza(false);
    }



    string ss = s.GetString();

    return ss;
}
