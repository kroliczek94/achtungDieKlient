project(AchtungAchtung)
cmake_minimum_required(VERSION 2.8)
aux_source_directory(. SRC_LIST)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L/usr/lib -ljsoncpp -pthread -std=c++14")
add_executable(${PROJECT_NAME} ${SRC_LIST})

