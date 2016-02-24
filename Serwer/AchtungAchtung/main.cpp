#include <stdio.h>
#include <stdlib.h>
#include <sys/ioctl.h>
#include <sys/poll.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <errno.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <iostream>
#include <string>
#include <vector>
#include "jsontranslator.h"

using namespace std;



int main (int argc, char *argv[])
{
    int SERVER_PORT  = 12345;
    int len, rc, on = 1;
    int mainSocket = -1, newSock = -1;
    bool end_server = false;
    bool endConn;
    char   buffer[80];
    struct sockaddr_in   addr = { AF_INET, htons(SERVER_PORT), INADDR_ANY };
    int timeout;
    struct pollfd fds[200];
    int numFd = 1, curSize = 0, i;


    manager * man = new manager();
    JSONTranslator * jtrans = new JSONTranslator(man);

    mainSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (mainSocket < 0)
    {
        perror("failed in function socket()");
        exit(-1);
    }

    rc = setsockopt(mainSocket, SOL_SOCKET,  SO_REUSEADDR,
                    (char *)&on, sizeof(on));
    if (rc < 0)
    {
        perror("failed in function setsockopt()");
        close(mainSocket);
        exit(-1);
    }

    rc = fcntl(mainSocket, F_SETFL, O_NONBLOCK, 1);
    if (rc < 0)
    {
        perror("niepowodzenie w fnctl()");
        close(mainSocket);
        exit(-1);
    }

    rc = bind(mainSocket, (struct sockaddr *)&addr, sizeof(addr));
    if (rc < 0)
    {
        perror("failed bind()");
        close(mainSocket);
        exit(-1);
    }

    rc = listen(mainSocket, 32);
    if (rc < 0)
    {
        perror("failed listen()");
        close(mainSocket);
        exit(-1);
    }

    memset(fds, 0 , sizeof(fds));

    fds[0].fd = mainSocket;
    fds[0].events = POLLIN;

    timeout = (3 * 60 * 1000);  //3 minuty

    do
    {

        rc = poll(fds, numFd, timeout);
        if (rc == 0){   printf("Czas upłynął..\n"); break;  }

        curSize = numFd;
        for (i = 0; i < curSize; i++)
        {
            if(fds[i].revents == 0) continue;//nic sie nie zdarzyło
            if(fds[i].revents != POLLIN)
            {
                printf("  Błąd! revents = %d\n", fds[i].revents); //przyszły jakieś kłopoty - my tylko nasłuchujemy
                end_server = true;
                break;

            }
            if (fds[i].fd == mainSocket)  //Dla mainSocketa - sprawdzamy, czy ktoś nowy nie przyszedł
            {
                do
                {
                    newSock = accept(mainSocket, NULL, NULL);
                    if (newSock < 0)
                    {
                        if (errno != EWOULDBLOCK)
                        {
                            perror("Failed accept()");
                            end_server = true;
                        }
                        break;
                    }
                    fcntl(newSock, F_SETFL, O_NONBLOCK, 1);


                    fds[numFd].fd = newSock;
                    fds[numFd].events = POLLIN;
                    numFd++;

                    // Ustaw na nieblokujące
                    long flags = fcntl(mainSocket,F_GETFL, 0);
                    flags = fcntl(newSock,F_GETFL, 0);
                    printf("cli  nonblock: %s\n", flags & O_NONBLOCK ? "true" : "false");

                } while (true);
            }else{
                endConn = false;

                do  //odbieramy dane
                {
                    rc = recv(fds[i].fd, buffer, sizeof(buffer), 0);
                    if (rc < 0)
                    {
                        if (errno != EWOULDBLOCK)
                        {
                            perror("niepowodzenie recv()");
                            endConn = true;
                        }
                        break;
                    }

                    if (rc == 0)
                    {
                        printf("Socket sobie poszedł\n");
                        endConn = true;
                        break;
                    }

                    len = rc;
                    printf("Otrzymano bajtów: %d\n", len);

                    string ss= jtrans->reply(buffer, i);
                    rc = send(fds[i].fd, ss.c_str(),ss.length(), 0);

                    if (rc < 0)
                    {
                        perror("Niepowodzenie send()");
                        endConn = true;
                        break;
                    }

                } while (true);

                if (endConn)
                {
                    close(fds[i].fd);
                    fds[i].fd = -1;
                }
            }
        }
    } while (end_server == false);

    //zamknij sockety po zabawie
    for (i = 0; i < numFd; i++)
    {
        if(fds[i].fd >= 0)
            close(fds[i].fd);
    }
}
