cmake_minimum_required(VERSION 3.4.1)
add_library(main SHARED src/main/jni/main.cpp)
target_link_libraries(main)