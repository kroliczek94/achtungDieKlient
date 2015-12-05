cmake_minimum_required(VERSION 2.6)
project(serwer)

add_executable(serwer main.cpp)

install(TARGETS serwer RUNTIME DESTINATION bin)
