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
#include "tcp.h"

using namespace std;



int main (int argc, char *argv[])
{
    int    SERVER_PORT  = 12345;
    int    len, rc, on = 1;
    int    listen_sd = -1, new_sd = -1;
    int    end_server = false;
    int    close_conn;
    char   buffer[80];
    struct sockaddr_in6   addr;
    int    timeout;
    struct pollfd fds[200];
    int    nfds = 1, current_size = 0, i, j;


    manager * man = new manager();
    JSONTranslator * jtrans = new JSONTranslator(man);

    listen_sd = socket(AF_INET6, SOCK_STREAM, 0);
    if (listen_sd < 0)
    {
        perror("Niepowodzenie funkcji socket()");
        exit(-1);
    }

    rc = setsockopt(listen_sd, SOL_SOCKET,  SO_REUSEADDR,
                    (char *)&on, sizeof(on));
    if (rc < 0)
    {
        perror("Niepowodzenie funkcji setsockopt()");
        close(listen_sd);
        exit(-1);
    }

    rc = fcntl(listen_sd, F_SETFL, O_NONBLOCK, 1);
    if (rc < 0)
    {
        perror("Niepowodzenie funkcji ioctl()");
        close(listen_sd);
        exit(-1);
    }

    memset(&addr, 0, sizeof(addr));
    addr.sin6_family      = AF_INET6;
    memcpy(&addr.sin6_addr, &in6addr_any, sizeof(in6addr_any));
    addr.sin6_port        = htons(SERVER_PORT);
    rc = bind(listen_sd,
              (struct sockaddr *)&addr, sizeof(addr));
    if (rc < 0)
    {
        perror("Niepowodzenie funkcji bind()");
        close(listen_sd);
        exit(-1);
    }

    rc = listen(listen_sd, 32);
    if (rc < 0)
    {
        perror("Niepowodzenie funkcji listen()");
        close(listen_sd);
        exit(-1);
    }

    memset(fds, 0 , sizeof(fds));

    fds[0].fd = listen_sd;
    fds[0].events = POLLIN;

    timeout = (3 * 60 * 1000);

    do
    {

        rc = poll(fds, nfds, timeout);

        if (rc < 0)
        {
            perror("Niepowodzenie funkcji poll()");
            break;
        }

        if (rc == 0)
        {
            printf("Przekroczenie limitu czasu funkcji poll().  Zakończ program.\n");
            break;
        }


        current_size = nfds;
        for (i = 0; i < current_size; i++)
        {

            if(fds[i].revents == 0)
                continue;

            if(fds[i].revents != POLLIN)
            {
                printf("  Błąd! revents = %d\n", fds[i].revents);
                end_server = true;
                break;

            }
            if (fds[i].fd == listen_sd)
            {
                printf("Gniazdo nasłuchujące jest czytelne\n");

                do
                {
                    new_sd = accept(listen_sd, NULL, NULL);
                    if (new_sd < 0)
                    {
                        if (errno != EWOULDBLOCK)
                        {
                            perror("Niepowodzenie funkcji accept()");
                            end_server = true;
                        }
                        break;
                    }

                    printf("Nowe połączenie przychodzące - %d\n", new_sd);
                    fcntl(new_sd, F_SETFL, O_NONBLOCK, 1);

                    printf("%d", nfds);
                    fds[nfds].fd = new_sd;
                    fds[nfds].events = POLLIN;
                    nfds++;

                    long flags = fcntl(listen_sd,F_GETFL, 0);
                    printf("serv nonblock: %s\n", flags & O_NONBLOCK ? "true" : "false");
                    flags = fcntl(new_sd,F_GETFL, 0);
                    printf("cli  nonblock: %s\n", flags & O_NONBLOCK ? "true" : "false");

                } while (new_sd != -1);
            }


            else
            {
                close_conn = false;

                do
                {

                    rc = recv(fds[i].fd, buffer, sizeof(buffer), 0);
                    if (rc < 0)
                    {
                        if (errno != EWOULDBLOCK)
                        {
                            perror("Niepowodzenie funkcji recv()");
                            close_conn = true;
                        }
                        break;
                    }

                    if (rc == 0)
                    {
                        printf("  Połączenie zamknięte\n");
                        close_conn = true;
                        break;
                    }

                    len = rc;
                    //printf("Otrzymano bajtów: %d\n", len);

                    string ss= jtrans->reply(buffer, i);
                    rc = send(fds[i].fd, ss.c_str(),ss.length(), 0);

                    if (rc < 0)
                    {
                        perror("Niepowodzenie funkcji send()");
                        close_conn = true;
                        break;
                    }

                } while (true);

                if (close_conn)
                {
                    close(fds[i].fd);
                    fds[i].fd = -1;

                }


            }
        }



    } while (end_server == false); /* Koniec działania serwera.    */

    for (i = 0; i < nfds; i++)
    {
        if(fds[i].fd >= 0)
            close(fds[i].fd);
    }
}
