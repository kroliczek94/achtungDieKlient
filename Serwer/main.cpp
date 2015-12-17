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

#define SERVER_PORT 8888

#define TRUE             1
#define FALSE            0

int main (int argc, char *argv[])
{
    int    len, rc, on = 1;
    int    listen_sd = -1, new_sd = -1;
    int    desc_ready, end_server = FALSE, compress_array = FALSE;
    int    close_conn;

    struct sockaddr_in6   addr;
    int    timeout;
    struct pollfd fds[200];
    int    nfds = 1, current_size = 0, i, j;

    /*************************************************************/
    /* Utwórz gniazdo strumieniowe AF_INET6 do odbierania        */
    /* połączeń przychodzących.                                  */
    /*************************************************************/
    listen_sd = socket(AF_INET6, SOCK_STREAM, 0);
    if (listen_sd < 0)
    {
        perror("Niepowodzenie funkcji socket()");
        exit(-1);
    }

    /*************************************************************/
    /* Pozwól na ponowne użycie deskryptora gniazda              */
    /*************************************************************/
    rc = setsockopt(listen_sd, SOL_SOCKET,  SO_REUSEADDR,
                    (char *)&on, sizeof(on));
    if (rc < 0)
    {
        perror("Niepowodzenie funkcji setsockopt()");
        close(listen_sd);
        exit(-1);

    }

    /*************************************************************/
    /* Ustaw gniazdo jako nieblokujące. Wszystkie gniazda        */
    /* połączeń przychodzących będą również nieblokujące, ponie- */
    /* waż będą dziedziczyć ten stan od gniazda nasłuchującego.  */
    /*************************************************************/
    rc = ioctl(listen_sd, FIONBIO, (char *)&on);
    if (rc < 0)
    {
        perror("Niepowodzenie funkcji ioctl()");
        close(listen_sd);
        exit(-1);
    }

    /*************************************************************/
    /* Powiąż gniazdo                                            */
    /*************************************************************/
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

    /*************************************************************/
    /* Ustaw parametr backlog funkcji listen.                    */
    /*************************************************************/
    rc = listen(listen_sd, 32);
    if (rc < 0)
    {
        perror("Niepowodzenie funkcji listen()");
        close(listen_sd);
        exit(-1);
    }

    /*************************************************************/
    /* Zainicjuj strukturę pollfd                                */
    /*************************************************************/
    memset(fds, 0 , sizeof(fds));

    /*************************************************************/
    /* Ustaw początkowe gniazdo nasłuchujące                     */
    /*************************************************************/
    fds[0].fd = listen_sd;
    fds[0].events = POLLIN;
    /*************************************************************/
    /* Ustaw limit czasu na 3 minuty. Brak                       */
    /* aktywności w tym czasie spowoduje zakończenie programu.   */
    /* Limit czasu podaje się w milisekundach.                   */
    /*************************************************************/
    timeout = (3 * 60 * 1000);

    /*************************************************************/
    /* Pętla oczekiwania na połączenia lub dane                  */
    /* przychodzące dla dowolnego połączonego gniazda.           */
    /*************************************************************/
    do
    {
        /***********************************************************/
        /* Wywołaj select() i zaczekaj 3 min na zakończenie.       */
        /***********************************************************/
        printf("Oczekiwanie na wywołanie funkcji select()...\n");
        rc = poll(fds, nfds, timeout);

        /***********************************************************/
        /* Sprawdź, czy wywołanie funkcji poll się                 */
        /* powiodło.                   */
        /***********************************************************/
        if (rc < 0)
        {
            perror("Niepowodzenie funkcji poll()");
            break;
        }

        /***********************************************************/
        /* Sprawdź, czy upłynęły 3 min czasu oczekiwania.          */
        /***********************************************************/
        if (rc == 0)
        {
            printf("Przekroczenie limitu czasu funkcji poll().  Zakończ program.\n");
            break;
        }


        /***********************************************************/
        /* Co najmniej jeden deskryptor jest czytelny.             */
        /* Sprawdź, który.                                         */
        /***********************************************************/
        current_size = nfds;
        for (i = 0; i < current_size; i++)
        {
            /*********************************************************/
            /* Znajdź deskryptory, które zwróciły                    */
            /* wartość POLLIN, i określ, czy jest to połączenie      */
            /* nasłuchujące, czy aktywne.                            */
            /*********************************************************/
            if(fds[i].revents == 0)
                continue;

            /*********************************************************/
            /* Jeśli pole revents nie ma wartości POLLIN, jest to    */
            /* rezultat nieoczekiwany, należy więc sporządzić        */
            /* protokół i zak. działanie serwera.                               */
            /*********************************************************/
            if(fds[i].revents != POLLIN)
            {
                printf("  Błąd! revents = %d\n", fds[i].revents);
                end_server = TRUE;
                break;

            }
            if (fds[i].fd == listen_sd)
            {
                /*******************************************************/
                /* Deskryptor nasłuchujący jest czytelny.                   */
                /*******************************************************/
                printf("Gniazdo nasłuchujące jest czytelne\n");

                /*******************************************************/
                /* Akceptuj wszystkie połączenia przychodzące,         */
                /* które znajdują się w kolejce gniazda                */
                /* nasłuchującego, przed ponownym wywołaniem           */
                /* funkcji poll.                                       */
                /*******************************************************/
                do
                {
                    /*****************************************************/
                    /* Akceptuj każde połączenie przychodzące.           */
                    /* Jeśli akceptowanie nie powiedzie się              */
                    /* z wartością EWOULDBLOCK, to zostały               */
                    /* zaakceptowane wszystkie. Każde inne               */
                    /* niepowodzenie akceptowania zakończy               */
                    /* działanie serwera.                                           */
                    /*****************************************************/
                    new_sd = accept(listen_sd, NULL, NULL);
                    if (new_sd < 0)
                    {
                        if (errno != EWOULDBLOCK)
                        {
                            perror("Niepowodzenie funkcji accept()");
                            end_server = TRUE;
                        }
                        break;
                    }

                    /*****************************************************/
                    /* Dodaj nowe połączenie przychodzące                */
                    /* do struktury pollfd                               */
                    /*****************************************************/
                    printf("Nowe połączenie przychodzące - %d\n", new_sd);
                    fds[nfds].fd = new_sd;
                    fds[nfds].events = POLLIN;
                    nfds++;

                    /*****************************************************/
                    /* Powróć do pętli i zaakceptuj nowe                 */
                    /* połączenie przychodzące.                          */
                    /*****************************************************/
                } while (new_sd != -1);
            }

                /*********************************************************/
                /* Nie jest to gniazdo nasłuchujące, dlatego             */
                /* istniejące połączenie musi być czytelne               */
                /*********************************************************/

            else
            {
                printf("Deskryptor %d jest czytelny\n", i);
                close_conn = FALSE;
                /*******************************************************/
                /* Odbierz wszystkie dane przychodzące do              */
                /* tego gniazda przed powrotem w pętli i ponownym      */
                /* wywołaniem funkcji poll.            */
                /*******************************************************/

                do
                {
                    char   buffer[80];
                    /*****************************************************/
                    /* Odbieraj dane dla tego połączenia aż do           */
                    /* wystąpienia niepowodzenia funkcji recv            */
                    /* z wartością EWOULDBLOCK. Jeśli wystąpi inne       */
                    /* niepowodzenie, połączenie zostanie zamknięte.     */
                    /*****************************************************/
                    rc = recv(fds[i].fd, buffer, sizeof(buffer), 0);
                    if (rc < 0)
                    {
                        if (errno != EWOULDBLOCK)
                        {
                            perror("Niepowodzenie funkcji recv()");
                            close_conn = TRUE;
                        }
                        break;
                    }

                    /*****************************************************/
                    /* Sprawdź, czy połączenie nie zostało               */
                    /* zamknięte przez klienta.                          */
                    /*****************************************************/
                    if (rc == 0)
                    {
                        printf("  Połączenie zamknięte\n");
                        close_conn = TRUE;
                        break;
                    }

                    /*****************************************************/
                    /* Dane zostały odebrane.                            */
                    /*****************************************************/
                    len = rc;
                    printf("Otrzymano bajtów: %d\n", len);
                    printf("%s", buffer);



                    char buf[] = "Ala ma kota";
                    /*****************************************************/
                    /* Odeślij dane do klienta                           */
                    /*****************************************************/

                            rc = send(fds[i].fd, buf, len, 0);
                    if (rc < 0)
                    {
                        perror("Niepowodzenie funkcji send()");
                        close_conn = TRUE;
                        break;
                    }
                     printf("poszlo dalej");
                } while (TRUE);

                /*******************************************************/
                /* Jeśli opcja close_conn została włączona,            */
                /* trzeba wyczyścić aktywne połączenie. Procedura      */
                /* czyszcząca obejmuje usunięcie deskryptora.          */
                /*                                         */
                /*******************************************************/
                if (close_conn)
                {
                    close(fds[i].fd);
                    fds[i].fd = -1;
                    compress_array = TRUE;
                }


            } /* Zakończenie istniejącego połączenia jest czytelne   */
        }   /* Zakończenie pętli poprzez deskryptory do odpytania  */

        /***********************************************************/
        /* Jeśli opcja compress array została włączona, należy     */
        /* skompresować tablice i zmniejszyć liczbę deskryptorów   */
        /* plików. Nie jest koniecznie ponowne przenoszenie pól    */
        /* events i revents, gdyż pole events zawsze będzie miało  */
        /* wartość POLLIN, a revents będzie polem danych           */
        /* wyjściowych.          */
        /***********************************************************/
        if (compress_array)
        {
            compress_array = FALSE;
            for (i = 0; i < nfds; i++)
            {
                if (fds[i].fd == -1)
                {
                    for(j = i; j < nfds; j++)
                    {
                        fds[j].fd = fds[j+1].fd;
                    }
                    nfds--;
                }
            }
        }

    } while (end_server == FALSE); /* Koniec działania serwera.    */

    /*************************************************************/
    /* Wyczyść wszystkie otwarte gniazda                         */
    /*************************************************************/
    for (i = 0; i < nfds; i++)
    {
        if(fds[i].fd >= 0)
            close(fds[i].fd);
    }
    return 0;
}